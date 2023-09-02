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

@Service (StringFixedBeanNames.TELEDATA_RESPONSE_SERVICE_BEAN)
@Slf4j
public class TeleDataResponseService extends ResponseServiceAbstract implements ResponseData {

    @Autowired
    @Qualifier (StringFixedBeanNames.DO_AUTH_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
    OutcomeIdentFinalCreate outcomeIdentCreate;

    @Override
    public void sendResponse(Socket socket, IncomeDataStorage store,
                             OutcomeIdent  preparingOutcomeData, byte code) throws NumberArrayDataException {
        log.info("Sending back TELEDATA-response to BNSO start. \n  " + ArrayUtils.arrayPrintToScreen(preparingOutcomeData.getData()));

        System.out.println(".......TELE=DATA response......."  +
                           "\n .0:::  "+ ArrayUtils.arrayPrintToScreen(preparingOutcomeData.getData())
        );
        preparingOutcomeData =
                outcomeIdentCreate.createResponse(store, code);


        System.out.println(".   ......TELE=DATA response......."  + "\n   " +
                           "1:::  "+ ArrayUtils.arrayPrintToScreen(preparingOutcomeData.getData())

        );
        sendData(socket,preparingOutcomeData.getData() );

System.out.println();
System.out.println();
System.out.println();
System.out.println();
System.out.println();
System.out.println();
System.out.println("***************************************************");
System.out.println();
    }
}
