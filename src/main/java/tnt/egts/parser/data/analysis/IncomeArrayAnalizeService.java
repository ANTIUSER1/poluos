package tnt.egts.parser.data.analysis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.*;

@Service (StringFixedBeanNames.INCOMING_ARRAY_ANALIZER_BEAN)
@Slf4j
public class IncomeArrayAnalizeService implements ByteAnalizer {

    @Override
    public int analize(byte[] income) throws NumberArrayDataException {
        if (incorrectLength(income)) {
            log.error("Income array is not valid. Invalid length: " + income.length);
            return ErrorCodes.INVALID_ARRAY_LENGTH;
        } else if (invalidHeaderDataValues(income)) {
            log.error("Invalid header data values.Problem found in  " + ByteFixPositions.HEAD_LENGTH_INDEX + " or " + ByteFixPositions.FDL_START_INDEX + " and " + (ByteFixPositions.FDL_START_INDEX + 1) + " positions ");
            return ErrorCodes.INVALID_HEADER_DATA_VALUE;
        }
        return 0;
    }


    private boolean invalidHeaderDataValues(byte[] income) throws NumberArrayDataException {
        byte[] shortArray = ArrayUtils.getFixedLengthSubArray(income, 5, 2);
        short fdl = NumberUtils.byteArrayInverseToShort(shortArray);
        return income[ByteFixPositions.HEAD_LENGTH_INDEX] <= 0 || fdl <= 0;
    }

    private boolean incorrectLength(byte[] income) {
        return income.length < ByteFixValues.HEAD_MIN_LENGTH || income.length < ByteFixValues.HEAD_MAX_LENGTH;
    }
}
