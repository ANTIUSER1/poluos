package tnt.egts.parser.data.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.Outcoming;
import tnt.egts.parser.data.validation.ResponseNormalCreate;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ProcessingResultCodeConstants;
import tnt.egts.parser.util.ArrayUtils;


@Service
@Slf4j
public class RESPONSECreator implements ResponseNormalCreate {


    byte[] tmpPid = new byte[2];

    @Autowired
    private RESPONSEHelper helper;
    @Autowired
    private CRC crc;

    @Override
    public Outcoming createNormalResponse(byte[] income, byte resultCode) {
        log.info("Response data to BNSO creation start");


        tmpPid[0] = income[7];
        tmpPid[1] = income[8];
        RESPONSE bdr =
                RESPONSE.builder()
                        .headBody(ArrayUtils.getSubArrayFromTo(income,
                                0, ByteFixValues.HEAD_MIN_LENGTH))
                        .pr(ProcessingResultCodeConstants.EGTS_PC_OK).build();
        bdr=helper.modify(bdr,resultCode);
//         bdr = changeFields(bdr, resultCode);
        log.info("Response data to BNSO creation finish:" + "\n " + bdr);
        return bdr;
    }



}
