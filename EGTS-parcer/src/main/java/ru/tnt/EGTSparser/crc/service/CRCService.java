package ru.tnt.EGTSparser.crc.service;

import ru.tnt.EGTSparser.crc.service.generators.CRC16Calculator;
import ru.tnt.EGTSparser.crc.service.generators.CRC8Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("makeCRC")
public class CRCService implements CRC {

    @Autowired
    @Qualifier("crc8")
    private CRC8Calculator calculator8;

    @Autowired
    @Qualifier("crc16")
    private CRC16Calculator calculator16;

    @Override
    public long calculate8(long[] data) {
        calculator8.reset();
calculator8.update(data);
        return   calculator8.value();
    }

    @Override
    public long calculate16(long[] data) {
        calculator16.setData(data);
        return calculator16.value();
    }

}
