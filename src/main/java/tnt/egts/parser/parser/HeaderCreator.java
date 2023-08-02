package tnt.egts.parser.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnt.egts.parser.data.*;
import tnt.egts.parser.data.analysis.BitsAnalizer;
import tnt.egts.parser.data.validation.ReadFDLValidate;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.parser.tools.*;
import tnt.egts.parser.util.*;


@Component(StringFixedBeanNames.HEADER_CREATOR_BEAN)
@Slf4j
public class HeaderCreator implements ConvertIncomingData {

    @Autowired
    private HeaderOptCreatorTool headerOptCreatorTool;

    @Autowired
    private AdditionalDataCreatorTool additionalDataCreatorTool;

    @Autowired
    private ReadFDLValidate readFDLValidate;

    @Autowired
    private BitsAnalizer optionAnalysis;
    @Override
    public Incoming create(byte[] data) throws IncorrectDataException {

        log.info("Start parsing incoming data");
        HeaderData hd = HeaderData.builder()
                .hasOptions(data[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MAX_LENGTH)
                .prv(data[0])
                .skid(data[1]).prf(data[2]).hl(data[3])
                .he(data[4]).fdl(new Byte[2])// {data[6] , data[5]})
                .pid(new Byte[2])// {data[8] , data[7]})
                .pt(data[9]).hcs(data[10]).build();
        hd = additionalDataCreatorTool.additionalCreate(hd, data);
        if (hd.isHasOptions()) headerOptCreatorTool.optCreate(hd, data);
        log.info("Finish parsing incoming data header normally");
        System.out.println("\n" + hd + "\n");
        return hd;
    }
}
