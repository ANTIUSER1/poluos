package tnt.egts.parser.data.analysis;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HeaderBitAnalysisService implements HeaderBitsAnalizer {

    private static  final int OPTIONS_POSITION=5;
    private static  final int COMPRESSING_POSITION=2;

    @Override
    public boolean routeOptionBitExist(String data) {
        log.info("RTE analysis start ");
       if(data.charAt(2)==1)
        return true;
        log.info("RTE analysis finish ");
        return false;
    }

    @Override
    public boolean compressingAnalysis(String data) {
        return false;
    }

    @Override
    public boolean priorityOptionBitExist(String data) {
        return false;
    }

    @Override
    public boolean encryptionOptionBitExist(String data) {
        return false;
    }


    private String glueBits(String data, int first, int  second) {
        return new StringBuffer(data.charAt(first)) .append( data.charAt(second)).toString();
    }


}
