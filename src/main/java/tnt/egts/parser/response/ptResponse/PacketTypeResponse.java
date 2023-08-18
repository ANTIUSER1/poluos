package tnt.egts.parser.response.ptResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.response.separate.SeparateRecord;
import tnt.egts.parser.util.ArrayUtils;

@Builder
@Data
@ToString
public class PacketTypeResponse implements OutcomeIdent {

    /**
     * RPID
     */
    private short responsePacketID;

    /**
     * pr
     */
    private byte processingResult;

    /**
     * additional separate record
     */
    private SeparateRecord separateRecord;

    private byte[] data;

    @Override
    public void prepareAuthData() {
        //   RPID
        data = ArrayUtils.shortToByteArray(responsePacketID);
        //RPID  continue -- add
        data = ArrayUtils.inverse(data);

        // PR   === EGTS_PC_OK
        data = ArrayUtils.addByteToTail(data, (byte) 0);

        //  SEPARATE_RECORD
        data = ArrayUtils.joinArrays(data, separateRecord.getData());
    }
}
