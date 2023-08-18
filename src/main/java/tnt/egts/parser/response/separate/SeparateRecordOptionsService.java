package tnt.egts.parser.response.separate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentCreate;
import tnt.egts.parser.data.store.IncomeDataStorage;
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
