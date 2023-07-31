package tnt.egts.parser.controllers.receiver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.validation.CRCValidate;
import tnt.egts.parser.data.validation.DataLengthValidate;
import tnt.egts.parser.util.ProcessingResultCodeConstants;

@Service
@Slf4j
public class ByteAnalizer {


    @Autowired
    private CRCValidate crcValidate;

    @Autowired
    private DataLengthValidate dataLengthValidate;

    public byte analize(byte[] income) {

        if (invalidPackageLength(income)) {
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
        return -10;
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
    return  dataLengthValidate.validPackageLength(income);
    }


}

