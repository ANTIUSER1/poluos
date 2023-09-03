package tnt.egts.parser.data.store;

import org.springframework.stereotype.Service;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service(StringFixedBeanNames.BNSO_DATA_STORAGE_SERVICE_BEAN)
public class BNSODataStorageService implements Storage {

    @Override
    public IncomeDataStorage create(byte[] income) throws NumberArrayDataException {


        return null;
    }
}
