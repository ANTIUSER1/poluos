package ru.tnt.EGTSparser.parser.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.data.BodyData_APPDATA;
import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.StringArrayUtils;

@Service
@Slf4j
public class APPDATAOptCreatorTool {

    boolean hasTmField;

    byte headerLength;
    boolean hasEvField;
    boolean hasObField;
    private int siftAction;

    //
    public int getSiftAction() {
        return siftAction;
    }

    public byte getHeaderLength() {
        return headerLength;
    }

    public void setShiftAction(byte[] data) {
        log.info("Initialize start shift for array index in head sensitive part ");
        this.siftAction = headerLength;
        headerLength = data[ByteFixedPositions.HEAD_LENGTH_INDEX];
        hasTmField = tmExist(data[headerLength + 4]);
        hasEvField = evExist(data[headerLength + 4]);
        hasObField = obExist(data[headerLength + 4]);
    }

    public BodyData_APPDATA optCreateOID(BodyData_APPDATA bda, byte[] data ) {
        log.info("Creating OID field");
        if (hasObField) {
            bda.setOid(new Byte[4]);
            // data[headerLength+8] data[headerLength+7]
            // data[headerLength+6] data[headerLength+5]
            bda.getOid()[0] = data[headerLength + 5];//16
            bda.getOid()[1] = data[headerLength + 6];//17
            bda.getOid()[2] = data[headerLength + 7];//18
            bda.getOid()[3] = data[headerLength + 8];//19
            siftAction += 4;
        }
        return bda;
    }


    public BodyData_APPDATA optCreateEVID(BodyData_APPDATA bda, byte[] data ) {
        log.info("Creating EVID field");
        if (hasEvField) {
            bda.setEvid(new Byte[4]);
            // data[headerLength+12] data[headerLength+11]
            // data[headerLength+10] data[headerLength+9]
            bda.getEvid()[0] = data[headerLength + 9];//20
            bda.getEvid()[1] = data[headerLength + 10];//21
            bda.getEvid()[2] = data[headerLength + 11];//22
            bda.getEvid()[3] = data[headerLength + 12];//23
            siftAction += 4;
        }
        return bda;
    }

    public BodyData_APPDATA optCreateTm(BodyData_APPDATA bda, byte[] data ) {
        log.info("Creating TM field");
        if (hasTmField) {
            bda.setEvid(new Byte[4]);
            // data[headerLength+16] data[headerLength+15]
            // data[headerLength+14] data[headerLength+13]
            bda.getTm()[0] = data[headerLength + 13];//24
            bda.getTm()[1] = data[headerLength + 14];//25
            bda.getTm()[2] = data[headerLength + 15];//26
            bda.getTm()[3] = data[headerLength + 16];//27
            siftAction += 4;
        }
        return bda;
    }


    private boolean tmExist(byte val) {
        String test = StringArrayUtils.byteToBinary(val);
        return test.charAt(test.length() - 1 - 2) == '1';
    }

    private boolean evExist(byte val) {
        String test = StringArrayUtils.byteToBinary(val);
        return test.charAt(test.length() - 1 - 1) == '1';
    }

    private boolean obExist(byte val) {
        String test = StringArrayUtils.byteToBinary(val);
        return test.charAt(test.length() - 1) == '1';
    }
}
