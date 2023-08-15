package tnt.egts.parser.data;

import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

public interface Storage {

     IncomeDataStorage create(byte[] income) throws NumberArrayDataException;


}
