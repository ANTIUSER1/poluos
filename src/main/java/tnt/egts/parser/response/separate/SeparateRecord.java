package tnt.egts.parser.response.separate;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.data.auth.AuthRecordData;
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
        //RL
        data=ArrayUtils.inverse(
                ArrayUtils.shortToByteArray((short) authRecordData.getData().length ) );

        //RN
        byte[] rn=ArrayUtils.shortToByteArray(recordNumber);
        rn=ArrayUtils.inverse(rn);
        data=ArrayUtils.joinArrays(data, rn);

        // FLAGS   === RFL
        data=ArrayUtils.addByteToTail(data, flags);

        // SST
        data=ArrayUtils.addByteToTail(data, sourceServicceType);

        System.out.println(   "     SST:");
        System.out.println(   "     SST:");
        System.out.println(   "     SST:   "+sourceServicceType);
        System.out.println(   "     SST:   "+sourceServicceType);
        System.out.println(   "     SST:   "+sourceServicceType);
        System.out.println(   "     SST:");
        System.out.println(   "     SST:");
        System.out.println(   "     SST:");
        //RST
        data=ArrayUtils.addByteToTail(data, recipientServicceType);

        //RD
        data=ArrayUtils.joinArrays(data,authRecordData.getData());

    }
}
