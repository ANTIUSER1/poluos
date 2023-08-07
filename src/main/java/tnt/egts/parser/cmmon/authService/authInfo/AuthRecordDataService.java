package tnt.egts.parser.cmmon.authService.authInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.authService.response.recResponse.RecResponseCreator;
import tnt.egts.parser.cmmon.authService.response.recResponse.SrRecResponse;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

@Service
public class AuthRecordDataService implements OutcomeIdentCreate {

    @Autowired
    private RecResponseCreator srResp;
    @Override
    public OutcomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        SrRecResponse srRecResponse= (SrRecResponse) srResp.create(storage);
        AuthRecordData ard=AuthRecordData.builder()
                .subRecordLength((short) 3)
                .srRecResponse(srRecResponse)
                .build();
        return ard;
    }
}
