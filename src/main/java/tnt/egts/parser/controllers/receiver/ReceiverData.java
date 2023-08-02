package tnt.egts.parser.controllers.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.BodyData_APPDATA;
import tnt.egts.parser.data.BodyData_RESPONSE;
import tnt.egts.parser.data.HeaderData;
import tnt.egts.parser.data.validation.ReadFDLValidate;
import tnt.egts.parser.data.validation.ResponseNormalCreate;
import tnt.egts.parser.errors.ConnectionException;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.parser.ConvertIncomingData;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringArrayUtils;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Component
@Slf4j
public class ReceiverData implements Runnable {

    private Socket socket;

    @Autowired
    private ByteAnalizer byteAnalizer;

    @Autowired
    @Qualifier (StringFixedBeanNames.HEADER_CREATOR_BEAN)
    private ConvertIncomingData headerCreator;

    @Autowired
    @Qualifier (StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
    private ConvertIncomingData appDataCreator;

    @Autowired
    private ResponseNormalCreate responseNormal;

    @Autowired
    private ReadFDLValidate readFDLValidate;

    @Autowired
    private CRC crc;

    private byte responseCode;

    @Override
    public void run() {
        try {
            byte[] income = receiveData();

            response(income, responseCode);
        } catch (IOException | IncorrectDataException e) {
            log.info("Receiving broken  " + e.getCause());
            throw new ConnectionException(e.getMessage());
        }
    }

    private byte[] receiveData() throws IOException {
        log.info("Receiving starts ");
        byte[] income = receive();
        log.info("Received: " + income.length + " bytes ");
        short pid = calcPID(income);
        log.info("PID: [" + income[7] + "  " + income[8] + "] ( " + pid + " ) ");
        responseCode = byteAnalizer.analize(income);
        log.info(" Response. CODDE " + responseCode  );
        incomingsCreate(income);
        log.info("Receiving finish ");
        return income;
    }

    private void incomingsCreate(byte[] income) {
        log.info("Incoming data wrapping start");
        HeaderData hd;
        BodyData_APPDATA appData;
        if (responseCode == 0) {
            hd = (HeaderData) headerCreator.create(income);
        }
        if (income[ByteFixedPositions.PACKAGE_TYPE_INDEX] == ByteFixValues.TYPE_APPDATA)
            appData = (BodyData_APPDATA) appDataCreator.create(income);
        log.info("Incoming data wrapping finish");
    }


    private void response(byte[] income, byte code) throws IOException {
        BodyData_RESPONSE bdr = (BodyData_RESPONSE) responseNormal.createNormalResponse(income, code);
        log.info("Sending back response to BNSO start. \n Data: " + StringArrayUtils.arrayPrintToScreen(bdr.getResponseBody()));
        OutputStream output = socket.getOutputStream();
        output.write(bdr.getResponseBody());
        log.info("Sending back response to BNSO finish. ");
        output.close();
    }


    private short calcPID(byte[] income) {
        byte[] pid = new byte[2];
        pid[0] = income[8];
        pid[1] = income[7];
        return StringArrayUtils.calcShortFromArray(pid);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private byte[] receive() throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        int dataSize = in.available();
        byte[] out = new byte[dataSize];
        in.read(out);
        return out;

    }
}
