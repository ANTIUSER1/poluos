package tnt.egts.parser.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.app.CommonAPPService;
import tnt.egts.parser.cmmon.hd.HeadService;
import tnt.egts.parser.cmmon.store.ComponentsStoring;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.data.ConvertIncomingData;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.data.appdata.APPDATA;
import tnt.egts.parser.data.header.HeaderData;
import tnt.egts.parser.data.response.RESPONSE;
import tnt.egts.parser.data.validation.ReadFDLValidate;
import tnt.egts.parser.data.validation.ResponseNormalCreate;
import tnt.egts.parser.errors.ConnectionException;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Component
@Slf4j
public class ReceiverData implements Runnable {

    @Autowired
    @Qualifier ("pt")
    OutcomeIdentCreate testIF;

    private Socket socket;

    @Autowired
    private ComponentsStoring componentsStoring;

    @Autowired
    private Storage storage;

    @Autowired
    private HeadService headService;

    @Autowired
    private CommonAPPService commonAPPService;

    //**********************************************************

    @Autowired
    private ByteAnalizer byteAnalizer;

    @Autowired
    @Qualifier (StringFixedBeanNames.HEADER_CREATOR_BEAN)
    private ConvertIncomingData headerCreator;

    @Autowired
    @Qualifier (StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
    private ConvertIncomingData appDataCreator;

    @Autowired
    private ResponseNormalCreate responseNormal;

    @Autowired
    private ReadFDLValidate readFDLValidate;

    private byte responseCode;

    @Override
    public void run() {
        log.info("work on request start");
        try {
            byte[] income = receive();
            //receiveData(); //
            if (income == null) {
                log.error("Null data received");
                return;
            }
            dataTransform(income);
            /**
             * possibly omit in future --- will be seen
             */
            //  response(income, responseCode);
            log.info("work on request finish");
        } catch (IOException | IncorrectDataException e) {
            log.info("Receiving broken  " + e.getCause());
            throw new ConnectionException(e.getMessage());
        } catch (NumberArrayDataException e) {
            throw new RuntimeException(e);
        }
    }

    private void dataTransform(byte[] income) throws NumberArrayDataException {
        log.info("Storage  income Data start");
        IncomeDataStorage store = storage.create(income);

//        CommonAPPDATA cnd = commonAPPService.create(store);
//        componentsStoring.append(cnd);
//        Head head = (Head) headService.create(store);
//        componentsStoring.append(head);
//        System.out.println();
//        System.out.println(componentsStoring);
        System.out.println();
        System.out.println();
        System.out.println("TESTIF");
        System.out.println("TESTIF");
        System.out.println("TESTIF");

        OutcomeIdent testOO = testIF.create(store);

        System.out.println(testOO);

        // 3--- 6---
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        log.info("Storage  income Data finish");
    }

    private byte[] receiveData() throws IOException, NumberArrayDataException {
        log.info("Receiving starts ");
        byte[] income = receive();
        if (income.length == 0) {
            log.error("Incoming data is too small ");
            return null;
        }
        log.info("Received: " + income.length + " bytes {"
                 + ArrayUtils.arrayPrintToScreen(income) + "}");

        short pid = calcPID(income);
        log.info("PID: [" + income[7] + "  " + income[8] + "] ( " + pid + " ) ");
        responseCode = byteAnalizer.analize(income);
        log.info(" Response. CODDE " + responseCode);
        incomingsCreate(income);
        log.info("Receiving finish ");
        return income;
    }

    private void incomingsCreate(byte[] income) throws NumberArrayDataException {

        log.info("Incoming data wrapping start");
        HeaderData hd = createHeadData(income);
        APPDATA appdata = createAppData(income);

        log.info("Incoming data wrapping finish");
    }

    private APPDATA createAppData(byte[] income) throws NumberArrayDataException {
        APPDATA appData = null;
        if (income[ByteFixedPositions.PACKAGE_TYPE_INDEX] ==
            ByteFixValues.TYPE_APPDATA && readFDLValidate.readFDL(income))
            appData = (APPDATA) appDataCreator.create(income);
        return appData;
    }

    private HeaderData createHeadData(byte[] income) throws NumberArrayDataException {
        HeaderData hd = null;
        // if (responseCode == ProcessingResultCodeConstants.EGTS_PC_OK) {
        hd = (HeaderData) headerCreator.create(income);
        // }
        return hd;
    }


    private void response(byte[] income, byte code) throws IOException {
        RESPONSE bdr = (RESPONSE) responseNormal.createNormalResponse(income, code);
        log.info("Sending back response to BNSO start. \n Data: " + ArrayUtils.arrayPrintToScreen(bdr.getResponseBody()));
        OutputStream output = socket.getOutputStream();
        output.write(bdr.getResponseBody());
        log.info("Sending back response to BNSO finish. ");
        output.close();
    }


    private short calcPID(byte[] income) {
        byte[] pid = new byte[2];
        pid[0] = income[8];
        pid[1] = income[7];
        return ArrayUtils.calcShortFromArray(pid);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private byte[] receive() throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        int dataSize = in.available();
        byte[] out = new byte[dataSize];
        in.read(out);
        return out;

    }
}
