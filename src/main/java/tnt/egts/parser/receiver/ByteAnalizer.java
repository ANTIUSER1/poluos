package tnt.egts.parser.receiver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.validation.CRCValidate;
import tnt.egts.parser.data.validation.DataLengthValidate;
import tnt.egts.parser.data.validation.PackageValidate;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.ProcessingResultCodeConstants;

@Service
@Slf4j
public class ByteAnalizer {


    @Autowired
    private CRCValidate crcValidate;

    @Autowired
    private DataLengthValidate dataLengthValidate;

    @Autowired
    private PackageValidate packageValidate;


    public byte analize(byte[] income) {

        log.error(" Validation incoming data start.  {"+ ArrayUtils.arrayPrintToScreen(income)+" } ");
        if (invalidPackageType(income)) {
            log.error("Invalid  package Type  {TYPE INCOMES: "
                      + income[ByteFixPositions.PACKAGE_TYPE_INDEX]
                      +"}");
            return ProcessingResultCodeConstants.EGTS_PC_INC_HEADERFORM;
        } else if (invalidPackagePRF(income)) {
            log.error("Invalid  PRF data   " );
            return ProcessingResultCodeConstants.EGTS_PC_UNS_PROTOCOL;
        } else if (invalidPackagePRV(income)) {
            log.error("Invalid  PRV data ");
            return ProcessingResultCodeConstants.EGTS_PC_UNS_PROTOCOL;
        } else if (invalidPackageLength(income)) {
            log.error("Invalid  package  length ");
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        } else if (invalidHeadLength(income)) {
            log.error("Invalid  header length ");
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        } else if (invalidCRC8(income)) {
            log.error("Invalid CRC header  ");
            return ProcessingResultCodeConstants.EGTS_PC_HEADER_CRCERROR;
        } else if (invalidCRC16(income)) {
            log.error("Invalid CRC Data  ");
            return ProcessingResultCodeConstants.EGTS_PC_DATACRC_ERRR;
        } else if (invalidDataLength(income)) {
            log.error("Invalid SFRD length  ");
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        }
        log.error(" Validation incoming data finish   ");
        return 0;
    }

    private boolean invalidPackageType(byte[] income) {
        return !packageValidate.validPackageType(income);
    }

    private boolean invalidPackagePRF(byte[] income) {
        return !packageValidate.validPRF(income);
    }

    private boolean invalidPackagePRV(byte[] income) {
        return !packageValidate.validPRV(income);
    }


    private boolean invalidCRC16(byte[] income) {
        return !crcValidate.CRC16Correct(income);
    }

    private boolean invalidCRC8(byte[] income) {
        return !crcValidate.CRC8Correct(income);
    }

    private boolean invalidHeadLength(byte[] income) {
         return !dataLengthValidate.validHeaderLength(income);
    }

    private boolean invalidDataLength(byte[] income) {
        return !dataLengthValidate.validDataLength(income);
    }


    private boolean invalidPackageLength(byte[] income) {
        return !dataLengthValidate.validPackageLength(income);
    }


}

