package tnt.egts.parser.cmmon.authService.authInfo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;

import tnt.egts.parser.response.recResponse.SrRecordResponse;
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
    public void prepareAuthData() {
        // SRT
        data= new byte[1];
        data[0]=subRecordType;

        //  SRL
         byte[] srl =
            ByteBuffer.wrap(ArrayUtils.shortToByteArray(subRecordLength)).array();
        srl = ArrayUtils.inverse(srl);
        data=ArrayUtils.joinArrays(data, srl);

        // SRD
        data = ArrayUtils.joinArrays(data, srRecResponse.getData());
    }
}
