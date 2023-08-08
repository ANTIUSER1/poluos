package tnt.egts.parser.cmmon;

import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

public interface OutcomeIdentFinalCreate {

    OutcomeIdent   create(IncomeDataStorage storage, byte code) throws NumberArrayDataException;
}
