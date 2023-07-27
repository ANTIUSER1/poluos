package ru.tnt.EGTSparser.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.crc.service.CRC;
import ru.tnt.EGTSparser.dataProcessing.ResponseNormalCreate;
import ru.tnt.EGTSparser.data.BodyData_RESPONSE;
import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;
import ru.tnt.EGTSparser.util.ByteFixedPositions;
import ru.tnt.EGTSparser.util.ProcessingResultCodeConstants;
import ru.tnt.EGTSparser.util.StringArrayUtils;

@Service
public class ResponseCreateImpl implements ResponseNormalCreate {


    @Autowired
    private CRC crc;
    @Override
    public Outcoming createNormalResponce(HeaderData hd) {

       BodyData_RESPONSE bdr= BodyData_RESPONSE.builder()
                .header(hd)
                .pr(ProcessingResultCodeConstants.EGTS_PC_OK)
                .build();

        bdr= changedataBodyLength(bdr);
        bdr= changedataFDL(bdr);
        bdr= changedataResponseType(bdr);
        bdr= changeHeaherOptopnals(bdr);
        bdr.setHeadBody(createHeadBody(bdr));
        bdr=createResponse(bdr);

        //*****************************************

long[] tmp=StringArrayUtils.byteToLong(bdr.getResponseBody());
        System.out.println("TMP  "+StringArrayUtils.arrayPrintToScreen(tmp));
        System.out.println("BDR  "+StringArrayUtils.arrayPrintToScreen(bdr.getResponseBody()));
        System.out.println(" 62 628  "+  (  (short) 62628)  );
        System.out.println("********** 62 crc  "+  crc.calculate16(tmp) );


//*******************************************************

        short crcV16 =(short) crc.calculate16(StringArrayUtils.byteToLong(bdr.getResponseBody()));
        System.out.println("   -------------  crc16  "+crcV16);
        byte[] checkSumm=StringArrayUtils.shortToByteArray(crcV16);
        byte[] rb=StringArrayUtils.joinArrays(bdr.getResponseBody(), StringArrayUtils.inverse( checkSumm) );

        bdr.setResponseBody(rb);
        System.out.println("    BR RESP BODY LEN "+bdr.getResponseBody().length);

        byte crcV8= (byte) crc.calculate8(StringArrayUtils.byteToLong(bdr.getHeadBody()));
System.out.println("   -------------  crc8  "+crcV8);
byte[] hb=StringArrayUtils.addByteToTail(bdr.getHeadBody(),crcV8) ;
bdr.setHeadBody(hb);
rb = StringArrayUtils.joinArrays(bdr.getHeadBody() , bdr.getResponseBody());
        bdr.setResponseBody(rb);
        System.out.println("    BR RESP BODY LEN "+bdr.getResponseBody().length);


        return bdr;
    }

    private BodyData_RESPONSE createFullResponse(BodyData_RESPONSE bdr, short crc) {
        byte[] responseBody=new byte[6];
        byte[] checkSumm=StringArrayUtils.shortToByteArray(crc );
        responseBody[0]=bdr.getHeader().getHcs();
        responseBody[1]=bdr.getHeader().getPid()[0];
        responseBody[2]=bdr.getHeader().getPid()[1];
        responseBody[3]=bdr.getPr();
        responseBody[4]=checkSumm[1];
        responseBody[5]=checkSumm[0];

        System.out.println("CHECK:: "+StringArrayUtils.arrayPrintToScreen(checkSumm));
        bdr.setResponseBody(responseBody);
        return bdr;
    }

    private BodyData_RESPONSE createResponse(BodyData_RESPONSE bdr) {
        byte[] responseBody=new byte[3];
       // responseBody[0]=bdr.getHeader().getHcs();
        responseBody[0]=bdr.getHeader().getPid()[0];
        responseBody[1]=bdr.getHeader().getPid()[1];
        responseBody[2]=bdr.getPr();
        bdr.setResponseBody(responseBody);
        return bdr;
    }

    private byte[] createHeadBody(BodyData_RESPONSE bdr) {
        byte[] out=  new byte[10];
        out[0]= (byte) (bdr.getHeader().getPrv()+1);
        out[1]=bdr.getHeader().getSkid();
        out[2]=bdr.getHeader().getPrf();
        out[3]=bdr.getHeader().getHl();
        out[4]=bdr.getHeader().getHe();
        out[5]=bdr.getHeader().getFdl()[0];
        out[6]=bdr.getHeader().getFdl()[1];
        out[7]=120;//bdr.getHeader().getPid()[0];
        out[8]=-120;//bdr.getHeader().getPid()[1];
        out[9]=bdr.getHeader().getPt();

        return out;
    }

    private BodyData_RESPONSE changeHeaherOptopnals(BodyData_RESPONSE bdr) {
        bdr.getHeader().setRca(null);
        bdr.getHeader().setPra(null);
        bdr.getHeader().setTtl(null);
        return bdr;
    }


    private BodyData_RESPONSE changedataFDL(BodyData_RESPONSE bdr) {
        bdr.getHeader().getFdl()[0]=3;
        bdr.getHeader().getFdl()[1]=0;
        return bdr;
    }

    private BodyData_RESPONSE changedataResponseType(BodyData_RESPONSE bdr) {
        bdr.getHeader().setPt((byte) 0);return bdr;
    }

    private BodyData_RESPONSE changedataBodyLength(BodyData_RESPONSE bdr) {
        bdr.getHeader().setHl((byte) ByteFixedPositions.HEAD_MIN_LENGTH);return bdr;
    }


}
