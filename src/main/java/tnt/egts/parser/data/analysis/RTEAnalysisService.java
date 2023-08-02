package tnt.egts.parser.data.analysis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RTEAnalysisService implements  BitsAnalizer{

    private static  final int OPTIONS_POSITION=3;

    @Override
    public BitFlags optionAnalysis(String data) {
        log.info("RTE analysis ");
        if(data.charAt(data.length()-OPTIONS_POSITION)==1)
        return BitFlags.HOPTIONS_EXISTS;
        return BitFlags.HOPTIONS_NOT_EXISTS;
    }


}
