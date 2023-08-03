package tnt.egts.parser.data.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.ArrayUtils;

import java.nio.ByteBuffer;

@Service
public class CRCValidateService implements CRCValidate {

    @Autowired
    private CRC crc;

    @Override
    public boolean CRC8Correct(byte[] income) {
        byte[] onlyHead = ArrayUtils.getSubArrayFromTo(income, 0, income[ByteFixedPositions.HEAD_LENGTH_INDEX]);
        byte[] testIncome = ArrayUtils.getSubArrayFromTo(income, 0, onlyHead.length - 1);
        long crc8 = crc.calculate8(testIncome);
        return crc8 == onlyHead[onlyHead.length - 1];
    }

    @Override
    public boolean CRC16Correct(byte[] income) {
        byte[] dataOnly = ArrayUtils.getSubArrayFromTo(income,
                income[ByteFixedPositions.HEAD_LENGTH_INDEX], income.length - 1);
        byte[] dataCrcFree = ArrayUtils.getSubArrayFromTo(dataOnly, 0, dataOnly.length - 2);
        byte[] dataCrc = ArrayUtils.getSubArrayFromTo(dataOnly, dataOnly.length - 2, dataOnly.length);
        dataCrc = ArrayUtils.inverse(dataCrc);
        ByteBuffer bbf = ByteBuffer.allocate(2);
        bbf.put(dataCrc);
        short realCrc16 = (short) crc.calculate16(dataCrcFree);
        return realCrc16 == bbf.getShort(0);
    }
}
