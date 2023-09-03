package tnt.egts.parser.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentFinalCreate;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.net.Socket;

@Service (StringFixedBeanNames.AUTH_RESPONSE_SERVICE_BEAN)
@Slf4j
public class AuthResponseService  extends ResponseServiceAbstract  implements ResponseData {

    @Autowired
    @Qualifier (StringFixedBeanNames.DO_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
    OutcomeIdentFinalCreate outcomeIdentCreate;

    @Override
    public void sendResponse(Socket socket, ResponseDataStorage store,
                             OutcomeIdent preparingOutcomeData, byte code) throws NumberArrayDataException {

        log.info("Sending back AUTH-response to BNSO start. \n  " + ArrayUtils.arrayPrintToScreen(preparingOutcomeData.getData()));
        preparingOutcomeData =
                outcomeIdentCreate.createResponse(store, code);

        sendData(socket,preparingOutcomeData.getData() );
    }
}
