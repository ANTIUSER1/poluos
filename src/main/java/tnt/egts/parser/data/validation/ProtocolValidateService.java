package tnt.egts.parser.data.validation;

import org.springframework.stereotype.Service;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringArrayUtils;

@Service
public class ProtocolValidateService implements ProtocolValidate {

    @Override
    public boolean validPRV(byte[] income) {
        return income[ByteFixedPositions.PACKAGE_PRV_INDEX] == ByteFixedPositions.PACKAGE_PRV_VALUE;
    }

    @Override
    public boolean validPRF(byte[] income) {
        String bits =
                StringArrayUtils.byteToBinary(income[ByteFixedPositions.PACKAGE_PRF_INDEX]);
        bits = bits.substring(bits.length() - 2);
        return bits.equals(ByteFixedPositions.PACKAGE_PRF_VALUE);
    }

}
