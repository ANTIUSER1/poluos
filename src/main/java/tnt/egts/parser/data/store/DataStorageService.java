package tnt.egts.parser.data.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.NumberToBits;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.data.analysis.StorageBitsAnalizer;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.NumberUtils;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service(StringFixedBeanNames.RESPONSE_DATA_STOREGE_SERVICE_BEAN)
@Slf4j
public class DataStorageService implements Storage {

    private static final int FLAG_INDEX = 4;

    private static final int SST_INDEX = FLAG_INDEX + 3 * 4;

    @Autowired
    @Qualifier (StringFixedBeanNames.CRC_SERVICE_DATA_BEAN)
    private CRC crc;

    @Autowired
    private IncomeCollectionsService incomeCollectionsService;

    @Autowired
    private NumberToBits numberToBitsService;

    @Autowired
    private StorageBitsAnalizer bitsAnalizer;

    private int shiftPos;

    @Override
    public ResponseDataStorage createResponseStorage(byte[] income) throws NumberArrayDataException {
        log.info("Backup data generate start");
        shiftPos = 0;
        int hcsPos = ByteFixPositions.getHCSIndex(income);
        ResponseDataStorage out = ResponseDataStorage.builder().fullPacket(income).packageHeader(ArrayUtils.getSubArrayFromTo(income, 0, hcsPos + 1)).crc8(income[hcsPos]).packagSFRD(ArrayUtils.getSubArrayToEnd(income, hcsPos + 1)).crc16(income[income.length - 1]).packageType(income[ByteFixPositions.PACKAGE_TYPE_INDEX]).recNum(createRN(income)).packetIdentifier(createPID(income)).frameDataLength(createFLD(income)).build();

        prepareStorageData(out);

        System.out.println();
        System.out.println();
        System.out.println("   ::::::::::::::::::::  ");
        System.out.println("   ::::::::::::::::::::  ");
        System.out.println("--------------------------------------");
        System.out.println("--------------------------------------");
        System.out.println(":  FLAGS :: "+out.getFlags());
        System.out.println( "OID : "+(bitsAnalizer.objectFieldExists(out.getFlags()) ));
        System.out.println( "GRP: "+(  out.isInGroup()));
        System.out.println( "TM: "+  bitsAnalizer.timeFieldExists(out.getFlags()));
        System.out.println( "EVID: "+ bitsAnalizer.eventFieldExists(out.getFlags()));
        System.out.println( "OID exists: "+(bitsAnalizer.objectFieldExists(out.getFlags()) || out.isInGroup()));
        System.out.println( "RPP: "+bitsAnalizer.getRecordProcessingPriority(out.getFlags()) );
        System.out.println( "SSOD: "+bitsAnalizer.sourceServiceOnDevice(out.getFlags()));
        System.out.println( "RSOD: "+bitsAnalizer.reciplentServiceOnDevice(out.getFlags()));
        System.out.println();
        System.out.println();
        System.out.println("OUT.    flag index " +  FLAG_INDEX );
        System.out.println("OUT.  flag as byte " + out.getPackagSFRD()[FLAG_INDEX]);
        System.out.println("OUT.           oid " + out.getObjectIdentifier());
        System.out.println("OUT.          evid " + out.getEventIdentifier());
        System.out.println("OUT.            tm " + out.getTime());
        System.out.println("OUT.           grp " + out.isInGroup());
        System.out.println("OUT.         prior " + out.getProcessingPriority());
        System.out.println("OUT.      RD-Start " + out.getLengthToRD());
        System.out.println("OUT.     sst index " + out.getSstIndex());
        System.out.println("OUT.     sst Value " + out.getPackagSFRD()[out.getSstIndex()]);
        System.out.println("OUT.   ServiceType " + out.getServiceType()  );
        System.out.println("OUT.        HEADER " + ArrayUtils.arrayPrintToScreen( out.getPackageHeader())  );
        System.out.println();
        System.out.println("OUT.  SFRD " + ArrayUtils.arrayPrintToScreen( out.getPackagSFRD())  );
        System.out.println();
        System.out.println();
        System.out.println("OUT.  full " + ArrayUtils.arrayPrintToScreen( out.getFullPacket())  );
        System.out.println();
        System.out.println();

        log.info("Backup data generate finish: " + out);
        return out;
    }

