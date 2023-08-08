package tnt.egts.parser.cmmon.sendBack;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.util.ArrayUtils;

@Builder
@Data
@ToString
public class DoResponse implements OutcomeIdent {

    private byte[] responseHead;
    private   byte[] sfrd;
    private   byte[] data;
    private byte code;

    @Override
    public void createData() {
        short sffdLength= (short) sfrd.length;
        byte[] sfrdArray=ArrayUtils.shortToByteArray(sffdLength);
        responseHead[9]=code;
        responseHead[5]=sfrdArray[1]; responseHead[6]=sfrdArray[0];
        data= ArrayUtils.joinArrays(responseHead, sfrd);
    }
}
