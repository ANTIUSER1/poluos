package tnt.egts.parser.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentFinalCreate;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.data.analysis.ByteAnalizer;
import tnt.egts.parser.data.store.IncomeCollectionsService;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.InvalidDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.ResponseData;
import tnt.egts.parser.util.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Main receiver class
 * in run method receive process initialized
 * if validate flag in config == true the income data will be validate
 */
@Component
@Slf4j
public class ReceiverData implements Runnable {

    @Autowired
    @Qualifier (StringFixedBeanNames.AUTH_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
    OutcomeIdentFinalCreate outcomeIdentCreate;

    @Autowired
    CRC crc;

    //    @Autowired
//    @Qualifier("prepareResponse")
    private OutcomeIdent preparingOutcomeAuthData;

    @Autowired
    @Qualifier (StringFixedBeanNames.RESPONSE_DATA_BEAN)
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
    @Qualifier (StringFixedBeanNames.BYTE_ANALIZER_FOR_EGTS_ERRORS_BEAN)
    private ByteAnalizer byteAnalizer;

    @Autowired
    @Qualifier (StringFixedBeanNames.INCOMING_ARRAY_ANALIZER_BEAN)
    private ByteAnalizer incomeArrayAnalizer;

    @Autowired
    private IncomeCollectionsService incomeCollectionsService;

    private IncomeDataStorage store;

    private int responseCode;
    private int step;

    private volatile long msgNO;

    private volatile long errorN0;

    @Override
    public void run() {
        log.info("work on request from " + socket.getRemoteSocketAddress() + "  start");
        log.info("local server runs on address/port " + socket.getLocalAddress() + ":" + socket.getPort());
        while (true) {
            log.info("\n----  \n start work! "+step+" step ");
            log.info("\n\n\n" +
                     "*******************************************************\n");
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
                        throw new InvalidDataException("Processing terminated unexpectedly due to a broken data packet ");
                    }
                }
                log.info("Response code " + responseCode);
                dataTransform(income, (byte) responseCode);
                responseData.sendResponse(socket, store, preparingOutcomeAuthData, (byte) responseCode);
                msgNO++;

                System.out.println("INFO SFRD:");
                System.out.println("          HEADERS");
                incomeCollectionsService.printCollectionHeadersOnly();
                System.out.println("         SFRD");
                incomeCollectionsService.printCollectionSFRDOnly();

                log.info("\n work on request finish. Steps: "+step+". " +
                         "Correct" +
                         " " +
                         "messeges: " + msgNO + "; errors: " + errorN0
                +"\n\n "
                );
                step++;
            } catch (Exception e) {step++;
                log.error("Error while data transform: " + e.getMessage());
                //  e.printStackTrace();
                return;
            }
            log.info("STOP!!");
        }
    }

    /**
     * use for tests different bytes
     *
     * @param income
     * @return
     */
    private byte[] fakeByte(byte[] income) {
        income[3] = 0;
        income[2] = 4;
        income[ByteFixPositions.PACKAGE_TYPE_INDEX] = 1;
        byte[] head = ArrayUtils.getFixedLengthSubArray(income, 0, 10);
        System.out.println("HEAD:  " + ArrayUtils.arrayPrintToScreen(head) + " " + "CRC8: " + crc.calculate8(head));
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
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());

        resTest = readFromStream(16);
        //fakeByte(resTest);
        if (incomeArrayAnalizer.analize(resTest) == ErrorCodes.INVALID_ARRAY_LENGTH) {
            errorN0++;
            log.error("Invalid length :  " + resTest.length);
            return new byte[0];
        } else if (incomeArrayAnalizer.analize(resTest) == ErrorCodes.INVALID_HEADER_DATA_VALUE) {
            errorN0++;
            log.error("Data are invalid in received package from " + socket.getRemoteSocketAddress());
            log.error(" Details: the Value of HeaderLength is 0 or " + "the length of SFRD is 0 ");
            throw new InvalidDataException("Processing terminated unexpectedly due to a broken data packet " + ArrayUtils.arrayPrintToScreen(resTest) + "\n");
        } else {
            byte[] shortArray = ArrayUtils.getFixedLengthSubArray(resTest, 5, 2);
            short fdl = NumberUtils.byteArrayInverseToShort(shortArray);
            dsize = resTest[3] + fdl + 2;

            result = new byte[dsize - resTest.length];
            in.read(result);
            result = ArrayUtils.joinArrays(resTest, result);
            if (result.length < dsize) {
                log.error("Invalid length :  " + result.length);
                return new byte[0];
            }
        }
        return result;
    }


    private void throwReceivedInfoGlobalError(byte[] income) {
        if (income == null || income.length < 3 || income.length < 7) {
            errorN0++;
            log.error("Null data received, or income data is empty" + socket.getRemoteSocketAddress());
            throw new InvalidDataException("Processing can not be proceed on corrupt data " + ArrayUtils.arrayPrintToScreen(income) + "\n");

        }
    }
}
