package tnt.egts.parser.response.separate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentCreate;
import tnt.egts.parser.data.auth.AuthRecordData;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service(StringFixedBeanNames.SEPARATE_RECORD_SERVICE_DATA_BEAN) //("separate")
@Slf4j
public class SeparateRecordService implements OutcomeIdentCreate {

    @Autowired

    @Qualifier (StringFixedBeanNames.RESPONSE_DATA_GENERATOR_BEAN)
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

        log.info("Storage  separate Data finish: "+out);
        return out;
    }
}
