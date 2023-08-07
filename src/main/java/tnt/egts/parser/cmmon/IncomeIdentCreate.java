package tnt.egts.parser.cmmon;

import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

public interface IncomeIdentCreate {

    IncomeIdent  create(IncomeDataStorage storage) throws NumberArrayDataException;
}