package tnt.egts.parser.data;

import tnt.egts.parser.data.cmmon.store.IncomeDataStorage;

public interface Storage {

    IncomeDataStorage create(byte[] income);

}
