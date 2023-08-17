package tnt.egts.parser.response.separate;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.authService.authInfo.AuthRecordData;
import tnt.egts.parser.util.ArrayUtils;

@Builder
@Data
@ToString
public class SeparateRecord implements OutcomeIdent {

    /**
     * RL
     */
    private short recordLength;

    /**
     * CRN
     */
    private short recordNumber;

    /**
     * record flags
     */
    private byte flags;

    /**
     * sep rec options
     */
    private SeparateRecordOptions options;

    /**
     * SST
     */
    private byte sourceServicceType;

    /**
     * RST
     */
    private byte recipientServicceType;

    /**
     * RD
     */
    private AuthRecordData authRecordData;

byte[] data;

    @Override
    public void prepareAuthData() {
        data= ArrayUtils.inverse(ArrayUtils.shortToByteArray((short) authRecordData.getData().length));
        data=ArrayUtils.inverse(data);
        byte[] rn=ArrayUtils.shortToByteArray(recordNumber);
        data=ArrayUtils.inverse(data);
        data=ArrayUtils.joinArrays(data, rn);
        data=ArrayUtils.addByteToTail(data, flags);
        data=ArrayUtils.addByteToTail(data, sourceServicceType);
        data=ArrayUtils.addByteToTail(data, recipientServicceType);
        data=ArrayUtils.joinArrays(data,authRecordData.getData());

    }
}
