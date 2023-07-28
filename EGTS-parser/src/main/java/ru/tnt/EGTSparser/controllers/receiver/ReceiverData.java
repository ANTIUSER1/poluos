package ru.tnt.EGTSparser.controllers.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tnt.EGTSparser.crc.service.CRC;
import ru.tnt.EGTSparser.data.BodyData_APPDATA;
import ru.tnt.EGTSparser.data.BodyData_RESPONSE;
import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.dataProcessing.ResponseNormalCreate;
import ru.tnt.EGTSparser.parser.ConvertIncomingData;

import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.StringArrayUtils;
import ru.tnt.EGTSparser.util.StringFixedBeanNames;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
@Slf4j
public class ReceiverData implements Callable<Future<Byte[]>> {

//    @Autowired
//    NormalProcessingImpl normalProcessing;
    private Socket socket;
    @Autowired
    @Qualifier(StringFixedBeanNames.HEADER_CREATOR_BEAN)
    private ConvertIncomingData headerCreator;

    @Autowired
    @Qualifier(StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
    private ConvertIncomingData appDataCreator;

    @Autowired
    private ResponseNormalCreate responseNormal;

    @Autowired
    private CRC crc;

    @Override
    public Future<Byte[]> call() throws Exception{
        BodyData_APPDATA appData;
        byte[] income = receive();
        log.info("Received: " + income.length + " bytes ");
short pid = calcPID(income);
        log.info("PID: [" + income[7] + "  " + income[8]+ "] ( "+pid+" ) ");

        HeaderData hd = (HeaderData) headerCreator.create(income);

        if (income[ByteFixedPositions.PACKAGE_TYPE_INDEX] == ByteFixedPositions.TYPE_APPDATA)
            appData = (BodyData_APPDATA) appDataCreator.create(income);


       response(hd);
        return null;
    }

    private void response(HeaderData hd) throws IOException {
        BodyData_RESPONSE bdr = (BodyData_RESPONSE) responseNormal.createNormalResponse(hd);
        log.info("Sending back response to BNSO start. \n Data: "
                +StringArrayUtils.arrayPrintToScreen(bdr.getResponseBody()));
        OutputStream output = socket.getOutputStream();

        output.write(bdr.getResponseBody());
        log.info("Sending back response to BNSO finish. " );
        // output.close();
    }


    private short calcPID(byte[] income) {
        byte[] pid=new byte[2];
        pid[0]=income[8];pid[1]=income[7];
        return  StringArrayUtils.calcShortFromArray(pid);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private byte[] receive() throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        int dataSize = in.available();
        byte[] res = new byte[dataSize];
        in.read(res);
        return res;

    }
}
