package tnt.egts.parser.data.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.NumberToBits;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.ArrayUtils;

@Service
@Slf4j
public class PackageValidateService implements PackageValidate {

    @Autowired
    private NumberToBits numberToBitsService;

    @Override
    public boolean validPRV(byte[] income) {
        return income[ByteFixPositions.PACKAGE_PRV_INDEX] == ByteFixValues.PACKAGE_PRV_VALUE;
    }

    @Override
    public boolean validPackageType(byte[] income) {
        return income[ByteFixPositions.PACKAGE_TYPE_INDEX]
               ==ByteFixValues.TYPE_APPDATA
               ||  income[ByteFixPositions.PACKAGE_TYPE_INDEX]
                   ==ByteFixValues.TYPE_SIGNED_APPDATA ;
    }

    @Override
    public boolean validPRF(byte[] income) {
        String bits =
                numberToBitsService.bitsFromByte(income[ByteFixPositions.PACKAGE_PRF_INDEX]);
        bits = bits.substring(0, ByteFixValues.PRF_LENGTH);
        return bits.equals(ByteFixValues.PACKAGE_PRF_VALUE);
    }

}
