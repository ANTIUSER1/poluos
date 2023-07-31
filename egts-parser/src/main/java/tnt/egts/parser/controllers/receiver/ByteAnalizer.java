package tnt.egts.parser.controllers.receiver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.util.*;


import java.nio.ByteBuffer;

@Service
@Slf4j
public class ByteAnalizer {


    @Autowired
    private CRC crc;


    public byte analize(byte[] income) {
        if (invalidDataLength(income)) {
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        } else if (incorrectCRC8(income)) {
            return ProcessingResultCodeConstants.EGTS_PC_HEADER_CRCERROR;
        } else if (incorrectCRC16(income)) {
            return ProcessingResultCodeConstants.EGTS_PC_DATACRC_ERRR;
        } else if (incorrectDataLength(income)) {
            return ProcessingResultCodeConstants.EGTS_PC_INVDATALEN;
        }

        return -10;
    }

    private boolean incorrectDataLength(byte[] income) {
        ByteBuffer bbf = ByteBuffer.allocate(2);
        byte[] fdl = StringArrayUtils.createSubArray(income, 5, 7);
        fdl = StringArrayUtils.inverse(fdl);
        bbf.put(fdl);
        short calcFDL = bbf.getShort(0);
        int incomeDataLength = income.length - income[ByteFixedPositions.HEAD_LENGTH_INDEX];
        incomeDataLength = Math.max(0, incomeDataLength - 2);
        return incomeDataLength != calcFDL;
    }

    private boolean incorrectCRC16(byte[] income) {
        byte[] dataOnly = StringArrayUtils.createSubArray(income, income[ByteFixedPositions.HEAD_LENGTH_INDEX],
                income.length - 1);
        byte[] dataCrcFree = StringArrayUtils.createSubArray(dataOnly, 0, dataOnly.length - 2);
        byte[] dataCrc = StringArrayUtils.createSubArray(dataOnly, dataOnly.length - 2, dataOnly.length);
        dataCrc = StringArrayUtils.inverse(dataCrc);
        ByteBuffer bbf = ByteBuffer.allocate(2);
        bbf.put(dataCrc);
        short realCrc16 = (short) crc.calculate16(dataCrcFree);
        return realCrc16 != bbf.getShort(0);
    }

    private boolean incorrectCRC8(byte[] income) {
        byte[] onlyHead = StringArrayUtils.createSubArray(income, 0, income[ByteFixedPositions.HEAD_LENGTH_INDEX]);
        byte[] testIncome = StringArrayUtils.createSubArray(income, 0, onlyHead.length - 1);
        long crc8 = crc.calculate8(testIncome);
        return crc8 != onlyHead[onlyHead.length - 1];
    }

    private boolean invalidDataLength(byte[] income) {
        return income.length < ByteFixedPositions.HEAD_MIN_LENGTH;
    }
}
