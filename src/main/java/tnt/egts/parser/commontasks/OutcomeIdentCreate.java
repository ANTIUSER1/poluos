package tnt.egts.parser.commontasks;

import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

public interface OutcomeIdentCreate {

    OutcomeIdent   create(IncomeDataStorage storage ) throws NumberArrayDataException;
}
