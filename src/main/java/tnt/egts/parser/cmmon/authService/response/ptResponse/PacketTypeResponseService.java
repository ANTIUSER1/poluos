package tnt.egts.parser.cmmon.authService.response.ptResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecord;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

@Service("pt")
public class PacketTypeResponseService implements OutcomeIdentCreate {


    @Autowired
    @Qualifier ("separate")
    private OutcomeIdentCreate creator;

    @Override
    public OutcomeIdent create(IncomeDataStorage storage ) throws NumberArrayDataException {
        SeparateRecord sr= (SeparateRecord) creator.create(storage);
             PacketTypeResponse out =PacketTypeResponse.builder()
                     .responsePacketID(storage.getPacketIdentifier())
                     .processingResult((byte) 0)
                     .separateRecord(sr)
                .build();
        out.createData();
        return out;
    }
}
