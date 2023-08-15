package tnt.egts.parser.cmmon.sendBack;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.util.ArrayUtils;

@Builder
@Data
@ToString
@Slf4j
public class PrepareedResponseData implements OutcomeIdent {

    private byte[] responseHead;
    /**
     * only PackageHead -- скс8=free
     */
    private byte[] properPackageHeader;
    private   byte[] sfrd;
    private   byte[] data;
    private byte code ;

    @Override
    public void prepareAuthData() {
        log.info("Create Answer data start");
        short sffdLength= (short) sfrd.length;
        byte[] sfrdArray=ArrayUtils.shortToByteArray(sffdLength);
        responseHead[9]=code;
        responseHead[5]=sfrdArray[1]; responseHead[6]=sfrdArray[0];
         properPackageHeader=ArrayUtils.getFixedLengthSubArray(responseHead,0
                 , responseHead.length-1);
        data= ArrayUtils.joinArrays(responseHead, sfrd);
System.out.println("3333333333333333333333333");
System.out.println("3333333333333333333333333");
System.out.println("3  " +ArrayUtils.arrayPrintToScreen(data)+" \n");
System.out.println("3333333333333333333333333");
System.out.println("3333333333333333333333333");
        log.info("Answer Data: \n "+ArrayUtils.arrayPrintToScreen(data)+" \n");
        log.info("Create Answer data finish");
    }
}
