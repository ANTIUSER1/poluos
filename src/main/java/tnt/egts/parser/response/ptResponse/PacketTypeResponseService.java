package tnt.egts.parser.response.ptResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentCreate;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.separate.SeparateRecord;
import tnt.egts.parser.util.StringFixedBeanNames;

//@Service ("pt")
@Service(StringFixedBeanNames.PACKET_TYPE_RESPONSE_DATA_BEAN)
@Slf4j
public class PacketTypeResponseService implements OutcomeIdentCreate {


    @Autowired
    @Qualifier (StringFixedBeanNames.SEPARATE_RECORD_SERVICE_DATA_BEAN)
    private OutcomeIdentCreate creator;

    @Override
    public OutcomeIdent create(ResponseDataStorage storage) throws NumberArrayDataException {
        log.info("Storage Packet Type data start");
        SeparateRecord sr = (SeparateRecord) creator.create(storage);

        PacketTypeResponse out = PacketTypeResponse.builder()
                .responsePacketID(storage.getPacketIdentifier())
                .processingResult((byte) 0)
                .separateRecord(sr)
                .build();
        out.prepareAuthData();
        log.info("Storage Packet Type data finish: " + out);
        return out;
    }
}
