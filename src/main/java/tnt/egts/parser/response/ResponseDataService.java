package tnt.egts.parser.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.data.store.ServiceType;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.NumberUtils;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.net.Socket;

@Service (StringFixedBeanNames.RESPONSE_DATA_BEAN)
@Slf4j
public class ResponseDataService implements ResponseData {

    @Autowired
    CRC crc;

    @Autowired
    @Qualifier (StringFixedBeanNames.AUTH_RESPONSE_SERVICE_BEAN)
    private ResponseData authResponse;

    @Autowired
    @Qualifier (StringFixedBeanNames.TELEDATA_RESPONSE_SERVICE_BEAN)
    private ResponseData teleDataResponse;

    public void sendResponse(Socket socket, ResponseDataStorage store,
                             OutcomeIdent  preparingOutcomeData, byte code) throws NumberArrayDataException {
System.out.println("SERVICE TYPE");
System.out.println("SERVICE TYPE  ****************************************");
System.out.println("SERVICE TYPE  "+store.getServiceType());
System.out.println("SERVICE TYPE  ****************************************");
System.out.println("SERVICE TYPE");
System.out.println("SERVICE TYPE");
        if (store.getServiceType().equals(ServiceType.TELEDATA_SERVICE)) {
            log.info("Response Work with TELEDATA Service start");
            teleDataResponse.sendResponse(socket, store, preparingOutcomeData, code);
            log.info(" ResponseWork with TELEDATA Service stop");
        }

        if (store.getServiceType().equals(ServiceType.AUTH_SERVICE)) {
            log.info("Response Work with AUTH Service start");
            authResponse.sendResponse(socket, store, preparingOutcomeData, code);
            log.info("Response Work with AUTH Service stop");
        }

        //testOutSendData(preparingOutcomeData.getData());
    }

    private void testOutSendData(byte[] data) {
        System.out.println();
        System.out.println("************** TEST  OUTPUT  " + "*********************");
        System.out.println();
        System.out.println("OUTPUT:  " + ArrayUtils.arrayPrintToScreen(data) + " LENGTH: " + data.length);
        System.out.println();
        System.out.println();
        byte bt = data[3];
        System.out.println("  HL: " + bt);
        byte[] inf = ArrayUtils.getFixedLengthSubArray(data, 0, bt);
        System.out.println("HEAD:  " + ArrayUtils.arrayPrintToScreen(inf));

        bt = data[9];
        System.out.println("PT:   " + bt);

        byte[] fdl = new byte[2];
        fdl[0] = data[6];
        fdl[1] = data[5];
        short fdlDat;
        try {
            fdlDat = NumberUtils.byteArrayToShort(fdl);
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
        System.out.println("FDL:  " + ArrayUtils.arrayPrintToScreen(fdl) + "  AS" + "  NUM:  " + fdlDat + "  EXPETED: " + (data.length - data[3] - 2));

        byte[] pid = new byte[2];
        pid[0] = data[8];
        pid[1] = data[7];
        short pidDat;
        try {
            pidDat = NumberUtils.byteArrayToShort(pid);
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
        System.out.println("PID:  " + ArrayUtils.arrayPrintToScreen(pid) + "  AS" + "  NUM:  " + pidDat);

//        bt=data[  data[3]-1];
//        System.out.println("HCS:   "+bt);

        byte[] sfrd = ArrayUtils.getFixedLengthSubArray(data, data[3], fdlDat);
        System.out.println("  sfrd: " + ArrayUtils.arrayPrintToScreen(sfrd) + "  LEN: " + sfrd.length);

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
