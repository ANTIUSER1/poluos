package tnt.egts.parser.cmmon.app;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.IncomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.util.ArrayUtils;

@Service
public class CommonAPPService implements IncomeIdentCreate {

    @Override
    public CommonAPPDATA create(IncomeDataStorage storage)   {

        AppDataOptions opt=AppDataOptions.builder().build();;
        short rl= recLength(storage.getPackagSFRD());
        short rn= recNum(storage.getPackagSFRD());
        CommonAPPDATA appdata=CommonAPPDATA.builder()
                .recordLength((short) storage.getPackagSFRD().length)
                .recordNum(rn++)
                .recordLength(rl)
                .build();

        return appdata;
    }

    @SneakyThrows
    private short recNum(byte[] packagSFRD) {
        byte[] out =ArrayUtils.getFixedLengthSubArray(packagSFRD,2,2);
        return ArrayUtils.byteArrayInverseToShort(out);
    }

    @SneakyThrows
    private short recLength(byte[] packagSFRD)   {
        byte[] out =ArrayUtils.getFixedLengthSubArray(packagSFRD,0,2);
        return ArrayUtils.byteArrayInverseToShort(out);
    }
}
