package tnt.egts.parser.cmmon;

import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

public interface OutcomeIdentCreate {

    OutcomeIdent   create(IncomeDataStorage storage) throws NumberArrayDataException;
}
