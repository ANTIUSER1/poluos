package tnt.egts.parser.data.authService.recordResponse;

import tnt.egts.parser.data.authService.Authentication;
import tnt.egts.parser.data.authService.CreateAuth;


public class RECORDRESPONSECreator implements CreateAuth {

    @Override
    public Authentication create(byte[] income) {
        RECORDRESPONSE recResp= RECORDRESPONSE.builder()


                .build();

        return null;
    }
}
