package tnt.egts.parser.cmmon.authService.authInfo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.authService.response.recResponse.SrRecordResponse;
import tnt.egts.parser.util.ArrayUtils;

import java.nio.ByteBuffer;

@Builder
@Data
@ToString
public class AuthRecordData implements OutcomeIdent {

    /**
     * SRT
     */
    private byte subRecordType;

    /**
     * SRL
     */
    private short subRecordLength;

    private SrRecordResponse srRecResponse;

    private byte[] data;

    @Override
    public void createData() {
        data = ByteBuffer.wrap(ArrayUtils.shortToByteArray(subRecordLength)).array();
        data = ArrayUtils.inverse(data);
        data = ArrayUtils.joinArrays(data, srRecResponse.getData());
        data = ArrayUtils.addByteToStart(data, subRecordType);
    }
}
