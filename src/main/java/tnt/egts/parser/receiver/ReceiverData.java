package tnt.egts.parser.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.OutcomeIdentFinalCreate;
import tnt.egts.parser.cmmon.sendBack.DoResponse;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Component
@Slf4j
public class ReceiverData implements Runnable {

    @Autowired
    @Qualifier ("readyToSend")
    OutcomeIdentFinalCreate outcomeIdentCreate;

    private Socket socket;

    private OutcomeIdent outcomeData;

//    @Autowired
//    private ComponentsStoring componentsStoring;

    @Autowired
    private Storage storage;

//    @Autowired
//    private HeadService headService;
//
//    @Autowired
//    private CommonAPPService commonAPPService;

    //**********************************************************

    @Autowired
    CRC crc;

    @Autowired
    private ByteAnalizer byteAnalizer;
//
//    @Autowired
//    @Qualifier (StringFixedBeanNames.HEADER_CREATOR_BEAN)
//    private ConvertIncomingData headerCreator;
//
//    @Autowired
//    @Qualifier (StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
//    private ConvertIncomingData appDataCreator;
//
//    @Autowired
//    private ResponseNormalCreate responseNormal;
//
//    @Autowired
//    private ReadFDLValidate readFDLValidate;

    private byte responseCode;
   private volatile byte msgNO;

    @Override
    public void run() {
        log.info("work on request start");
        try {
            byte[] income = receive();
            if (income == null || income.length==0) {
                log.error("Null data received, or income data is empty");
                return;
            }

//responseCode= byteAnalizer.analize(income);
            //receiveData ; //
            dataTransform(income, responseCode);
            sendResponse();

            msgNO++;msgNO= (byte) (msgNO % 100);
            log.info("work on request finish. step: "+msgNO  );
        } catch (IOException | IncorrectDataException e) {
            log.info("Receiving broken  ");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Error while data transform");
            e.printStackTrace();
        }
    }

    private void sendResponse() {
        DoResponse resp = (DoResponse) outcomeData;
        log.info("Sending back response to BNSO start. \n Data: " + ArrayUtils.arrayPrintToScreen(resp.getData())+ " of length "+resp.getData().length);
        OutputStream output = null;
        try {
            output = socket.getOutputStream();
            output.write(resp.getData());
            log.info("Sending back response to BNSO finish. ");
            testOutSendData(resp.getData());
//            output.close();
        } catch (IOException e) {
            log.error("Error while response to  attempt");
            e.printStackTrace();
        }
    }

