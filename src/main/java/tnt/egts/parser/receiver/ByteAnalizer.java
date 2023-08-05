package tnt.egts.parser.receiver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.validation.CRCValidate;
import tnt.egts.parser.data.validation.DataLengthValidate;
import tnt.egts.parser.data.validation.ProtocolValidate;
import tnt.egts.parser.util.ProcessingResultCodeConstants;

@Service
@Slf4j
public class ByteAnalizer {


    @Autowired
    private CRCValidate crcValidate;

    @Autowired
    private DataLengthValidate dataLengthValidate;

    @Autowired
    private ProtocolValidate protocolValidate;


    public byte analize(byte[] income) {

        log.error(" Validation incoming data start   ");
        if (invalidProtocolType(income)) {
            log.error("Invalid  protocol Type  {TYPE INCOMES: "+income[9]+"}");
            return ProcessingResultCodeConstants.EGTS_PC_INC_HEADERFORM;
        } else if (invalidProtocolPRF(income)) {
            log.error("Invalid  PRF data ");
            return ProcessingResultCodeConstants.EGTS_PC_UNS_PROTOCOL;
        } else if (invalidProtocolPRV(income)) {
            log.error("Invalid  PRV data ");
            return ProcessingResultCodeConstants.EGTS_PC_UNS_PROTOCOL;
        } else if (invalidPackageLength(income)) {
            log.error("Invalid  package  length ");
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        } else if (invalidDataLength(income)) {
            log.error("Invalid  header length ");
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        } else if (incorrectCRC8(income)) {
            log.error("Invalid CRC header  ");
            return ProcessingResultCodeConstants.EGTS_PC_HEADER_CRCERROR;
        } else if (incorrectCRC16(income)) {
            log.error("Invalid CRC Data  ");
            return ProcessingResultCodeConstants.EGTS_PC_DATACRC_ERRR;
        } else if (incorrectDataLength(income)) {
            log.error("Invalid SFRD length  ");
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        }
        log.error(" Validation incoming data finish   ");
        return 0;
    }

    private boolean invalidProtocolType(byte[] income) {
        return !protocolValidate.validProtocolType(income);
    }

    private boolean invalidProtocolPRF(byte[] income) {
        return !protocolValidate.validPRF(income);
    }

    private boolean invalidProtocolPRV(byte[] income) {
        return !protocolValidate.validPRV(income);
    }


    private boolean incorrectCRC16(byte[] income) {
        return !crcValidate.CRC16Correct(income);
    }

    private boolean incorrectCRC8(byte[] income) {
        return !crcValidate.CRC8Correct(income);
    }

    private boolean invalidDataLength(byte[] income) {
         return !dataLengthValidate.validHeaderLength(income);
    }

    private boolean incorrectDataLength(byte[] income) {
        return !dataLengthValidate.validDataLength(income);
    }


    private boolean invalidPackageLength(byte[] income) {
        return !dataLengthValidate.validPackageLength(income);
    }


}

