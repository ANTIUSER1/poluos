package tnt.egts.parser.cmmon.authService.response.recResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;

@Service("sr")
@Slf4j
public class SrRecordResponseService implements OutcomeIdentCreate {

    @Override
    public OutcomeIdent create(IncomeDataStorage storage ) {
        log.info("Storage SrRecord start");
        SrRecordResponse out= SrRecordResponse.builder()
                .confirmRN((short) 10)
                .build();
        out.createData();
        log.info("Storage SrRecord finish: "+out);
        return out;
    }
}
