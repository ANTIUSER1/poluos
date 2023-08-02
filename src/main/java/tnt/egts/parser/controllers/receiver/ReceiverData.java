package tnt.egts.parser.controllers.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tnt.egts.parser.data.BodyData_APPDATA;
import tnt.egts.parser.data.BodyData_RESPONSE;
import tnt.egts.parser.data.HeaderData;
import tnt.egts.parser.data.validation.ReadFDLValidate;
import tnt.egts.parser.data.validation.ResponseNormalCreate;
import tnt.egts.parser.errors.ConnectionException;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.parser.ConvertIncomingData;
import tnt.egts.parser.util.*;

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

    private byte responseCode;

    @Override
    public void run() {
        try {
            byte[] income = receiveData();
            if (income == null) {
                log.error("Null data received");
                return;
            }
            response(income, responseCode);
        } catch (IOException | IncorrectDataException e) {
            log.info("Receiving broken  " + e.getCause());
            throw new ConnectionException(e.getMessage());
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] receiveData() throws IOException, NumberArrayDataException {
        log.info("Receiving starts ");
        byte[] income = receive();
        log.info("Received: " + income.length + " bytes {"
                 + ArrayUtils.arrayPrintToScreen(income) + "}");
        if (income.length ==0) {
            log.error("Incoming data is too small ");
            return null;
        }

        short pid = calcPID(income);
        log.info("PID: [" + income[7] + "  " + income[8] + "] ( " + pid + " ) ");
        responseCode = byteAnalizer.analize(income);
        log.info(" Response. CODDE " + responseCode);
        incomingsCreate(income);
        log.info("Receiving finish ");
        return income;
    }

    private void incomingsCreate(byte[] income) throws NumberArrayDataException {

        log.info("Incoming data wrapping start");
        HeaderData hd;
        BodyData_APPDATA appData;
        System.out.println(responseCode+"   ***000000000   " + (responseCode == ProcessingResultCodeConstants.EGTS_PC_OK));
        if (responseCode == ProcessingResultCodeConstants.EGTS_PC_OK) {
            hd = (HeaderData) headerCreator.create(income);
        }



        if (income[ByteFixedPositions.PACKAGE_TYPE_INDEX] ==
            ByteFixValues.TYPE_APPDATA && readFDLValidate.readFDL(income))
            appData = (BodyData_APPDATA) appDataCreator.create(income);
        log.info("Incoming data wrapping finish");
    }


    private void response(byte[] income, byte code) throws IOException {
        BodyData_RESPONSE bdr = (BodyData_RESPONSE) responseNormal.createNormalResponse(income, code);
        log.info("Sending back response to BNSO start. \n Data: " + ArrayUtils.arrayPrintToScreen(bdr.getResponseBody()));
        OutputStream output = socket.getOutputStream();
        output.write(bdr.getResponseBody());
        log.info("Sending back response to BNSO finish. ");
        output.close();
    }


    private short calcPID(byte[] income) {
        byte[] pid = new byte[2];
        pid[0] = income[8];
        pid[1] = income[7];
        return ArrayUtils.calcShortFromArray(pid);
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
