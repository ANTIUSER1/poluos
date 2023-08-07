package tnt.egts.parser.cmmon.authService.authInfo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.authService.response.recResponse.SrRecResponse;

@Builder
@Data
@ToString
public class AuthRecordData  implements OutcomeIdent {

    /**
     * SRT
     */
    private  byte subRecordType;

    /**
     * SRL
     */
    private short subRecordLength;
private SrRecResponse srRecResponse;

}
