package tnt.egts.parser.data.analysis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BitAnalysisService implements  BitsAnalizer{

    private static  final int OPTIONS_POSITION=5;
    private static  final int COMPRESSING_POSITION=2;

    @Override
    public BitFlags optionAnalysis(String data) {
        log.info("RTE analysis ");
        if(data.charAt(OPTIONS_POSITION)==1)
        return BitFlags.HOPTIONS_EXISTS;
        return BitFlags.HOPTIONS_NOT_EXISTS;
    }

    @Override
    public BitFlags compressingAnalysis(String data) {
        log.info("CMP analysis ");
        if(data.charAt(COMPRESSING_POSITION)==1)
            return BitFlags.USE_COMPRESSION;
        return BitFlags.NO_USE_COMPRESSION; }

    @Override
    public BitFlags priorityAnalysis(String data) {
        String pr=glueBits(data,0,1);
        if(pr.equals("00"))return BitFlags.PRIORITY_LEVEL_SUPERHARD;
        if(pr.equals("01"))return BitFlags.PRIORITY_LEVEL_HARD;
        if(pr.equals("10"))return BitFlags.PRIORITY_LEVEL_MIDDLE;
        return BitFlags.PRIORITY_LEVEL_LOW;
    }

    @Override
    public BitFlags encryptionAnalysis(String data) {
        String pr=            glueBits(data,3,4);
        if(pr.equals("00"))return BitFlags.NO_USE_ENCRYPTION;
        return BitFlags.USE_ENCRYPTION;
    }

    private String glueBits(String data, int first, int  second) {
        return new StringBuffer(data.charAt(first)) .append( data.charAt(second)).toString();
    }


}
