package tnt.egts.parser.data.appdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tnt.egts.parser.util.ArrayUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Component
@Slf4j
  class APPDATAHelper {

    int pos=1;
    APPDATA   modify(APPDATA appdata){


        appdata.setOid(createOID(appdata ));
        appdata.setEvid(createEVID(appdata ));
        appdata.setTm(createTM(appdata ));
        appdata.setRst(createRST(appdata ));
        appdata.setRecordData(createRecData(appdata ));
        return appdata;
    }

    private byte[] createRecData(APPDATA appdata) {
        return ArrayUtils.getSubArrayToEnd(appdata.getContent(),pos);
    }

    private byte createRST(APPDATA appdata) {
        byte[] content = appdata.getContent();pos++;
return content[pos-1];
    }

    private int createTM(APPDATA appdata) {
        System.out.println("+++  tm +------ "+ ( appdata.getFlags().get("TM")  ) );
        if(appdata.getFlags().get(   "TM")) {
        byte[] content = appdata.getContent();
        byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 4);pos+=4;
        System.out.println("++++++++++++++++" + ArrayUtils.arrayPrintToScreen(out));
        return ByteBuffer.wrap(out).getInt(0);
    }     return 0;
    }

    private int createEVID(APPDATA appdata) {
        System.out.println("+++  evfe +------ "+ ( appdata.getFlags().get(
                "EVFE")  ) );
        if(appdata.getFlags().get(     "EVFE")) {
        byte[] content = appdata.getContent();
        byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 4);pos+=4;
        System.out.println("++++++++++++++++" + ArrayUtils.arrayPrintToScreen(out));
        return ByteBuffer.wrap(out).getInt(0);
    }  return 0;
    }

    private int createOID(APPDATA appdata) {
        System.out.println("+++  oid +------ "+ ( appdata.getFlags().get("OID")  ) );
        if(appdata.getFlags().get("OID")) {
            byte[] content = appdata.getContent();
            byte[] out = ArrayUtils.getFixedLengthSubArray(content, pos, 4);
            System.out.println("++++++++++++++++" + ArrayUtils.arrayPrintToScreen(out));pos+=4;
            return ByteBuffer.wrap(out).getInt(0);
        }
       return 0;
    }


}
