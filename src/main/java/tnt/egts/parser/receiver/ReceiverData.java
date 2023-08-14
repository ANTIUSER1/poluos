package tnt.egts.parser.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentFinalCreate;
import tnt.egts.parser.cmmon.sendBack.DoResponse;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Component
@Slf4j
public class ReceiverData implements Runnable {

    @Autowired
    @Qualifier ("readyToSend")
    OutcomeIdentFinalCreate outcomeIdentCreate;

    @Autowired
    CRC crc;

    @Autowired
    private boolean isValidatePacket;

    private Socket socket;

//    @Autowired
//    private ComponentsStoring componentsStoring;

   // @Autowired
    private OutcomeIdent outcomeData;

//    @Autowired
//    private HeadService headService;
//
//    @Autowired
//    private CommonAPPService commonAPPService;

    //**********************************************************

    @Autowired
    private Storage storage;

    @Autowired
    private ByteAnalizer byteAnalizer;


    private int responseCode;

    private volatile long msgNO;

    private volatile long errorN0;

    @Override
    public void run() {
        log.info("work on request from " + socket.getRemoteSocketAddress() +
                 "  start");
        try {
            byte[] income = receive();

            throwReceivedInfoGlobalError(income);
            //  income=fakeByte(income );
            log.info("Received data from BNSO. Data length: " + income.length);
            log.debug("DATA:\n  " + ArrayUtils.arrayPrintToScreen(income) + "\n");

            if (isValidatePacket) {
                responseCode = byteAnalizer.analize(income);
                if (responseCode < 0) {
                    errorN0++;
                    log.error("Data are invalid in received package from " + socket.getRemoteSocketAddress());
                    throw new IncorrectDataException(
                            "Processing terminated unexpectedly due to a broken data packet "
                    );
                }
            }

            log.info("Response code " + responseCode);

            //receiveData ; //
            ///*******************
            dataTransform(income, (byte) responseCode);
            sendResponse();
//
            msgNO++;
            log.info("work on request finish. Correct steps: " + msgNO
                     + "; errors: " + errorN0);
        } catch (Exception e) {
            log.error("Error while data transform: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private byte[] fakeByte(byte[] income) {
        income[2] = 4;
        income[ByteFixPositions.PACKAGE_TYPE_INDEX] = 1;
        byte[] head = ArrayUtils.getFixedLengthSubArray(income, 0, 10);
        System.out.println("HEAD:  " + ArrayUtils.arrayPrintToScreen(head) + " " +
                           "CRC8: " + crc.calculate8(head));
        income[10] = (byte) crc.calculate8(head);
        return income;
    }

    private void sendResponse() {
        DoResponse resp = (DoResponse) outcomeData;
        log.info("Sending back response to BNSO start. \n Data: " + ArrayUtils.arrayPrintToScreen(resp.getData()) + " of length " + resp.getData().length);
        OutputStream output = null;
        try {
            output = socket.getOutputStream();
            output.write(resp.getData());

            log.info("Sending back response to BNSO finish. ");
           // testOutSendData(resp.getData());
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
        System.out.println("OUTPUT:  " + ArrayUtils.arrayPrintToScreen(data) +
                           " LENGTH: " + data.length);
        System.out.println();
        System.out.println();
        byte bt = data[3];
        System.out.println("  HL: " + bt);
//        byte[] inf=ArrayUtils.getFixedLengthSubArray(data,0, bt);
//        System.out.println( "HEAD:  "+ArrayUtils.arrayPrintToScreen(inf));

        bt = data[9];
        System.out.println("PT:   " + bt);

        byte[] fdl = new byte[2];
        fdl[0] = data[6];
        fdl[1] = data[5];
        short fdlDat;
        try {
            fdlDat = ArrayUtils.byteArrayToShort(fdl);
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
        System.out.println("FDL:  " + ArrayUtils.arrayPrintToScreen(fdl) + "  AS" +
                           "  NUM:  " + fdlDat + "  EXPETED: " + (data.length - data[3] - 2));

        byte[] pid = new byte[2];
        pid[0] = data[8];
        pid[1] = data[7];
        short pidDat;
        try {
            pidDat = ArrayUtils.byteArrayToShort(pid);
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
        System.out.println("PID:  " + ArrayUtils.arrayPrintToScreen(pid) + "  AS" +
                           "  NUM:  " + pidDat);

//        bt=data[  data[3]-1];
//        System.out.println("HCS:   "+bt);

        byte[] sfrd = ArrayUtils.getFixedLengthSubArray(data, data[3], fdlDat);
        System.out.println("  sfrd: " + ArrayUtils.arrayPrintToScreen(sfrd) +
                           "  LEN: " + sfrd.length);

        long crc16 = crc.calculate16(sfrd);
        short crcShort = (short) crc16;
        byte[] crcArr = ArrayUtils.shortToByteArray(crcShort);
        System.out.println("C CRC16:: " + crc16 + "  HEX  " + Long.toHexString(crc16));
        System.out.println("CRC-short:  " + crcShort + "  as arr:  " + ArrayUtils.arrayPrintToScreen(crcArr) + "   HEX:  " + Integer.toHexString(crcShort));
//testCRC16();
        System.out.println();
        System.out.println("****************TEST");
        System.out.println();
    }

    private void testCRC16() {
        byte[] tb = {2, 5};
        System.out.println("***========CRC-16 test =============");
        long crc16 = crc.calculate16(tb);
        System.out.println(" crc-16  16:  " + crc16 + " HEX:  " + Integer.toHexString((int) crc16));
        System.out.println("***========CRC-16 test =============");

    }

    private void dataTransform(byte[] income, byte code) throws NumberArrayDataException {
        log.info("Storage  income Data start");
        IncomeDataStorage store = storage.create(income);

        outcomeData = outcomeIdentCreate.create(store, code);
        log.info("Storage  income Data finish");
    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private byte[] receive() throws IOException, NumberArrayDataException {
        int dsize;
        byte[] resTest;
        byte[] result;
        BufferedInputStream in =
                new BufferedInputStream(socket.getInputStream());

        resTest = readFromStream(16);
        if (resTest.length < 4) {
            log.error("Invalid length  :  " + resTest.length);
            return new byte[0];
        } else if (resTest.length < 16) {
            log.error("Invalid length :  " + resTest.length);
            return new byte[0];
        } else {
            byte[] shortArray = ArrayUtils.getFixedLengthSubArray(resTest, 5
                    , 2);
            short fdl = ArrayUtils.byteArrayInverseToShort(shortArray);
            if (resTest[3]  <=0 || fdl<= 0   ) {
                errorN0++;
                log.error("Data are invalid in received package from " + socket.getRemoteSocketAddress());
                log.error(" Details: the Value of HeaderLength is 0 or " +
                          "the length of SFRD is 0 ");
                throw new IncorrectDataException(
                        "Processing terminated unexpectedly due to a broken data packet "+
                        ArrayUtils.arrayPrintToScreen(resTest)+ "\n"
                );
            }
            dsize = resTest[3] + fdl + 2;

            result = new byte[dsize - resTest.length];
            in.read(result);
            result = ArrayUtils.joinArrays(resTest, result);
            if(result.length<dsize ) {
                log.error("Invalid length :  " + result.length);
                return new byte[0];
            }
        }
       return result;
    }


    private byte[] readFromStream(int length) throws IOException {
        byte[] result = new byte[length];
        InputStream inTest = socket.getInputStream();
        inTest.read(result);
throwReceivedInfoGlobalError(result);
        return result;
    }

    private void throwReceivedInfoGlobalError(byte[] income){

        if (income == null || income.length < 3 || income.length < 7) {
            errorN0++;
            log.error("Null data received, or income data is empty" + socket.getRemoteSocketAddress());
            throw new IncorrectDataException(
                    "Processing can not be proceed on corrupt data " +
                    ArrayUtils.arrayPrintToScreen(income)+ "\n"
            );

        }
    }
}
