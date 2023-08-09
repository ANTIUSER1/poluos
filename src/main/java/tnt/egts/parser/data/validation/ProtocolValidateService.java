package tnt.egts.parser.data.validation;

import org.springframework.stereotype.Service;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.ArrayUtils;

@Service
public class ProtocolValidateService implements ProtocolValidate {



    @Override
    public boolean validPRV(byte[] income) {
        return income[ByteFixedPositions.PACKAGE_PRV_INDEX] == ByteFixValues.PACKAGE_PRV_VALUE;
    }

    @Override
    public boolean validPackageType(byte[] income) {
        return income[ByteFixedPositions.PACKAGE_TYPE_INDEX]==1
               ||  income[ByteFixedPositions.PACKAGE_TYPE_INDEX]==2 ;
    }

    @Override
    public boolean validPRF(byte[] income) {
        String bits =
                ArrayUtils.byteToBinary(income[ByteFixedPositions.PACKAGE_PRF_INDEX]);
        bits = bits.substring(bits.length() - 2);
        return bits.equals(ByteFixValues.PACKAGE_PRF_VALUE);
    }

}
