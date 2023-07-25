package ru.transteft.tnt.crc.CRCmaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.transteft.tnt.crc.CRCmaker.generators.CRC16Calculator;
import ru.transteft.tnt.crc.CRCmaker.generators.CRC8Calculator;

@Service
public class CRCService implements CRC {
    @Autowired
    @Qualifier("crc8")
    private CRC8Calculator calculator8;

    @Autowired
    @Qualifier("crc16")
    private CRC16Calculator calculator16;

//    @Override
    public long calculate8(long[] data) {
        calculator8.reset();
calculator8.update(data);
        return   calculator8.value();
    }

//    @Override
    public long calculate16(long[] data) {
        calculator16.setData(data);
        return calculator16.value();
    }

}
