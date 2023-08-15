package tnt.egts.parser.cmmon;

import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

/**
 * income data identification creates
 */
public interface IncomeIdentCreate {

    IncomeIdent  create(IncomeDataStorage storage) throws NumberArrayDataException;
}
