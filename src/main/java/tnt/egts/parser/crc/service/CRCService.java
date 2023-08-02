package tnt.egts.parser.crc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.generator.*;
import tnt.egts.parser.util.StringArrayUtils;

@Service("makeCRC")
@Slf4j
public class CRCService implements CRC {

    @Autowired
    @Qualifier("crc8")
    private CRC8Calculator calculator8;

    @Autowired
    @Qualifier("crc16")
    private CRC16Calculator calculator16;

    @Override
    public long calculate8(byte[] data) {
        log.info(" CRC-8 generates data   "  );
        calculator8.reset();
        calculator8.update(data);
        return calculator8.value();
    }

    @Override
    public long calculate16(byte[] data) {
        log.info("CRC-16 generates data");
        calculator16.setData(data);
        return  calculator16.value();
    }

}