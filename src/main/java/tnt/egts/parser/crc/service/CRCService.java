package tnt.egts.parser.crc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.generator.*;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service(StringFixedBeanNames.CRC_SERVICE_DATA_BEAN)
@Slf4j
public class CRCService implements CRC {

    @Autowired
    @Qualifier(StringFixedBeanNames.CRC8_SERVICE_DATA_BEAN)
    private CRC8Calculator calculator8;

    @Autowired
    @Qualifier(StringFixedBeanNames.CRC16_SERVICE_DATA_BEAN)
    private CRC16Calculator calculator16;

    @Override
    public long calculate8(byte[] data) {
        log.info(" CRC-8 generates data   "  );
        calculator8.reset();
        calculator8.update(data);
        return calculator8.value();
    }

    @Override
    public long calculateHead(byte[] data) {
        int headLengthIndexIndex = ByteFixPositions.HEAD_LENGTH_INDEX;
        byte[] onlyHead = ArrayUtils.getSubArrayFromTo(data, 0,
                data[ByteFixPositions.HEAD_LENGTH_INDEX] - 1);
      return calculate8(onlyHead);
    }

    @Override
    public long calculate16(byte[] data) {
        log.info("CRC-16 generates data");
       return calculator16.crc16calc(data);
    }

    @Override
    public long calculateSfrd(byte[] data) {
        byte[] dataOnly =
            ArrayUtils.getSubArrayFromTo(data,
            data[ByteFixPositions.HEAD_LENGTH_INDEX], data.length - 2);
        return calculate16(dataOnly);
    }

}
