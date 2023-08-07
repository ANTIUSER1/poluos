package tnt.egts.parser.cmmon.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Storage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixedPositions;

@Service
public class IncomeDataStorageService implements Storage {

    @Autowired
    private CRC crc;

    @Override
    public IncomeDataStorage create(byte[] income) throws NumberArrayDataException {
        int hcsPos = ByteFixedPositions.getHCSIndex(income);
        IncomeDataStorage storage=IncomeDataStorage.builder()
                .fullPacket(income)
                .packageHeader(ArrayUtils.getSubArrayFromTo(income, 0, hcsPos + 1))
                .crc8(income[hcsPos])
                .packagSFRD(ArrayUtils.getSubArrayToEnd(income,hcsPos+1))
                .crc16(income[income.length-1])
                .packetType(income[9])
                .packetIdentifier(createPID(income))
                .frameDataLength(createFLD(income))
                .build();


        return storage;
    }

    private short createFLD(byte[] income) throws NumberArrayDataException {
        byte[] out =ArrayUtils.getFixedLengthSubArray(income,5,2);
        return ArrayUtils.byteArrayInverseToShort(out);
    }
    private short createPID(byte[] income) throws NumberArrayDataException {
        byte[] out = ArrayUtils.getFixedLengthSubArray(income,7,2);
        return ArrayUtils.byteArrayInverseToShort(out);
    }
}
