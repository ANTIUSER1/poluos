package tnt.egts.parser.data.sendBack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.commontasks.OutcomeIdentCreate;
import tnt.egts.parser.commontasks.OutcomeIdentFinalCreate;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.ptResponse.PacketTypeResponse;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service (StringFixedBeanNames.DO_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
@Slf4j
public class DoFinalResponseService implements OutcomeIdentFinalCreate {

    @Autowired
    @Qualifier (StringFixedBeanNames.PACKET_TYPE_RESPONSE_DATA_BEAN)
    OutcomeIdentCreate creator;

    @Autowired
    @Qualifier(StringFixedBeanNames.CRC_SERVICE_DATA_BEAN)
    private CRC crc;

    @Override
    public OutcomeIdent createResponse(ResponseDataStorage storage, byte code) throws NumberArrayDataException {

        PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage);
        PrepareedResponseData out = PrepareedResponseData.builder()
                .sfrd(pt.getData()).code(code)
                .responseHead(storage.getPackageHeader())
                .build();
        out.prepareAuthData();
        out=   modyfySFRD(out, storage);
        log.info("Response data generate finish: " + out);

        return out;
    }


    private PrepareedResponseData modyfySFRD(PrepareedResponseData out, ResponseDataStorage storage) {
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


        System.out.println("OUT;;;  ");
        System.out.println("****************;;;  ");
        System.out.println("   "+out);
        System.out.println("****************;;;  ");
        System.out.println("OUT;;;  ");

return  out;
    }
}
