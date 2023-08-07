package tnt.egts.parser.cmmon.authService.response.ptResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.IncomeIdent;
import tnt.egts.parser.cmmon.IncomeIdentCreate;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecord;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecordService;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
@Service
public class ProtocolTypeResponseService implements OutcomeIdentCreate {

@Autowired
private  SeparateRecordService separateService;
    @Override
    public OutcomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        SeparateRecord separate= (SeparateRecord) separateService.create(storage);
        ProtocolTypeResponse ptr =ProtocolTypeResponse.builder()
               .responsePacketID(storage.getPacketIdentifier())
                .separateRecord(separate)
                .processingResult((byte) 0)
                .build();
        return ptr;
    }
}
