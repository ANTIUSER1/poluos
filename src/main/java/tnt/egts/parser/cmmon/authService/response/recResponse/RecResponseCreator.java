package tnt.egts.parser.cmmon.authService.response.recResponse;

import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;

@Service
public class RecResponseCreator implements OutcomeIdentCreate {

    @Override
    public OutcomeIdent create(IncomeDataStorage storage) {
        return  SrRecResponse.builder().build();
    }
}
