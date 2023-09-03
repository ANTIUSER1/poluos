package tnt.egts.parser.data.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentCreate;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.recResponse.SrRecordResponse;
import tnt.egts.parser.util.StringFixedBeanNames;



@Service (StringFixedBeanNames.RESPONSE_DATA_GENERATOR_BEAN)
@Slf4j
public class AuthRecordDataService implements OutcomeIdentCreate {

    @Autowired
    @Qualifier(StringFixedBeanNames.SR_RECORD_RESPONSE_DATA_BEAN)
    private OutcomeIdentCreate creator;

    @Override
    public OutcomeIdent create(ResponseDataStorage storage ) throws NumberArrayDataException {
        log.info("Storage PAuth Response data start");
        SrRecordResponse srRecResponse= (SrRecordResponse) creator.create(storage );
        AuthRecordData out=AuthRecordData.builder() 
                .subRecordLength((short) 3)
                .srRecResponse(srRecResponse)
                .build();
        out.prepareAuthData();
        log.info("Storage PAuth Response data finish: "+out);
        return out;
    }
}
