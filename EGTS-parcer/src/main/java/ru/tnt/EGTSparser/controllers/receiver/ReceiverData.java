package ru.tnt.EGTSparser.controllers.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tnt.EGTSparser.crc.service.CRC;
import ru.tnt.EGTSparser.data.BodyData_APPDATA;
import ru.tnt.EGTSparser.data.BodyData_RESPONSE;
import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;
import ru.tnt.EGTSparser.dataProcessing.ResponseNormalCreate;
import ru.tnt.EGTSparser.parser.ConvertIncomingData;

import ru.tnt.EGTSparser.parser.ResponseCreateImpl;
import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.ProcessingResultCodeConstants;
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
        log.info("   Received: " + income.length + " bytes ");
     String s=   StringArrayUtils.arrayPrintToScreen(StringArrayUtils.convertToObject(income));
     System.out.println("s="+s);

     HeaderData hd = (HeaderData) headerCreator.create(income);
     System.out.println("hd "+hd);
        System.out.println();System.out.println();System.out.println();
        if (income[ByteFixedPositions.PACKAGE_TYPE_INDEX] == ByteFixedPositions.TYPE_APPDATA)
            appData = (BodyData_APPDATA) appDataCreator.create(income);
        System.out.println(")))))))))))))))))");
//       response(income); //crc8=117   crc16  -11435   checkSumm ++   v[0]=-45   v[1]=85


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(1234567890);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

       response(hd);
     //
        return null;
    }

    private void response(HeaderData hd) throws IOException {
        BodyData_RESPONSE bdr = (BodyData_RESPONSE) responseNormal.createNormalResponce(hd);



//        output.write(responseData);
//        log.info("Responce send    :   "+responseData.length);
//        output.close();
        System.out.println("  HeaderData hd)");
        System.out.println();
        System.out.println();
        System.out.println(bdr);
        System.out.println();
        System.out.println();
        OutputStream output = socket.getOutputStream();
        output.write(bdr.getResponseBody());
        output.close();

    }

    private void response(byte[] income) throws IOException {
        OutputStream output = socket.getOutputStream();


        System.out.println("+   income  ++  "+StringArrayUtils.arrayPrintToScreen( income ));
        System.out.println("INCOME  HCS "+ByteFixedPositions.getHCSIndex(income));

      byte[] responseData = StringArrayUtils.createSubArray(income, 0, income[3]);
        System.out.println("+ -1  responseData   ++  "+StringArrayUtils.arrayPrintToScreen(responseData));
        System.out.println();
        System.out.println();

        responseData[5]=3;
        responseData[6]=0;
        System.out.println("+ 0  responseData   ++  "+StringArrayUtils.arrayPrintToScreen(responseData));
        System.out.println();

        byte[] responseBody = new byte[3];
        responseBody[0] = income[7];
        responseBody[1] =income[8];
        responseBody[2] = ProcessingResultCodeConstants.EGTS_PC_OK;
        System.out.println("+   responseBody   ++  "+StringArrayUtils.arrayPrintToScreen(responseBody));
        System.out.println();

        responseData = StringArrayUtils.joinArrays(responseData, responseBody);
        System.out.println("+ 1  ----responseData ++  "+StringArrayUtils.arrayPrintToScreen(responseData));
        System.out.println();

        responseData[0]++;
        responseData[7] =  120; //StringArrayUtils.rndByte(2)[0];
        responseData[8] = -120;// StringArrayUtils.rndByte(2)[0];
        responseData[ByteFixedPositions.getHCSIndex(income)-1] =  0;
        System.out.println("+ 2  responseData ++  "+StringArrayUtils.arrayPrintToScreen(responseData));
        System.out.println();

        byte[] hcs = StringArrayUtils.createSubArray(responseData, 0,ByteFixedPositions.getHCSIndex(income));
        System.out.println("+-----   HCS  ++  "+StringArrayUtils.arrayPrintToScreen(hcs));
        System.out.println();

        System.out.println(" *****\n********                     hcs len  "+hcs.length);
System.out.println("ARRAYS: \n" +
        "[2, 0, 2, 11, 0, 3, 0, 122, 0, 0] \n" +
       StringArrayUtils.arrayPrintToScreen(hcs)+" \n"

);
        byte crcVal8 =(byte) crc.calculate8(StringArrayUtils.byteToLong(hcs));
        System.out.println("+-----   crcVal8 ++  "+crcVal8);
        System.out.println();

        short crcVal16 = (short) crc.calculate16(StringArrayUtils.byteToLong(responseBody));
        System.out.println("+-----   crcVal16 ++  "+crcVal16);
        System.out.println("+-----   -----responseBody ++  "+StringArrayUtils.arrayPrintToScreen(responseBody));
        System.out.println();
        System.out.println();


        System.out.println("+   responseData array before ++  " + StringArrayUtils.arrayPrintToScreen(responseData));
        responseData[ByteFixedPositions.getHCSIndex(income)] = (byte) crc.calculate8(StringArrayUtils.byteToLong(hcs));
        System.out.println("+   responseData array after ++  " + StringArrayUtils.arrayPrintToScreen(responseData));
        System.out.println();

        byte[] checkSumm=StringArrayUtils.shortToByteArray(crcVal16);
        System.out.println("+-----   crcVal16 ++  "+crcVal16);
        System.out.println("+-----   -----checkSumm ++  "+StringArrayUtils.arrayPrintToScreen(checkSumm));
        System.out.println();



        System.out.println("+ 3  responseData ++  "+StringArrayUtils.arrayPrintToScreen(responseData));
        System.out.println();

        short ccrr=  (short) crc.calculate16(StringArrayUtils.byteToLong(responseData));
        System.out.println(ccrr+" CORRECT "+
                StringArrayUtils.arrayPrintToScreen(responseData)+" \n");

        System.out.println("F    FFFFFFFFFFFFFF   RESPONSEBODY Len "+responseData.length);

         output.write(responseData);
        log.info("Responce send    :   "+responseData.length);
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
