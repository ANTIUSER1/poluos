package tnt.egts.parser.cmmon.authService.response.separate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
@Service
@Slf4j
public class SeparateRecordOptionsService implements OutcomeIdentCreate {

    @Override
    public OutcomeIdent create(IncomeDataStorage storage ) throws NumberArrayDataException {
        log.info("Storage  separate optionData start");

        log.info("Storage  separate optionData finish  ....");
        return null;
    }
}
