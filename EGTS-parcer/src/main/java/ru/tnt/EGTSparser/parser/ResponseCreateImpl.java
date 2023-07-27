package ru.tnt.EGTSparser.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.crc.service.CRC;
import ru.tnt.EGTSparser.data.BodyData_RESPONSE;
import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;
import ru.tnt.EGTSparser.dataProcessing.ResponseNormalCreate;
import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.ProcessingResultCodeConstants;
import ru.tnt.EGTSparser.util.StringArrayUtils;

@Service
@Slf4j
public class ResponseCreateImpl implements ResponseNormalCreate {


    @Autowired
    private CRC crc;

    @Override
    public Outcoming createNormalResponse(HeaderData hd) {
        log.info("Response data to BNSO creation start");
        BodyData_RESPONSE bdr = BodyData_RESPONSE.builder()
                .header(hd).pr(ProcessingResultCodeConstants.EGTS_PC_OK)
                .build();
        bdr = changeDataBodyLength(bdr);
        bdr = changeDataFDL(bdr);
        bdr = changeDataResponseType(bdr);
        bdr = changeHeaherOptopnals(bdr);
        bdr=createHeadBody(bdr) ;
        bdr = createResponse(bdr);
        bdr = createCRC8(bdr);
        bdr = createCRC16(bdr);
        bdr = createResponseBodyFinal(bdr);
        log.info("Response data to BNSO creation finish" +
                "\n " + bdr);
        return bdr;
    }

    private BodyData_RESPONSE createResponseBodyFinal(BodyData_RESPONSE bdr) {
        byte[] rbFinal = StringArrayUtils.joinArrays(bdr.getHeadBody(), bdr.getResponseBody());
        bdr.setResponseBody(rbFinal);
        return bdr;
    }

    private BodyData_RESPONSE createCRC8(BodyData_RESPONSE bdr) {
        System.out.println(" \n\n  BDR  "+bdr+" \n\n ");
        byte crcV8 = (byte) crc.calculate8(bdr.getHeadBody());
               byte[] hb = StringArrayUtils.addByteToTail(bdr.getHeadBody(), crcV8);
        bdr.setHeadBody(hb);
        return bdr;
    }

    private BodyData_RESPONSE createCRC16(BodyData_RESPONSE bdr) {
        short crcV16 = (short) crc.calculate16(bdr.getResponseBody());
        byte[] checkSumm = StringArrayUtils.shortToByteArray(crcV16);
        byte[] rb = StringArrayUtils.joinArrays(bdr.getResponseBody(), StringArrayUtils.inverse(checkSumm));
        bdr.setResponseBody(rb);
        return bdr;
    }

    private BodyData_RESPONSE createResponse(BodyData_RESPONSE bdr) {
        byte[] responseBody = new byte[3];
        // responseBody[0]=bdr.getHeader().getHcs();
        responseBody[0] = bdr.getHeader().getPid()[0];
        responseBody[1] = bdr.getHeader().getPid()[1];
        responseBody[2] = bdr.getPr();
        bdr.setResponseBody(responseBody);
        return bdr;
    }

    private BodyData_RESPONSE  createHeadBody(BodyData_RESPONSE bdr) {
        byte[] out = new byte[10];
        out[0] = (byte) (bdr.getHeader().getPrv() + 1);
        out[1] = bdr.getHeader().getSkid();
        out[2] = bdr.getHeader().getPrf();
        out[3] = bdr.getHeader().getHl();
        out[4] = bdr.getHeader().getHe();
        out[5] = bdr.getHeader().getFdl()[0];
        out[6] = bdr.getHeader().getFdl()[1];
        out[7] = 120;//bdr.getHeader().getPid()[0];
        out[8] = -120;//bdr.getHeader().getPid()[1];
        out[9] = bdr.getHeader().getPt();
bdr.setHeadBody(out);
        return bdr;
    }

    private BodyData_RESPONSE changeHeaherOptopnals(BodyData_RESPONSE bdr) {
        bdr.getHeader().setRca(null);
        bdr.getHeader().setPra(null);
        bdr.getHeader().setTtl(null);
        return bdr;
    }


    private BodyData_RESPONSE changeDataFDL(BodyData_RESPONSE bdr) {
        bdr.getHeader().getFdl()[0] = 3;
        bdr.getHeader().getFdl()[1] = 0;
        return bdr;
    }

    private BodyData_RESPONSE changeDataResponseType(BodyData_RESPONSE bdr) {
        bdr.getHeader().setPt((byte) 0);
        return bdr;
    }

    private BodyData_RESPONSE changeDataBodyLength(BodyData_RESPONSE bdr) {
        bdr.getHeader().setHl((byte) ByteFixedPositions.HEAD_MIN_LENGTH);
        return bdr;
    }


}
