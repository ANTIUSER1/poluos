package tnt.egts.parser.data.sendBack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentCreate;
import tnt.egts.parser.commontasks.OutcomeIdentFinalCreate;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.ptResponse.PacketTypeResponse;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service (StringFixedBeanNames.DO_TELEDATA_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
@Slf4j
public class DoFinalTeledataResponseService implements OutcomeIdentFinalCreate {


    @Autowired
    @Qualifier ("pt")
    OutcomeIdentCreate creator;

    @Autowired
    private CRC crc;


    @Override
    public OutcomeIdent createAuthResponse(IncomeDataStorage storage, byte code) throws NumberArrayDataException {
        return null;
    }

    @Override
    public OutcomeIdent createTeleDataResponse(IncomeDataStorage storage, byte code) throws NumberArrayDataException {
        PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage);
        PrepareedResponseData out = PrepareedResponseData.builder()
                .sfrd(pt.getData()).code(code)
                .responseHead(storage.getPackageHeader())
                .build();
        out.prepareAuthData();
     //   modyfySFRD(out, storage);
        log.info("Response data generate finish: " + out);

        return out;
    }
}
