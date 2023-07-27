package ru.tnt.EGTSparser.parser.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.data.HeaderData;
@Service
@Slf4j
public class HeaderOptCreatorTool {

    public HeaderData optCreate(HeaderData hd, byte[] data){
        log.info("Creating option data in header start");
        hd.setPra(new Byte[2]);// data[12], data[11]
        hd.getPra()[0]=data[12];
        hd.getPra()[1]=data[11];

        hd.setTtl(data[10]);

        hd.setRca(new Byte[2]);// data[14], data[13]
        hd.getRca()[0]=data[14];
        hd.getRca()[1]=data[13];

        hd.setTtl(data[15]);
        hd.setHcs(data[16]);
        log.info("Creating option data in header finish");
        return hd;
    }
}
