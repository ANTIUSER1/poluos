package tnt.egts.parser.data.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.NumberToBits;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.data.analysis.StorageBitsAnalizer;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.NumberUtils;

@Service
@Slf4j
public class IncomeDataStorageService implements Storage {

    private static final int FLAG_INDEX = 4;

    @Autowired
    private CRC crc;

    @Autowired
    private IncomeCollectionsService incomeCollectionsService;

    @Autowired
    private NumberToBits numberToBitsService;

    @Autowired
    private StorageBitsAnalizer bitsAnalizer;

    private int shiftPos;

    @Override
    public IncomeDataStorage create(byte[] income) throws NumberArrayDataException {
        log.info("Backup data generate start");
        int hcsPos = ByteFixPositions.getHCSIndex(income);
        IncomeDataStorage out = IncomeDataStorage.builder().fullPacket(income).packageHeader(ArrayUtils.getSubArrayFromTo(income, 0, hcsPos + 1)).crc8(income[hcsPos]).packagSFRD(ArrayUtils.getSubArrayToEnd(income, hcsPos + 1)).crc16(income[income.length - 1]).packageType(income[ByteFixPositions.PACKAGE_TYPE_INDEX]).recNum(createRN(income)).packetIdentifier(createPID(income)).frameDataLength(createFLD(income))
    .build();

        out.setFlags(numberToBitsService.bitsFromByte(out.getPackagSFRD()[FLAG_INDEX]));
        prepareStorageData(out);
        incomeCollectionsService.add(out);
        System.out.println();
        System.out.println();
        System.out.println("   ::::::::::::::::::::  ");
        System.out.println("   ::::::::::::::::::::  ");
        System.out.println("--------------------------------------");
        System.out.println("OUT.   flag штвуч " +  FLAG_INDEX );
        System.out.println("OUT. flag as byte " + out.getPackagSFRD()[FLAG_INDEX]);
        System.out.println("OUT.           oid " + out.getObjectIdentifier());
        System.out.println("OUT.          evid " + out.getEventIdentifier());
        System.out.println("OUT.            tm " + out.getTime());
        System.out.println("OUT.         prior " + out.getProcessingPriority());
        System.out.println("OUT.      RD-Start " + out.getLengthToRD());
        System.out.println("OUT.     sst index " + out.getSstIndex());
        System.out.println("OUT.     sst Value " + out.getPackagSFRD()[out.getSstIndex()]);
        System.out.println("OUT.     sst Value " + out.getPackagSFRD()[out.getSstIndex()]);
        System.out.println("OUT.   SteviceType " + out.getServiceType()  );
        System.out.println("OUT.        HEADER " + ArrayUtils.arrayPrintToScreen( out.getPackageHeader())  );
        System.out.println();
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

    private void prepareStorageData(IncomeDataStorage out) {
        System.out.println("FLAGS::   " + out.getFlags());
        System.out.println("FLAGS::---   " + out.getPackagSFRD()[FLAG_INDEX]);
        int n = 0;
        try {
            System.out.println("HHH- 00");
            System.out.println("HHH- 00");
            System.out.println("HHH- 00");
            if (bitsAnalizer.objectFieldExists(out.getFlags())) {
                setOID(out);
            }
            System.out.println("HHH- 01");
            System.out.println("HHH- 01");
            System.out.println("HHH- 00");
        } catch (NumberArrayDataException e) {
            log.error("Can not create OID  flag: \"+out.getFlags()");
            throw new RuntimeException(e);
        }
        try {
            System.out.println("HHH- 10");
            System.out.println("HHH- 10");
            System.out.println("HHH- 10");
            if (bitsAnalizer.eventFieldExists(out.getFlags())) {
                setEVID(out);
            }
            System.out.println("HHH- 11");
            System.out.println("HHH- 11");
            System.out.println("HHH- 11");
        } catch (NumberArrayDataException e) {
            log.error("Can not create EVID  flag: " + out.getFlags());
            throw new RuntimeException(e);
        }
        try {
            System.out.println("HHH- 02");
            System.out.println("HHH- 02");
            System.out.println("HHH- 02");
            if (bitsAnalizer.timeFieldExists(out.getFlags())) {
                seTM(out);
            }
            System.out.println("HHH- 12");
            System.out.println("HHH- 12");
            System.out.println("HHH- 12");
        } catch (NumberArrayDataException e) {
            log.error("Can not create TM  flag: \"+out.getFlags()");
            throw new RuntimeException(e);
        }
        out.setProcessingPriority(bitsAnalizer.getRecordProcessingPriority(out.getFlags()));
        out.setInGroup(bitsAnalizer.inGroup(out.getFlags()));
        out.setReciplentServiceOnDevice(bitsAnalizer.reciplentServiceOnDevice(out.getFlags()));
        out.setSourceServiceOnDevice(bitsAnalizer.sourceServiceOnDevice(out.getFlags()));

        out.setServiceType(out.getByTypeID( out.getPackagSFRD()[out.getSstIndex()]));
        out.setLengthToRD(FLAG_INDEX + shiftPos + 2);
        out.setSstIndex(FLAG_INDEX + shiftPos + 1);
System.out.println();
System.out.println();
System.out.println(  "QQQQQQQQQQQQQQQQQQQQ");
System.out.println();
System.out.println();

    }

    private void seTM(IncomeDataStorage out) throws NumberArrayDataException {
        byte[] intArray = ArrayUtils.getFixedLengthSubArray(out.getPackagSFRD(), FLAG_INDEX + 1 + shiftPos, 4);
        out.setTime(NumberUtils.byteArrayInverseToInt(intArray));
        shiftPos += 4;
    }

    private void setEVID(IncomeDataStorage out) throws NumberArrayDataException {
        byte[] intArray = ArrayUtils.getFixedLengthSubArray(out.getPackagSFRD(), FLAG_INDEX + 1 + shiftPos, 4);
        out.setEventIdentifier(NumberUtils.byteArrayInverseToInt(intArray));
        shiftPos += 4;
    }

    private void setOID(IncomeDataStorage out) throws NumberArrayDataException {
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
