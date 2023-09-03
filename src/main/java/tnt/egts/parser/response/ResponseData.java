package tnt.egts.parser.response;

import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

import java.net.Socket;

public interface ResponseData {
    void sendResponse(Socket socket, ResponseDataStorage store , OutcomeIdent preparingOutcomeData,
                      byte code) throws NumberArrayDataException;
}
