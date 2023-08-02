package tnt.egts.parser.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.*;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.parser.tools.*;
import tnt.egts.parser.util.*;


@Service(StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
@Slf4j
public class APPDATACreator implements ConvertIncomingData {

    @Autowired
    private APPDATAOptCreatorTool appdataOptCreatorTool;

    @Autowired
    private AdditionalDataCreatorTool additionalDataCreatorTool;

    @Override
    public Incoming create(byte[] data) throws IncorrectDataException {
        if( data.length < ByteFixValues.HEAD_MIN_LENGTH
                +ByteFixValues.PACKAGE_APPDATA_MIN_LENGTH
                +ByteFixValues.PACKAGE_APPDATA_RECORD_DATA_MIN_LENGTH

        )
            throw new IncorrectDataException("Incoming APPDATA data too small ");
            appdataOptCreatorTool.setShiftAction(data);
            BodyData_APPDATA bda = BodyData_APPDATA.builder().rl(new Byte[2]) // data[headerLength+1] data[headerLength]
                    .rn(new Byte[2]) // data[headerLength+3] data[headerLength+2]
                    .rfl(data[appdataOptCreatorTool.getHeaderLength() + 4]).build();
            bda = appdataOptCreatorTool.optCreateOID(bda, data );
            bda = appdataOptCreatorTool.optCreateEVID(bda, data );
            bda = appdataOptCreatorTool.optCreateTm(bda, data );
            bda = additionalDataCreatorTool.additionalCreate(bda, data,  data[3]);
            bda = additionalDataCreatorTool.tailCreate(
                    bda, data , appdataOptCreatorTool.getSiftAction());
        log.info( "BodyData_APPDATA created \n" +bda );
            return bda;

    }

}
