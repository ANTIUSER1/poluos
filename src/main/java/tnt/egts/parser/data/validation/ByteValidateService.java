package tnt.egts.parser.data.validation;

import org.springframework.stereotype.Service;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.ByteFixValues;

@Service
public class ByteValidateService implements ByteValidate{

    @Override
    public boolean validHLByte(byte[] income) {
       return income[ByteFixPositions.HEAD_LENGTH_INDEX]== ByteFixValues.HEAD_MIN_LENGTH
               || income[ByteFixPositions.HEAD_LENGTH_INDEX]== ByteFixValues.HEAD_MAX_LENGTH;
    }
}
