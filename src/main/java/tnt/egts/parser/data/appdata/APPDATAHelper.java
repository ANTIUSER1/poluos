package tnt.egts.parser.data.appdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnt.egts.parser.util.ArrayUtils;

import java.nio.ByteBuffer;

@Component
@Slf4j
class APPDATAHelper {

    @Autowired
    private APPDATAService appdataService;

    private int pos = 0;


    APPDATA modify(APPDATA appdata) {
        appdata.setRecLength(createRecLength(appdata));
        appdata.setRecNum(createRecNum(appdata));
        appdata.setOid(createOID(appdata));
        appdata.setEvid(createEVID(appdata));
        appdata.setTm(createTM(appdata));
        appdata.setRst(createRST(appdata));
        appdata.setRecordData(createRecData(appdata));

        appdataService.setAppDataRD(appdata.getRecordData());
        return appdata;
    }

    private short createRecLength(APPDATA appdata) {
        byte[] content = appdata.getContent();
        byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 2);
        pos += 2;

        return ByteBuffer.wrap(ArrayUtils.inverse(out)).getShort(0);
    }

    private short createRecNum(APPDATA appdata) {
        byte[] content = appdata.getContent();
        byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 2);
        pos += 2;
        return ByteBuffer.wrap(ArrayUtils.inverse(out)).getShort(0);
    }

    private byte[] createRecData(APPDATA appdata) {
        byte[] out = ArrayUtils.getSubArrayFromTo(appdata.getContent(), pos, appdata.getRecLength());
        pos += appdata.getRecLength();
        return out;
//        return ArrayUtils.getSubArrayToEnd(appdata.getContent(),pos);
    }

    private byte createRST(APPDATA appdata) {
        byte[] content = appdata.getContent();
        pos++;
        return content[pos - 1];
    }

    private int createTM(APPDATA appdata) {
        if (appdata.getFlags().get("TM")) {
            byte[] content = appdata.getContent();
            byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 4);
            pos += 4;
            System.out.println("++++++++++++++++" + ArrayUtils.arrayPrintToScreen(out));
            return ByteBuffer.wrap(ArrayUtils.inverse(out)).getInt(0);
        }
        return 0;
    }

    private int createEVID(APPDATA appdata) {
        if (appdata.getFlags().get("EVFE")) {
            byte[] content = appdata.getContent();
            byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 4);
            pos += 4;
            System.out.println("++++++++++++++++" + ArrayUtils.arrayPrintToScreen(out));
            return ByteBuffer.wrap(ArrayUtils.inverse(out)).getInt(0);
        }
        return 0;
    }

    private int createOID(APPDATA appdata) {
        if (appdata.getFlags().get("OID")) {
            byte[] content = appdata.getContent();
            byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 4);
            System.out.println("++++++++++++++++" + ArrayUtils.arrayPrintToScreen(out));
            pos += 4;
            return ByteBuffer.wrap(out).getInt(0);
        }
        return 0;
    }


}
