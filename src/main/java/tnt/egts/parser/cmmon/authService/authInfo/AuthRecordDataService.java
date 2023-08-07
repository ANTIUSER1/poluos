package tnt.egts.parser.cmmon.authService.authInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.authService.response.recResponse.SrRecordResponse;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

@Service("authRecord")
public class AuthRecordDataService implements OutcomeIdentCreate {

    @Autowired
    @Qualifier("sr")
    private OutcomeIdentCreate creator;

    @Override
    public OutcomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        SrRecordResponse srRecResponse= (SrRecordResponse) creator.create(storage);
        AuthRecordData out=AuthRecordData.builder() 
                .subRecordLength((short) 3)
                .srRecResponse(srRecResponse)
                .build();
        out.createData();
        return out;
    }
}
