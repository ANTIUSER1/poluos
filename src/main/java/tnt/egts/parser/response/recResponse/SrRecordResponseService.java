package tnt.egts.parser.response.recResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentCreate;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service(StringFixedBeanNames.SR_RECORD_RESPONSE_DATA_BEAN)
@Slf4j
public class SrRecordResponseService implements OutcomeIdentCreate {

    @Override
    public OutcomeIdent create(ResponseDataStorage storage ) {
        log.info("Storage SrRecord start");
        SrRecordResponse out= SrRecordResponse.builder()
                .confirmRN(storage.getRecNum())
                .build();
        out.prepareAuthData();

        log.info("Storage SrRecord finish: "+out);
        return out;
    }
}
