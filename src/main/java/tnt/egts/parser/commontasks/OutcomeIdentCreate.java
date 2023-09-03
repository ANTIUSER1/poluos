package tnt.egts.parser.commontasks;

import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

public interface OutcomeIdentCreate {

    OutcomeIdent   create(ResponseDataStorage storage ) throws NumberArrayDataException;
}
