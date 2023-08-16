package tnt.egts.parser.data.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;

@Service
public class CRCValidateService implements CRCValidate {

    @Autowired
    private CRC crc;

    @Override
    public boolean CRC8Correct(byte[] income) {
        long crc8=crc.calculateHead(income);
        int headLengthIndexIndex = ByteFixPositions.HEAD_LENGTH_INDEX;
        return (byte) (crc8) == income[income[headLengthIndexIndex] - 1];
    }

    @Override
    public boolean CRC16Correct(byte[] income) {

        byte[] dataOnly = ArrayUtils.getSubArrayFromTo(income,
                income[ByteFixPositions.HEAD_LENGTH_INDEX], income.length - 2);
        long crcFromData = crc.calculate16(dataOnly);
        byte[] crcFromDataShort =
                ArrayUtils.shortToByteArray((short) crcFromData);
        crcFromDataShort = ArrayUtils.inverse(crcFromDataShort);

        byte[] crc16Only = new byte[2];
        crc16Only[1] = income[income.length - 1];
        crc16Only[0] = income[income.length - 2];

        boolean result = ArrayUtils.arraysEquals(crcFromDataShort, crc16Only);

        return result;
    }
}