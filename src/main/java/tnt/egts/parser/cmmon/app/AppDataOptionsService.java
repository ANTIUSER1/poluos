package tnt.egts.parser.cmmon.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.IncomeIdent;
import tnt.egts.parser.cmmon.IncomeIdentCreate;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

@Service
@Slf4j
public class AppDataOptionsService implements IncomeIdentCreate {

    @Override
    public IncomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        log.info("Storage  income head options Data start");

        log.info("Storage  income appdata option  Data finish");
        return null;
    }
}