    private void testOutSendData(byte[] data) {
        System.out.println();
        System.out.println("************** TEST  OUTPUT  " +
                           "*********************");
        System.out.println();
        System.out.println("OUTPUT:  " +ArrayUtils.arrayPrintToScreen(data)+
                           " LENGTH: "+data.length);
        System.out.println();
        System.out.println();
        byte bt=data[3];
        System.out.println("  HL: "+bt);
        byte[] inf=ArrayUtils.getFixedLengthSubArray(data,0, bt);
        System.out.println( "HEAD:  "+ArrayUtils.arrayPrintToScreen(inf));

        bt=data[9];
        System.out.println("PT:   "+bt);

        byte[] fdl=new byte[2];
        fdl[0]=data[6];
        fdl[1]=data[5];
        short fdlDat;
        try {
            fdlDat=ArrayUtils.byteArrayToShort(fdl);
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
        System.out.println("FDL:  "+ArrayUtils.arrayPrintToScreen(fdl)+ "  AS" +
                           "  NUM:  "+fdlDat + "  EXPETED: " +(data.length-data[3]-2) );


        byte[] pid=new byte[2];
        pid[0]=data[8];
        pid[1]=data[7];
        short pidDat;
        try {
            pidDat=ArrayUtils.byteArrayToShort(pid);
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
        System.out.println("PID:  "+ArrayUtils.arrayPrintToScreen(pid)+ "  AS" +
                           "  NUM:  "+pidDat);

        bt=data[  data[3]-1];
        System.out.println("HCS:   "+bt);

        byte[] sfrd =ArrayUtils.getFixedLengthSubArray(data, data[3] ,fdlDat);
        System.out.println( "  sfrd: "+ArrayUtils.arrayPrintToScreen(sfrd)+
                            "  LEN: "+sfrd.length);

        long crc16=   crc.calculate16(sfrd);
       short crcShort= (short) crc16;
        byte[] crcArr=ArrayUtils.shortToByteArray(crcShort);
         System.out.println("C CRC16:: "+crc16+"  HEX  "+Long.toHexString(crc16));
        System.out.println("CRC-short:  "+crcShort +"  as arr:  "+ArrayUtils.arrayPrintToScreen(crcArr)+ "   HEX:  "+Integer.toHexString(crcShort));
//testCRC16();
        System.out.println();
        System.out.println("****************TEST");
        System.out.println();
    }

    private void testCRC16() {
        byte[] tb= {2,5};
        System.out.println("***========CRC-16 test =============");
        long crc16= crc.calculate16(tb);
        System.out.println(" crc-16  16:  "+crc16+" HEX:  "+Integer.toHexString((int) crc16));
        System.out.println("***========CRC-16 test =============");

    }

    private void dataTransform(byte[] income, byte code) throws NumberArrayDataException {
        log.info("Storage  income Data start");
        IncomeDataStorage store = storage.create(income);

//        CommonAPPDATA cnd = commonAPPService.create(store);
//        componentsStoring.append(cnd);
//        Head head = (Head) headService.create(store);
//        componentsStoring.append(head);
//        System.out.println();
//        System.out.println(componentsStoring);

        outcomeData = outcomeIdentCreate.create(store,code);
        log.info("Storage  income Data finish");
    }

//    private byte[] receiveData() throws IOException, NumberArrayDataException {
//        log.info("Receiving starts ");
//        byte[] income = receive();
//        if (income.length == 0) {
//            log.error("Incoming data is too small ");
//            return null;
//        }
//        log.info("Received: " + income.length + " bytes {"
//                 + ArrayUtils.arrayPrintToScreen(income) + "}");
//
//        short pid = calcPID(income);
//        log.info("PID: [" + income[7] + "  " + income[8] + "] ( " + pid + " ) ");
//        responseCode = byteAnalizer.analize(income);
//        log.info(" Response. CODDE " + responseCode);
//        incomingsCreate(income);
//        log.info("Receiving finish ");
//        return income;
//    }

//    private void incomingsCreate(byte[] income) throws NumberArrayDataException {
//
//        log.info("Incoming data wrapping start");
//        HeaderData hd = createHeadData(income);
//        APPDATA appdata = createAppData(income);
//
//        log.info("Incoming data wrapping finish");
//    }
//
//    private APPDATA createAppData(byte[] income) throws NumberArrayDataException {
//        APPDATA appData = null;
//        if (income[ByteFixedPositions.PACKAGE_TYPE_INDEX] ==
//            ByteFixValues.TYPE_APPDATA && readFDLValidate.readFDL(income))
//            appData = (APPDATA) appDataCreator.create(income);
//        return appData;
//    }
//
//    private HeaderData createHeadData(byte[] income) throws NumberArrayDataException {
//        HeaderData hd = null;
//        // if (responseCode == ProcessingResultCodeConstants.EGTS_PC_OK) {
//        hd = (HeaderData) headerCreator.create(income);
//        // }
//        return hd;
//    }

//    private void response(byte[] income, byte code) throws IOException {
//        RESPONSE bdr = (RESPONSE) responseNormal.createNormalResponse(income, code);
//        log.info("Sending back response to BNSO start. \n Data: " + ArrayUtils.arrayPrintToScreen(bdr.getResponseBody()));
//        OutputStream output = socket.getOutputStream();
//        output.write(bdr.getResponseBody());
//        log.info("Sending back response to BNSO finish. ");
//        output.close();
//    }
//
//
//    private short calcPID(byte[] income) {
//        byte[] pid = new byte[2];
//        pid[0] = income[8];
//        pid[1] = income[7];
//        return ArrayUtils.calcShortFromArray(pid);
//    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private byte[] receive() throws IOException {
        log.info("Read from socket start");
        DataInputStream in = new DataInputStream(socket.getInputStream());
        int dataSize = in.available();
        byte[] out = new byte[dataSize];
        in.read(out);

        log.info("Income data: "+ArrayUtils.arrayPrintToScreen(out)
        +"  of length "+out.length);
        log.info("Read from socket finish");
        return out;

    }
}
