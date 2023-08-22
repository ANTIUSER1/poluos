package tnt.egts.parser.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentFinalCreate;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Service (StringFixedBeanNames.AUTH_RESPONSE_SERVICE_BEAN)
@Slf4j
public class AuthResponseService implements ResponseData {

    @Autowired
    @Qualifier (StringFixedBeanNames.DO_AUTH_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
    OutcomeIdentFinalCreate outcomeIdentCreate;

    @Override
    public void sendResponse(Socket socket, IncomeDataStorage store,
                             OutcomeIdent preparingOutcomeData, byte code) throws NumberArrayDataException {

        log.info("Sending back AUTH-response to BNSO start. \n  " + ArrayUtils.arrayPrintToScreen(preparingOutcomeData.getData()));
        preparingOutcomeData =
                outcomeIdentCreate.createAuthResponse(store, code);

        OutputStream output = null;
        try {
            output = socket.getOutputStream();
            output.write(preparingOutcomeData.getData());
            log.info("Sending back AUTH-response to BNSO finish. ");
        } catch (IOException e) {
            log.error("Error while response to  attempt");
            e.printStackTrace();
        }
    }
}
