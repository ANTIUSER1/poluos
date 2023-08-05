package tnt.egts.parser.cmmon.authService.response.recResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.Authentication;

@Data
@Builder
@ToString
public class RecResponse implements Authentication {

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
