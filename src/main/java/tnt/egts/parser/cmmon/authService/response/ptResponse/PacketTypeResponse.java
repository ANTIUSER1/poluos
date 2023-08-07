package tnt.egts.parser.cmmon.authService.response.ptResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.authService.authInfo.AuthRecordData;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecord;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecordService;
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
    public void createData() {
        data=ArrayUtils.shortToByteArray((short) separateRecord.getData().length);
//        data = ArrayUtils.shortToByteArray(responsePacketID);
        data = ArrayUtils.inverse(data);
        data = ArrayUtils.addByteToTail(data, processingResult);
        data=ArrayUtils.joinArrays(data, separateRecord.getData());
        System.out.println(data.length);
    }
}
