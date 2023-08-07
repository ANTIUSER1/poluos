package tnt.egts.parser.cmmon.authService.response.separate;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.authService.authInfo.AuthRecordData;

@Builder
@Data
@ToString
public class SeparateRecord  implements OutcomeIdent  {

    /**
     * RL
     */
    private short recordLength;

    /**
     * RN

     */
    private  short recordNumber;

    /**
     * record flags
     */
    private byte             flags;

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
    private AuthRecordData recordData;
}
