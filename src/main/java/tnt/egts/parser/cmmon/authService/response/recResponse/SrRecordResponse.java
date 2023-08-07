package tnt.egts.parser.cmmon.authService.response.recResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.util.ArrayUtils;

import java.nio.ByteBuffer;

@Data
@Builder
@ToString
public class SrRecordResponse implements OutcomeIdent {

    /**
     * CRN
     */
    private  short confirmRN;

    /**
     * RST
     */
    private byte recStatus;

    /**
     * data for export to BNSO
     */
    private byte[] data;

    @Override
    public void createData() {
data =  ByteBuffer.wrap(ArrayUtils.shortToByteArray(confirmRN)).array();
data=ArrayUtils.inverse(data);
data=ArrayUtils.addByteToTail(data,recStatus);
        System.out.println(data.length);
    }
}
