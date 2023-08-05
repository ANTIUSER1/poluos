package tnt.egts.parser.cmmon.authService.response.recResponse;

import tnt.egts.parser.cmmon.authService.Authentication;
import tnt.egts.parser.cmmon.authService.CreateAuth;
import tnt.egts.parser.cmmon.authService.response.recResponse.RECORDRESPONSE;


public class RECORDRESPONSECreator implements CreateAuth {

    @Override
    public Authentication create(byte[] income) {
        RECORDRESPONSE recResp= RECORDRESPONSE.builder()


                .build();

        return null;
    }
}
