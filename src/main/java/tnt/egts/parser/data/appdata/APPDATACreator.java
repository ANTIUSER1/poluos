package tnt.egts.parser.data.appdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.data.Incoming;
import tnt.egts.parser.data.header.HeaderService;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.data.ConvertIncomingData;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.util.HashMap;
import java.util.Map;


@Service (StringFixedBeanNames.APP_DATA_CREATOR_BEAN)
@Slf4j
public class APPDATACreator implements ConvertIncomingData {

    @Autowired
    private HeaderService headerService;

    @Autowired
    private APPDATAHelper helper;
    private int startAPPDATA;

    private byte[] fdl;

    private Map<String, Boolean> appDataOptions= new HashMap<>();
    private short fdlPos;

    @Override
    public Incoming create(byte[] income)
            throws IncorrectDataException, NumberArrayDataException {
        prepareData(income);
        byte[] incomeApp=ArrayUtils.getFixedLengthSubArray(income,
                startAPPDATA , fdlPos);
System.out.println("||||||||||||||||||||||  incomeApp LRENN "+incomeApp.length);
        System.out.println(startAPPDATA+
                " ===startAPPDATA  *******  "+ArrayUtils.arrayPrintToScreen(incomeApp)+"\n");

//        String appDataFlags=ArrayUtils.byteToBinary(incomeApp[0]);
//        System.out.println(  " ***flags  "+appDataFlags);
//        optionTypes(appDataFlags);

        APPDATA bda = APPDATA.builder()
                .head(headerService.getHeaderContent())
                .content(incomeApp)
                .flags(appDataOptions)
                .build();
        helper.modify(bda);

//        System.out.println("//     //   appdata head \n  "+ArrayUtils.arrayPrintToScreen(  bda.getHead()));
//        System.out.println("///////////   appdata content \n  "+ArrayUtils.arrayPrintToScreen(  bda.getContent()));
//        System.out.println("//   /////////   appdata RD  \n  "+ArrayUtils.arrayPrintToScreen(  bda.getRecordData()));
//        System.out.println("   ||||    RD len    " +bda.getRecordData().length+
//                           "  /////////   "+bda.getRecLength());
        log.info("BodyData_APPDATA created \n" + bda+"\n ");
        return bda;

    }


    private void optionTypes(String appDataFlags) {
        System.out.println(appDataFlags);
         appDataOptions.put("OID",  appDataFlags.charAt(0)=='1');
        appDataOptions.put("EVFE", appDataFlags.charAt(1)=='1');
        appDataOptions.put("TM", appDataFlags.charAt(2)=='1');
    }

    private void prepareData(byte[] income) throws NumberArrayDataException {
        startAPPDATA = ByteFixedPositions.getAPPDATAStart(income);
        fdl = ByteFixValues.getFDLByteValue(income, startAPPDATA);
        fdlPos = ByteFixValues.getFDLNumberValue(fdl);
    }

}
