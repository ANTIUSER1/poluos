package tnt.egts.parser.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentFinalCreate;
import tnt.egts.parser.data.sendBack.PrepareedResponseData;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.data.analysis.ByteAnalizerService;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.ResponseData;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Main receiver class
 * in run method receive process initialized
 * if validate flag in config == true the income data will be validate
 */
@Component
@Slf4j
public class ReceiverData implements Runnable {

//    @Autowired
//    @Qualifier("prepareResponse")
    private OutcomeIdent preparingOutcomeAuthData;

    @Autowired
    @Qualifier (StringFixedBeanNames.AUTH_FINAL_RESPONSE_SEND_BEAN)
    OutcomeIdentFinalCreate outcomeIdentCreate;

    @Autowired
    CRC crc;

    @Autowired
    @Qualifier(StringFixedBeanNames.RESPONSE_DATA_BEAN)
    private ResponseData responseData;
    /**
     * flag to activate/deactivate validation incoming data
     */
    @Autowired
    private boolean isValidatePacket;

    private Socket socket;


    @Autowired
    private Storage storage;

    @Autowired
    private ByteAnalizerService byteAnalizer;

    private IncomeDataStorage store;

    private int responseCode;

    private volatile long msgNO;

    private volatile long errorN0;

    @Override
    public void run() {
        log.info("work on request from " + socket.getRemoteSocketAddress() +
                 "  start");
        log.info("local server runs on address/port "+socket.getLocalAddress());
        try {
            byte[] income = receive();
            throwReceivedInfoGlobalError(income);
            log.info("Received data from BNSO. Data length: " + income.length);
            log.debug("DATA:\n  " + ArrayUtils.arrayPrintToScreen(income) + "\n");
             if (isValidatePacket) {
                responseCode = byteAnalizer.analize(income);
                if (responseCode < 0) {
                    errorN0++;
                    log.error("Data are invalid in received package from " + socket.getRemoteSocketAddress());
                    throw new IncorrectDataException(
                            "Processing terminated unexpectedly due to a broken data packet "
                    );
                }
            }
            log.info("Response code " + responseCode);
             dataTransform(income, (byte) responseCode);
            responseData.sendResponse(socket,store ,preparingOutcomeAuthData,
                    (byte) responseCode);
            msgNO++;
            log.info("work on request finish. Correct steps: " + msgNO
                     + "; errors: " + errorN0);
        } catch (Exception e) {
            log.error("Error while data transform: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * use for tests different bytes
     * @param income
     * @return
     */
    private byte[] fakeByte(byte[] income) {
        income[2] = 4;
        income[ByteFixPositions.PACKAGE_TYPE_INDEX] = 1;
        byte[] head = ArrayUtils.getFixedLengthSubArray(income, 0, 10);
        System.out.println("HEAD:  " + ArrayUtils.arrayPrintToScreen(head) + " " +
                           "CRC8: " + crc.calculate8(head));
        income[10] = (byte) crc.calculate8(head);
        return income;
    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private void dataTransform(byte[] income, byte code) throws NumberArrayDataException {
        log.info("Storage  income Data start");
        store = storage.create(income);
        preparingOutcomeAuthData = outcomeIdentCreate.createAuthResponse(store, code);
        log.info("Storage  income Data finish");
    }

    private byte[] readFromStream(int length) throws IOException {
        byte[] result = new byte[length];
        InputStream inTest = socket.getInputStream();
        inTest.read(result);
        throwReceivedInfoGlobalError(result);
        return result;
    }

    private byte[] receive() throws IOException, NumberArrayDataException {
        int dsize;
        byte[] resTest;
        byte[] result;
        BufferedInputStream in =
                new BufferedInputStream(socket.getInputStream());

        resTest = readFromStream(16);
        if (resTest.length < 4) {
            log.error("Invalid length  :  " + resTest.length);
            return new byte[0];
        } else if (resTest.length < 16) {
            log.error("Invalid length :  " + resTest.length);
            return new byte[0];
        } else {
            byte[] shortArray = ArrayUtils.getFixedLengthSubArray(resTest, 5
                    , 2);
            short fdl = ArrayUtils.byteArrayInverseToShort(shortArray);
            if (resTest[3]  <=0 || fdl<= 0   ) {
                errorN0++;
                log.error("Data are invalid in received package from " + socket.getRemoteSocketAddress());
                log.error(" Details: the Value of HeaderLength is 0 or " +
                          "the length of SFRD is 0 ");
                throw new IncorrectDataException(
                        "Processing terminated unexpectedly due to a broken data packet "+
                        ArrayUtils.arrayPrintToScreen(resTest)+ "\n"
                );
            }
            dsize = resTest[3] + fdl + 2;

            result = new byte[dsize - resTest.length];
            in.read(result);
            result = ArrayUtils.joinArrays(resTest, result);
            if(result.length<dsize ) {
                log.error("Invalid length :  " + result.length);
                return new byte[0];
            }
        }
       return result;
    }



    private void throwReceivedInfoGlobalError(byte[] income){

        if (income == null || income.length < 3 || income.length < 7) {
            errorN0++;
            log.error("Null data received, or income data is empty" + socket.getRemoteSocketAddress());
            throw new IncorrectDataException(
                    "Processing can not be proceed on corrupt data " +
                    ArrayUtils.arrayPrintToScreen(income)+ "\n"
            );

        }
    }
}
