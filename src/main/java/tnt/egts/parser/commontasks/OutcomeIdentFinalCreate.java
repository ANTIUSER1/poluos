package tnt.egts.parser.commontasks;

import tnt.egts.parser.data.store.ResponseDataStorage;
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
    OutcomeIdent createResponse(ResponseDataStorage storage, byte code) throws NumberArrayDataException;

}
