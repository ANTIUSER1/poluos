package tnt.egts.parser.data.validation;

import org.springframework.stereotype.Service;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringArrayUtils;

import java.nio.ByteBuffer;

@Service
public class DataLengthValidateService implements DataLengthValidate {

    @Override
    public boolean validDataLength(byte[] income) {

        ByteBuffer bbf = ByteBuffer.allocate(2);
        byte[] fdl = StringArrayUtils.createSubArray(income, 5, 7);
        fdl = StringArrayUtils.inverse(fdl);
        bbf.put(fdl);
        short calcFDL = bbf.getShort(0);
        int incomeDataLength = income.length - income[ByteFixedPositions.HEAD_LENGTH_INDEX];
        incomeDataLength = Math.max(0, incomeDataLength - 2);
        return incomeDataLength == calcFDL;
    }

    @Override
    public boolean validHeaderLength(byte[] income) {
        return (income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixedPositions.HEAD_MIN_LENGTH || income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixedPositions.HEAD_MAX_LENGTH);

    }

    @Override
    public boolean validPackageLength(byte[] income) {
        return income.length >= ByteFixedPositions.HEAD_MIN_LENGTH;
    }
}
