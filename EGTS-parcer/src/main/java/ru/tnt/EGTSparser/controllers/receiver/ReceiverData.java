package ru.tnt.EGTSparser.controllers.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tnt.EGTSparser.data.BodyData_APPDATA;
import ru.tnt.EGTSparser.data.BodyData_RESPONSE;
import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.parser.ConvertIncomingData;
import ru.tnt.EGTSparser.parser.NormalProcessingImpl;
import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.StringArrayUtils;
import ru.tnt.EGTSparser.util.StringFixedBeanNames;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Slf4j
@Component
public class ReceiverData implements Callable<Future<Byte[]>> {

    private Socket socket;

    @Autowired
    @Qualifier(StringFixedBeanNames.HEADER_CREATOR_BEAN)
    private ConvertIncomingData headerCreator;


    @Autowired
    @Qualifier( StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
    private ConvertIncomingData appDataCreator;

    @Autowired
    NormalProcessingImpl normalProcessing;

    @Override
    public Future<Byte[]> call() throws Exception {
        BodyData_APPDATA appData;
        byte[] income = receive();
        log.info("   Received: " + income.length + " bytes ");
        StringArrayUtils.arrayPrintToScreen(StringArrayUtils.convertToObject(income));
        HeaderData hd = (HeaderData) headerCreator.create(income);
        if(income[ByteFixedPositions.PACKAGE_TYPE_INDEX]==ByteFixedPositions.TYPE_APPDATA )
            appData = (BodyData_APPDATA) appDataCreator.create(income);
        response(income);
        return null;
    }

    private void response(byte[] income) throws IOException {
        OutputStream output = socket.getOutputStream();
        BodyData_RESPONSE bdr = (BodyData_RESPONSE) normalProcessing.prepareData(income);

       System.out.println("    ***** \n"+bdr+"   *****");
       byte[] headerOut =StringArrayUtils.createSubArray(income,0,11);
      byte[] out= new byte[3];
        out[0]=bdr.getRpid()[0]; out[1]=bdr.getRpid()[1]; out[2]=bdr.getRp();
   out=StringArrayUtils.joinArrays(headerOut,out);
       out[7]=StringArrayUtils.rndByte(2)[0];out[8]=StringArrayUtils.rndByte(2)[0];
        output.write(out);
        log.info("   Responce send ");
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
