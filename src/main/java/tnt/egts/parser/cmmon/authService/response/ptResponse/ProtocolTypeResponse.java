package tnt.egts.parser.cmmon.authService.response.ptResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecord;

@Builder
@Data
@ToString
public class ProtocolTypeResponse implements OutcomeIdent {

    /**
     * RPID
     */
    private  short  responsePacketID;

    /**
     * pr
     */
    private byte processingResult;

    /**
     * additional separate record
     */
    private SeparateRecord  separateRecord;

    @Override
    public void createData() {

    }
}
