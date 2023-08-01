package tnt.egts.parser.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.BodyData_RESPONSE;
import tnt.egts.parser.data.Outcoming;
import tnt.egts.parser.data.validation.ResponseNormalCreate;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.ProcessingResultCodeConstants;
import tnt.egts.parser.util.StringArrayUtils;


@Service
@Slf4j
public class ResponseCreateImpl implements ResponseNormalCreate {


    byte[] tmpPid = new byte[2];

    @Autowired
    private CRC crc;

    @Override
    public Outcoming createNormalResponse(byte[] income, byte resultCode) {
        log.info("Response data to BNSO creation start");
        tmpPid[0] = income[7];
        tmpPid[1] = income[8];
        BodyData_RESPONSE bdr =
                BodyData_RESPONSE.builder()
                        .headBody(StringArrayUtils.createSubArray(income,
                                0, ByteFixedPositions.HEAD_MIN_LENGTH))
                        .pr(ProcessingResultCodeConstants.EGTS_PC_OK).build();
         bdr = changeFields(bdr, resultCode);
        log.info("Response data to BNSO creation finish:" + "\n " + bdr);
        return bdr;
    }

    private BodyData_RESPONSE changeFields(BodyData_RESPONSE bdr,
                                           byte resultCode) {

        log.info("Response data to BNSO creation -- update process start" +
                 "\n " + bdr );
        bdr = changeDataBodyLength(bdr);
        bdr = changeDataFDL(bdr);
        bdr = changeDataResponseType(bdr, resultCode);
        bdr = createHeadBody(bdr);
        bdr = createResponse(bdr);
        bdr = createCRC8(bdr);
        bdr = createCRC16(bdr);
        bdr = createResponseBodyFinal(bdr);
        log.info("Response data to BNSO creation -- update process finish" + "\n " + bdr);
        return bdr;
    }

    private BodyData_RESPONSE createResponseBodyFinal(BodyData_RESPONSE bdr) {
        byte[] rbFinal = StringArrayUtils.joinArrays(bdr.getHeadBody(), bdr.getResponseBody());
        bdr.setResponseBody(rbFinal);
        return bdr;
    }

    private BodyData_RESPONSE createCRC8(BodyData_RESPONSE bdr) {
        log.info("CRC8 create for  " + bdr +" start" );
          byte crcV8 = (byte) crc.calculate8(bdr.getHeadBody());
        byte[] hb = StringArrayUtils.addByteToTail(bdr.getHeadBody(), crcV8);
        bdr.setHeadBody(hb);
        log.info("CRC8 create for  finish" );
        return bdr;
    }

    private BodyData_RESPONSE createCRC16(BodyData_RESPONSE bdr) {
        log.info("CRC16 create for  " + bdr +" start" );
        short crcV16 = (short) crc.calculate16(bdr.getResponseBody());
        byte[] checkSumm = StringArrayUtils.shortToByteArray(crcV16);
        byte[] rb = StringArrayUtils.joinArrays(bdr.getResponseBody(), StringArrayUtils.inverse(checkSumm));
        bdr.setResponseBody(rb);
        log.info("CRC16 create for  finish" );
        return bdr;
    }

    private BodyData_RESPONSE createResponse(BodyData_RESPONSE bdr) {
        byte[] responseBody = new byte[3];
        // responseBody[0]=bdr.getHeader().getHcs();
        responseBody[0] = tmpPid[0];
        responseBody[1] = tmpPid[1];
        responseBody[2] = bdr.getPr();
        bdr.setResponseBody(responseBody);
        return bdr;
    }

    private BodyData_RESPONSE createHeadBody(BodyData_RESPONSE bdr) {
        byte[] out = new byte[10];
        bdr.getHeadBody()[0]++;
        bdr.getHeadBody()[7] = 1;
        bdr.getHeadBody()[8] = 1;

        return bdr;
    }




    private BodyData_RESPONSE changeDataFDL(BodyData_RESPONSE bdr) {
        bdr.getHeadBody()[5] = 3;
        bdr.getHeadBody()[6] = 0;
        return bdr;
    }

    private BodyData_RESPONSE changeDataResponseType(BodyData_RESPONSE bdr, byte resultCode) {
        bdr.getHeadBody()[ByteFixedPositions.PACKAGE_TYPE_INDEX] = resultCode;
        return bdr;
    }

    private BodyData_RESPONSE changeDataBodyLength(BodyData_RESPONSE bdr) {
        bdr.getHeadBody()[ByteFixedPositions.HEAD_LENGTH_INDEX] =
                ByteFixedPositions.HEAD_MIN_LENGTH;
        return bdr;
    }


}
