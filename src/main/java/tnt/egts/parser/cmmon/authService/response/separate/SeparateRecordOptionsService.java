package tnt.egts.parser.cmmon.authService.response.separate;

import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
@Service
public class SeparateRecordOptionsService implements OutcomeIdentCreate {

    @Override
    public OutcomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        return null;
    }
}
