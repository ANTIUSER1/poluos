package tnt.egts.parser.data.incomeData.hd.head;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.IncomeIdent;
import tnt.egts.parser.cmmon.IncomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.data.analysis.BitAnalysisService;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;

@Service
@Slf4j
public class HeadOptionsService implements IncomeIdentCreate {

    @Autowired
    private BitAnalysisService bitAnalizer;


    @Override
    public IncomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        log.info("Storage  income head options Data start  "
        +ArrayUtils.arrayPrintToScreen(storage.getPackageHeader()));
        byte income = storage.getPackageHeader()[2];
        if(hasRTE(ArrayUtils.byteToBinary(income))){
            log.info("Storage  income head options Data finish : Returned NULL ");
            return null;
        }
        byte[] data =
                ArrayUtils.getFixedLengthSubArray(storage.getPackageHeader(), 10, 5);
         HeadOptions out = HeadOptions.builder().build();
            short pra = createPRA(data);
            out.setPeerAddres(pra);
            short rca = createRCA(data);
            out.setRecipientAddress(rca);
            out.setTimeToLive(data[4]);
        log.info("Storage  income head options Data finish: "+out);
        return out;
    }

    private short createRCA(byte[] income) throws NumberArrayDataException {
        byte[] out = ArrayUtils.getFixedLengthSubArray(income, 2, 2);
        return ArrayUtils.byteArrayInverseToShort(out);
    }

    private short createPRA(byte[] income) throws NumberArrayDataException {
        byte[] out = ArrayUtils.getFixedLengthSubArray(income, 0, 2);
        return ArrayUtils.byteArrayInverseToShort(out);
    }


    private boolean hasRTE(String testSt) {
        return testSt.charAt(5) == '0';
    }
}
