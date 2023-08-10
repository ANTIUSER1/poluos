package tnt.egts.parser.data.appdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.ConvertIncomingData;
import tnt.egts.parser.data.Incoming;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.util.HashMap;
import java.util.Map;


@Service (StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
@Slf4j
public class APPDATACreator implements ConvertIncomingData {

    @Autowired
    private APPDATAService appdataService;


    @Autowired
    private APPDATAHelper helper;

    private int startAPPDATA;

    private byte[] fdl;

    private final Map<String, Boolean> appDataOptions = new HashMap<>();

    private short fdlPos;

    @Override
    public Incoming create(byte[] income)
            throws IncorrectDataException, NumberArrayDataException {
        prepareData(income);
        byte[] incomeApp = ArrayUtils.getFixedLengthSubArray(income,
                startAPPDATA, fdlPos);

        String appDataFlags=ArrayUtils.byteToBinary(incomeApp[0]);
        optionTypes(appDataFlags);

        System.out.println("||||||||||||||||||||||  incomeApp LRENN " + incomeApp.length);
        System.out.println(startAPPDATA +
                           " ===startAPPDATA  *******  " + ArrayUtils.arrayPrintToScreen(incomeApp) + "\n");
        APPDATA bda = APPDATA.builder()
                .packageHeader(appdataService.getPackageHead())
                .content(incomeApp)
                .flags(appDataOptions)
                .build();

        helper.modify(bda);


        System.out.println("\n\n \n  ***||||||||   appdata getPackageHeader \n" + ArrayUtils.arrayPrintToScreen(bda.getPackageHeader()));
        System.out.println("\n \n ***||||||||   appdata getContent \n" + ArrayUtils.arrayPrintToScreen(bda.getContent()));
        System.out.println("\n \n ***||||||||   appdata getRecordData \n" + ArrayUtils.arrayPrintToScreen(bda.getRecordData()));
System.out.println("\n \n \n \n ");
        log.info("BodyData_APPDATA created \n" + bda + "\n ");
        return bda;

    }


    private void optionTypes(String appDataFlags) {
        System.out.println(appDataFlags);
        appDataOptions.put("OID", appDataFlags.charAt(0) == '1');
        appDataOptions.put("EVFE", appDataFlags.charAt(1) == '1');
        appDataOptions.put("TM", appDataFlags.charAt(2) == '1');
    }

    private void prepareData(byte[] income) throws NumberArrayDataException {
        startAPPDATA = ByteFixPositions.getAPPDATAStart(income);
        fdl = ByteFixValues.getFDLByteValue(income, startAPPDATA);
        fdlPos = ByteFixValues.getFDLNumberValue(fdl);

    }

}
