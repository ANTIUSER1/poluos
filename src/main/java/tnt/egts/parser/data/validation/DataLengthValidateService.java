package tnt.egts.parser.data.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.analysis.BitFlags;
import tnt.egts.parser.data.analysis.BitsAnalizer;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.ArrayUtils;

import java.nio.ByteBuffer;

@Service
public class DataLengthValidateService implements DataLengthValidate {

    @Autowired
    private BitsAnalizer bitsAnalizer;

    @Override
    public boolean validDataLength(byte[] income) {
        int incomeDataLength = income.length - income[ByteFixedPositions.HEAD_LENGTH_INDEX];
        incomeDataLength = Math.max(0, incomeDataLength - 2);
        return incomeDataLength == calcFDLFromIncome( income);
    }

    @Override
    public boolean validHeaderLength(byte[] income) {
        BitFlags flag =
                bitsAnalizer.optionAnalysis(ArrayUtils.byteToBinary(income[2]));
        if (flag.equals(BitFlags.HOPTIONS_NOT_EXISTS))
            return income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MIN_LENGTH;
        return income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MAX_LENGTH;
   }

    @Override
    public boolean validPackageLength(byte[] income) {
        return income.length  == calcPacageHead(income)+
               calcFDLFromIncome(income);
    }

    private   int calcPacageHead(byte[] income){
        BitFlags flag =
                bitsAnalizer.optionAnalysis(ArrayUtils.byteToBinary(income[2]));
        if (flag.equals(BitFlags.HOPTIONS_NOT_EXISTS))
        return   ByteFixValues.HEAD_MIN_LENGTH ;
        return  ByteFixValues.HEAD_MAX_LENGTH;
    }
    private  int calcFDLFromIncome(byte[] income){
        byte[] fdl = ArrayUtils.getSubArrayFromTo(income, 5, 7);
        short calcFDL = 0;
        try {
            calcFDL = ArrayUtils.byteArrayInverseToShort(fdl);
        } catch (NumberArrayDataException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }return  calcFDL;
    }
}
