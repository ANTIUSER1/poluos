package tnt.egts.parser.cmmon.sendBack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.OutcomeIdentFinalCreate;
import tnt.egts.parser.cmmon.authService.response.ptResponse.PacketTypeResponse;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.errors.NumberArrayDataException;

@Service("readyToSend")
public class DoResponseService implements OutcomeIdentFinalCreate {

@Autowired
private CRC crc;

    @Autowired
    @Qualifier ("pt")
    OutcomeIdentCreate creator;
    @Override
    public OutcomeIdent create(IncomeDataStorage storage, byte code) throws NumberArrayDataException {
        PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage );
        DoResponse out=DoResponse.builder()
                .sfrd(pt.getData())
                .code(code)
                .responseHead(storage.getPackageHeader())
                .build();
        short crc8= (short) crc.calculate8(storage.getPackageHeader());

        out.getResponseHead()[9]=0;

        out.createData();
        return out;
    }
}
