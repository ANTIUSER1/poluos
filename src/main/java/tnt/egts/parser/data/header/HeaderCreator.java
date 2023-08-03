package tnt.egts.parser.data.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tnt.egts.parser.data.*;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.data.ConvertIncomingData;
import tnt.egts.parser.util.*;


@Component(StringFixedBeanNames.HEADER_CREATOR_BEAN)
@Slf4j
public class HeaderCreator implements ConvertIncomingData {


    @Override
    public Incoming create(byte[] income) throws IncorrectDataException {
        log.info("Start parsing incoming data");
        int hcsPos=ByteFixedPositions.getHCSIndex(income);
        HeaderData hd = HeaderData.builder()
                .hasOptions(income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MAX_LENGTH)
                .content(ArrayUtils.getSubArrayFromTo(income, 0, hcsPos))
                .build();

        log.info("Finish parsing incoming data header normally");
        System.out.println("\n" + hd + "\n");
        return hd;
    }
}
