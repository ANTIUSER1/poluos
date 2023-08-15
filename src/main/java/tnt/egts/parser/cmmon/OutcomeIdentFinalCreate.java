package tnt.egts.parser.cmmon;

import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

/**
 * finalization formatting data for response
 */
public interface OutcomeIdentFinalCreate {

    /**
     * create final data for auth-response
     * @param storage
     * @param code
     * @return
     * @throws NumberArrayDataException
     */
    OutcomeIdent createAuthResponse(IncomeDataStorage storage, byte code) throws NumberArrayDataException;
}
