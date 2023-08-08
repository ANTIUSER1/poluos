package tnt.egts.parser.cmmon.authService.response.recResponse;

import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;

@Service("sr")
public class SrRecordResponseService implements OutcomeIdentCreate {

    @Override
    public OutcomeIdent create(IncomeDataStorage storage ) {
        SrRecordResponse out= SrRecordResponse.builder()
                .confirmRN((short) 10)
                .build();
        out.createData();
        return out;
    }
}