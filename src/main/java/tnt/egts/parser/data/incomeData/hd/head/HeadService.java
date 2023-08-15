package tnt.egts.parser.data.incomeData.hd.head;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.IncomeIdent;
import tnt.egts.parser.cmmon.IncomeIdentCreate;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ByteFixPositions;

@Service
@Slf4j
public class HeadService implements IncomeIdentCreate {

    @Autowired
    private HeadOptionsService hoService;
    @Override
    public IncomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        log.info("Storage  income head Data start");
        byte[] income=storage.getPackageHeader();

        Head out=Head.builder()
                .protocolVersion(income[0])
                .securityKeyID(income[1])
                .flag(income[2])
                .headLength(income[3])
                .headEncoding(income[4])
                .frameDataLength(storage.getFrameDataLength())
                .packetIdentifier(storage.getPacketIdentifier())
                .headOptions((HeadOptions) hoService.create(storage))
                .crc8((byte) ByteFixPositions.getHCSIndex(income))
                .build();
        log.info("Storage  income head Data finish: "+out);
        return out;
    }


}
