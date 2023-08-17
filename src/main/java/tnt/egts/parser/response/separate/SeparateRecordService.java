package tnt.egts.parser.response.separate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.authService.authInfo.AuthRecordData;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

@Service ("separate")
@Slf4j
public class SeparateRecordService implements OutcomeIdentCreate {

    @Autowired
    @Qualifier ("authRecord")
    private OutcomeIdentCreate creator;


    @Override
    public OutcomeIdent create(IncomeDataStorage storage ) throws NumberArrayDataException {
        log.info("Storage  separate Data start");
        AuthRecordData ard = (AuthRecordData) creator.create(storage);
        SeparateRecord out = SeparateRecord.builder()
                .authRecordData(ard)
                .recordNumber(storage.getRecNum())
                .build();


        out.prepareAuthData();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("KKKKKKKKKKKKKKKKKKKK "+ard);
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("KKKKK  SeparateRecord  "+out);
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKK");


        log.info("Storage  separate Data finish: "+out);
        return out;
    }
}
