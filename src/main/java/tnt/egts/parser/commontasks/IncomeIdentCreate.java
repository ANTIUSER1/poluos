package tnt.egts.parser.commontasks;

import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

/**
 * income data identification creates
 */
public interface IncomeIdentCreate {

    IncomeIdent  create(ResponseDataStorage storage) throws NumberArrayDataException;
}
