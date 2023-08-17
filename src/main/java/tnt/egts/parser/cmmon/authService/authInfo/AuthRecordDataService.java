package tnt.egts.parser.cmmon.authService.authInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.recResponse.SrRecordResponse;

@Service("authRecord")
@Slf4j
public class AuthRecordDataService implements OutcomeIdentCreate {

    @Autowired
    @Qualifier("sr")
    private OutcomeIdentCreate creator;

    @Override
    public OutcomeIdent create(IncomeDataStorage storage ) throws NumberArrayDataException {
        log.info("Storage PAuth Response data start");
        SrRecordResponse srRecResponse= (SrRecordResponse) creator.create(storage );
        AuthRecordData out=AuthRecordData.builder() 
                .subRecordLength((short) 3)
                .srRecResponse(srRecResponse)
                .build();
        out.prepareAuthData();
        System.out.println("SR  ");
        System.out.println("  OOOOOOO!!!OOOO  "+out);
        System.out.println("SR  ");
        System.out.println("SR BBBBBBBBB ");
        System.out.println("SR  SR  SrRecordResp  "+srRecResponse);
        System.out.println("SR  ");
        log.info("Storage PAuth Response data finish: "+out);
        return out;
    }
}
