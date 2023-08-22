package tnt.egts.parser.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.net.Socket;

@Service (StringFixedBeanNames.TELEDATA_RESPONSE_SERVICE_BEAN)
@Slf4j
public class TeleDataResponseService implements ResponseData {

    @Override
    public void sendResponse(Socket socket, IncomeDataStorage store,
                             OutcomeIdent  preparingOutcomeData, byte code) throws NumberArrayDataException {
System.out.println(".......TELE=DATA response........");
    }
}
