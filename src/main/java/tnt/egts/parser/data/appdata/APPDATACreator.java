package tnt.egts.parser.data.appdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.Incoming;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.data.ConvertIncomingData;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringFixedBeanNames;


@Service (StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
@Slf4j
public class APPDATACreator implements ConvertIncomingData {

    @Override
    public Incoming create(byte[] income)
            throws IncorrectDataException, NumberArrayDataException {
        int startAPPDATA = ByteFixedPositions.getAPPDATAStart(income);
System.out.println("JJJJJJJJJJstartAPPDATA   "+startAPPDATA);
        byte[] fdl = ArrayUtils.createSubArray(income,
                ByteFixedPositions.FDL_START_INDEX,
                ByteFixedPositions.FDL_START_INDEX + 2);
        System.out.println("JJFDL  "+ArrayUtils.arrayPrintToScreen(fdl)
        +"   income.length-1-2-startAPPDATA " +( income.length-1-2-startAPPDATA) );

        fdl = ArrayUtils.inverse(fdl);
        System.out.println("JJFDL  "+ArrayUtils.arrayPrintToScreen(fdl));

        short fdlPos = ArrayUtils.byteArrayToShort(fdl);
        System.out.println("fdlPos  "+fdlPos);
        APPDATA bda = APPDATA.builder()
                .content(ArrayUtils.createFixedLengthSubArray(income, startAPPDATA,
                        fdlPos) )
                .build();

        log.info("BodyData_APPDATA created \n" + bda);
        return bda;

    }

}
