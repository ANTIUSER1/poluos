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
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service (StringFixedBeanNames.DO_AUTH_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
@Slf4j
public class DoFinalAuthResponseService implements OutcomeIdentFinalCreate {

    @Autowired
    @Qualifier ("pt")
    OutcomeIdentCreate creator;

    @Autowired
    private CRC crc;

    @Override
    public OutcomeIdent createAuthResponse(IncomeDataStorage storage, byte code) throws NumberArrayDataException {
        PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage);
        PrepareedResponseData out = PrepareedResponseData.builder()
                .sfrd(pt.getData()).code(code)
                .responseHead(storage.getPackageHeader())
                .build();
        out.prepareAuthData();
        modyfySFRD(out, storage);
        log.info("Response data generate finish: " + out);

        return out;
    }

    @Override
    public OutcomeIdent createTeleDataResponse(IncomeDataStorage storage, byte code) throws NumberArrayDataException {
//        PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage);
//        PrepareedResponseData out = PrepareedResponseData.builder()
//                .sfrd(pt.getData()).code(code)
//                .responseHead(storage.getPackageHeader())
//                .build();
//        out.prepareAuthData();
//        modyfySFRD(out, storage);
//        log.info("Response data generate finish: " + out);

        return null;
    }

    private void modyfySFRD(PrepareedResponseData out, IncomeDataStorage storage) {
        int headLengthIndexIndex = ByteFixPositions.HEAD_LENGTH_INDEX;

        int headLen = storage.getPackageHeader().length;
        int headLen1 = storage.getPackageHeader()[headLengthIndexIndex];
        long crc8 = crc.calculateHead(out.getResponseHead());
        long crc16 = crc.calculateSfrd(out.getData());
        long crc16SFRD = crc.calculate16(out.getSfrd());

        byte[] crc16Array = ArrayUtils.shortToByteArray((short) crc16SFRD);
        crc16Array = ArrayUtils.inverse(crc16Array);

        byte[] data = out.getData();
        data[headLen - 1] = (byte) crc8;
        data = ArrayUtils.joinArrays(data, crc16Array);
        out.setData(data);


    }
}
