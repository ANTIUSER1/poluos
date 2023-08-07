package tnt.egts.parser.cmmon.authService.response.recResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.Authentication;
import tnt.egts.parser.cmmon.OutcomeIdent;

@Data
@Builder
@ToString
public class SrRecResponse implements OutcomeIdent {

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
}
