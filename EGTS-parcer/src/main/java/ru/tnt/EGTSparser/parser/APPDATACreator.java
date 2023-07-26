package ru.tnt.EGTSparser.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.parser.tools.APPDATAOptCreatorTool;
import ru.tnt.EGTSparser.parser.tools.AdditionalDataCreatorTool;
import ru.tnt.EGTSparser.data.BodyData_APPDATA;
import ru.tnt.EGTSparser.data.Incoming;
import ru.tnt.EGTSparser.errors.IncorrectDataException;
import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.StringFixedBeanNames;

@Service(StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
@Slf4j
public class APPDATACreator implements ConvertIncomingData {

    @Autowired
    private APPDATAOptCreatorTool appdataOptCreatorTool;

    @Autowired
    private AdditionalDataCreatorTool additionalDataCreatorTool;

    @Override
    public Incoming create(byte[] data) throws IncorrectDataException {
        if( data.length < ByteFixedPositions.HEAD_MIN_LENGTH
                +ByteFixedPositions.PACKAGE_APPDATA_MIN_LENGTH
                +ByteFixedPositions.PACKAGE_APPDATA_RECORD_DATA_MIN_LENGTH

        )
            throw new IncorrectDataException("Incoming APPDATA data too small ");
            appdataOptCreatorTool.setShiftAction(data);
            BodyData_APPDATA bda = BodyData_APPDATA.builder().rl(new Byte[2]) // data[headerLength+1] data[headerLength]
                    .rn(new Byte[2]) // data[headerLength+3] data[headerLength+2]
                    .rfl(data[appdataOptCreatorTool.getHeaderLength() + 4]).build();
            bda = appdataOptCreatorTool.optCreateOID(bda, data );
            System.out.println(" 0  appdataOptCreatorTool.getSiftAction() " + appdataOptCreatorTool.getSiftAction());
            bda = appdataOptCreatorTool.optCreateEVID(bda, data );
            System.out.println("  1 appdataOptCreatorTool.getSiftAction() " + appdataOptCreatorTool.getSiftAction());
            bda = appdataOptCreatorTool.optCreateTm(bda, data );
            System.out.println("  2 appdataOptCreatorTool.getSiftAction() " + appdataOptCreatorTool.getSiftAction());
            bda = additionalDataCreatorTool.additionalCreate(bda, data,  data[3]);
            bda = additionalDataCreatorTool.tailCreate(
                    bda, data , appdataOptCreatorTool.getSiftAction());
            System.out.println(bda);
            return bda;

    }

}