    @Override
    public BNSODataStorage createBNSOStorage(byte[] income) throws NumberArrayDataException {
        return null;
    }

    private void prepareStorageData(ResponseDataStorage out) {
        log.info("set additional data to IncomeDataStorage start");
        out.setFlags(numberToBitsService.bitsFromByte(out.getPackagSFRD()[FLAG_INDEX]));
        try {
            if (bitsAnalizer.objectFieldExists(out.getFlags()) || out.isInGroup()) {
                setOID(out);
            }
        } catch (NumberArrayDataException e) {
            log.error("Can not create OID  flag: \"+out.getFlags()");
            throw new RuntimeException(e);
        }
        try {
            if (bitsAnalizer.eventFieldExists(out.getFlags())) {
                setEVID(out);
            }
        } catch (NumberArrayDataException e) {
            log.error("Can not create EVID  flag: " + out.getFlags());
            throw new RuntimeException(e);
        }
        try {
            if (bitsAnalizer.timeFieldExists(out.getFlags())) {
                seTM(out);
            }
        } catch (NumberArrayDataException e) {
            log.error("Can not create TM  flag: \"+out.getFlags()");
            throw new RuntimeException(e);
        }
        out.setProcessingPriority(bitsAnalizer.getRecordProcessingPriority(out.getFlags()));
        out.setInGroup(bitsAnalizer.inGroup(out.getFlags()));
        out.setReciplentServiceOnDevice(bitsAnalizer.reciplentServiceOnDevice(out.getFlags()));
        out.setSourceServiceOnDevice(bitsAnalizer.sourceServiceOnDevice(out.getFlags()));
        out.setInGroup(bitsAnalizer.inGroup(out.getFlags()));

        out.setSstIndex(FLAG_INDEX + shiftPos + 1);
        out.setServiceType(out.getByTypeID(out.getPackagSFRD()[out.getSstIndex()]));

        log.info("set additional data to IncomeDataStorage finish");
    }

    private void seTM(ResponseDataStorage out) throws NumberArrayDataException {
        log.info("set additional time data to IncomeDataStorage start");
        byte[] intArray = ArrayUtils.getFixedLengthSubArray(out.getPackagSFRD(), FLAG_INDEX + 1 + shiftPos, 4);
        out.setTime(NumberUtils.byteArrayInverseToInt(intArray));
        shiftPos += 4;
    }

    private void setEVID(ResponseDataStorage out) throws NumberArrayDataException {
        log.info("set additional evidid data to IncomeDataStorage start");
        byte[] intArray = ArrayUtils.getFixedLengthSubArray(out.getPackagSFRD(), FLAG_INDEX + 1 + shiftPos, 4);
        out.setEventIdentifier(NumberUtils.byteArrayInverseToInt(intArray));
        shiftPos += 4;
    }

    private void setOID(ResponseDataStorage out) throws NumberArrayDataException {
        log.info("set additional oid data to IncomeDataStorage start");
        byte[] intArray = ArrayUtils.getFixedLengthSubArray(out.getPackagSFRD(), FLAG_INDEX + 1, 4);
        out.setObjectIdentifier(NumberUtils.byteArrayInverseToInt(intArray));
        shiftPos += 4;
    }

    private short createRN(byte[] income) throws NumberArrayDataException {
        byte[] out = ArrayUtils.getFixedLengthSubArray(income, 2 + 1 + ByteFixPositions.getHCSIndex(income), 2);
        return NumberUtils.byteArrayInverseToShort(out);
    }

    private short createFLD(byte[] income) throws NumberArrayDataException {
        byte[] out = ArrayUtils.getFixedLengthSubArray(income, 5, 2);
        return NumberUtils.byteArrayInverseToShort(out);
    }

    private short createPID(byte[] income) throws NumberArrayDataException {
        byte[] out = ArrayUtils.getFixedLengthSubArray(income, 7, 2);
        return NumberUtils.byteArrayInverseToShort(out);
    }
}
