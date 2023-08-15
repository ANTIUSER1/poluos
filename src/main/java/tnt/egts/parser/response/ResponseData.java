package tnt.egts.parser.response;

import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

import java.net.Socket;

public interface ResponseData {
    void sendResponse(Socket socket, IncomeDataStorage store , OutcomeIdent preparingOutcomeData,
                      byte code) throws NumberArrayDataException;
}
