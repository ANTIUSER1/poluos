package ru.tnt.EGTSparser.controllers.receiver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.crc.service.CRC;
import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.ProcessingResultCodeConstants;
import ru.tnt.EGTSparser.util.StringArrayUtils;

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
        }

        return -10;
    }

    private boolean incorrectCRC16(byte[] income) {
        System.out.println("888888888888888888888888888888888888888");
        byte[] dataOnly = StringArrayUtils.createSubArray(income, income[ByteFixedPositions.HEAD_LENGTH_INDEX], income.length - 1);
        byte[] dataCrcFree = StringArrayUtils.createSubArray(dataOnly, 0, dataOnly.length - 2);
        byte[] dataCrc = StringArrayUtils.createSubArray(dataOnly, dataOnly.length - 2, dataOnly.length);
        dataCrc = StringArrayUtils.inverse(dataCrc);
        ByteBuffer bbf = ByteBuffer.allocate(2);
        short realCrc16 = (short) crc.calculate16(dataCrcFree);
        bbf.putShort(realCrc16);
        return !StringArrayUtils.arraysEquals(dataCrc, bbf.array());
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
