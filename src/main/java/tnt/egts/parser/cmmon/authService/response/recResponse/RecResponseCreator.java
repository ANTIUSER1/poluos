package tnt.egts.parser.cmmon.authService.response.recResponse;

import tnt.egts.parser.cmmon.Authentication;
import tnt.egts.parser.cmmon.CreateAuth;


public class RecResponseCreator implements CreateAuth {

    @Override
    public Authentication create(byte[] income) {
        RecResponse recResp= RecResponse.builder()


                .build();

        return null;
    }
}
