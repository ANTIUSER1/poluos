package tnt.egts.parser.data.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.analysis.HeaderBitsAnalizer;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.ArrayUtils;

@Service
public class DataLengthValidateService implements DataLengthValidate {

    @Autowired
    private HeaderBitsAnalizer headerBitsAnalizer;

    @Override
    public boolean validDataLength(byte[] income) {
        int incomeDataLength = income.length - income[ByteFixPositions.HEAD_LENGTH_INDEX];
        incomeDataLength = Math.max(0, incomeDataLength - 2);
        return incomeDataLength == calcFDLFromIncome( income);
    }

    @Override
    public boolean validHeaderLength(byte[] income) {
         if (headerBitsAnalizer. routeOptionBitExist(ArrayUtils.byteToBinary(income[2])))
            return income[ByteFixPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MAX_LENGTH;
        return income[ByteFixPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MIN_LENGTH;
   }

    @Override
    public boolean validPackageLength(byte[] income) {
       int pl= calcPacageHead(income)+
                                calcFDLFromIncome(income)+ByteFixValues.CRC16_VALUE_SIZE;
        return pl==income.length;
    }

    private   int calcPacageHead(byte[] income){
        if (headerBitsAnalizer. routeOptionBitExist(ArrayUtils.byteToBinary(income[2])))
            return  ByteFixValues.HEAD_MAX_LENGTH;
        return   ByteFixValues.HEAD_MIN_LENGTH ;
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
