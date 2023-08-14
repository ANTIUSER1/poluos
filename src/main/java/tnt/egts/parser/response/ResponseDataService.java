package tnt.egts.parser.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentFinalCreate;
import tnt.egts.parser.cmmon.sendBack.DoResponse;
import tnt.egts.parser.cmmon.sendBack.DoResponse;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Service
@Slf4j
public class ResponseDataService  implements ResponseData{

    @Autowired
    @Qualifier (StringFixedBeanNames.AUTH_RESPONSE_SEND_BEAN)
    OutcomeIdentFinalCreate outcomeIdentCreate;

    @Autowired
    CRC crc;

    private Socket socket;

    private OutcomeIdent outcomeAuthData;

    public void sendResponse() {
        DoResponse resp = (DoResponse) outcomeAuthData;
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

}
