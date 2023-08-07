package tnt.egts.parser.cmmon.hd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.IncomeIdent;
import tnt.egts.parser.cmmon.IncomeIdentCreate;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixedPositions;

@Service
@Slf4j
public class HeadService implements IncomeIdentCreate {

    @Autowired
    private HeadOptionsService hoService;
    @Override
    public IncomeIdent create(IncomeDataStorage storage) throws NumberArrayDataException {
        log.info("Storage  income head Data start");
        byte[] income=storage.getPackageHeader();

        Head head=Head.builder()
                .protocolVersion(income[0])
                .securityKeyID(income[1])
                .flag(income[2])
                .headLength(income[3])
                .headEncoding(income[4])
                .frameDataLength(storage.getFrameDataLength())
                .packetIdentifier(storage.getPacketIdentifier())
                .headOptions((HeadOptions) hoService.create(storage))
                .crc8((byte) ByteFixedPositions.getHCSIndex(income))
                .build();


        log.info("Storage  income head Data finish");
        return head;
    }


}
