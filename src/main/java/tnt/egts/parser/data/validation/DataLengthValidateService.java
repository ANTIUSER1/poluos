package tnt.egts.parser.data.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.analysis.BitFlags;
import tnt.egts.parser.data.analysis.BitsAnalizer;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringArrayUtils;

import java.nio.ByteBuffer;

@Service
public class DataLengthValidateService implements DataLengthValidate {

    @Autowired
    private BitsAnalizer optionAnalysis;

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
        BitFlags flag =
                optionAnalysis.optionAnalysis(StringArrayUtils.byteToBinary(income[2]));
        if (flag.equals(BitFlags.HOPTIONS_NOT_EXISTS))
            return income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MIN_LENGTH;
        return income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MAX_LENGTH;
   }

    @Override
    public boolean validPackageLength(byte[] income) {
        return income.length >= ByteFixValues.HEAD_MIN_LENGTH;
    }
}
