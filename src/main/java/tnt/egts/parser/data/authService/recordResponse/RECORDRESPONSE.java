package tnt.egts.parser.data.authService.recordResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.data.authService.Authentication;

@Data
@Builder
@ToString
public class RECORDRESPONSE implements Authentication {

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
