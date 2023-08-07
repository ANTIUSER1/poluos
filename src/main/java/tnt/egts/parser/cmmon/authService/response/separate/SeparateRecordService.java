package tnt.egts.parser.cmmon.authService.response.separate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.authService.authInfo.AuthRecordData;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

@Service ("separate")
public class SeparateRecordService implements OutcomeIdentCreate {

    @Autowired
    @Qualifier ("authRecord")
    private OutcomeIdentCreate creator;


    @Override
    public OutcomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        AuthRecordData ars = (AuthRecordData) creator.create(storage);
        SeparateRecord out = SeparateRecord.builder()
                .authRecordData(ars)

                .build();
        out.createData();
        return out;
    }
}
