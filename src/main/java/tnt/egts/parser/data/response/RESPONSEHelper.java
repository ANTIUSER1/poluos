package tnt.egts.parser.data.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.data.appdata.APPDATAService;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;

import java.nio.ByteBuffer;

@Component
@Slf4j
class RESPONSEHelper {

    byte[] tmpPid = new byte[2];

    @Autowired
    private CRC crc;

    @Autowired
    private APPDATAService appdataService;

    RESPONSE modify(RESPONSE bdr, byte resultCode) {
        log.info("Response data to BNSO creation -- update process start" +
                 "\n " + bdr);

        tmpPid[0] = bdr.getHeadBody()[7];
        tmpPid[1] = bdr.getHeadBody()[8];

//head
        bdr = changeDataBodyLength(bdr);
        bdr = changeDataFDL(bdr);
        bdr = changeDataResponseType(bdr, resultCode);
        bdr = createHeadBody(bdr);
        bdr = createCRC8(bdr);

//response
        bdr = createResponse(bdr);
//        bdr=createResponsSFRD(bdr);
        bdr = createCRC16(bdr);
        bdr = createResponseBodyFinal(bdr);
        log.info("Response data to BNSO creation -- update process finish" + "\n " + bdr);
        return bdr;


    }


    private RESPONSE createResponseBodyFinal(RESPONSE bdr) {
        byte[] rbFinal = ArrayUtils.joinArrays(bdr.getHeadBody(), bdr.getResponseBody());
        bdr.setResponseBody(rbFinal);
        return bdr;
    }


    private RESPONSE createCRC16(RESPONSE bdr) {
        log.info("CRC16 create for  " + bdr + " start");
        short crcV16 = (short) crc.calculate16(bdr.getResponseBody());
        byte[] checkSumm = ArrayUtils.shortToByteArray(crcV16);
        byte[] rb = ArrayUtils.joinArrays(bdr.getResponseBody(), ArrayUtils.inverse(checkSumm));
        bdr.setResponseBody(rb);
        log.info("CRC16 create for  finish");
        return bdr;
    }

    private RESPONSE createResponse(RESPONSE bdr) {
        byte[] responseBody = new byte[3];
        responseBody[0] = tmpPid[0];
        responseBody[1] = tmpPid[1];
        responseBody[2] = bdr.getPr();

        byte[] sfrdContent=createSFRD(bdr);




        bdr.setResponseBody(responseBody);
        return bdr;
    }

   void outputHeader(RESPONSE bdr){
        System.out.println("    HEADER");
        byte[] out=bdr.getHeadBody();
        ByteBuffer bbf=ByteBuffer.wrap( out);

        System.out.println("\n HEADad\n "  +ArrayUtils.arrayPrintToScreen( out )
                           + "  \n  FIELDS: ");


        System.out.println( "Version prf  *0* "+out[0]);
        System.out.println( "security *1*  "+out[1]);
        System.out.println( "flags *2*  "+out[2]+"   "+ArrayUtils.byteToBinary(out[2]) );
        System.out.println( "hl *3* "+out[3]);
       System.out.println( "he *4* "+out[4]);
       System.out.println( "fdl *5* *6* "+out[5]+ "  "+out[6]+ "  " +bbf.getShort( 5));
       System.out.println( "pid *7* *8* "+out[7]+ "  "+out[8]+ "  " +bbf.getShort( 7));
       System.out.println( "pt *9*  "+out[9]);
       System.out.println( "hcs *10* "+out[10]);


   }
    private byte[] createSFRD(RESPONSE bdr) {
outputHeader(bdr);

       byte[] inSFRD= appdataService.getAppDataRD();
       System.out.println("\n Response SFRD\n "  +ArrayUtils.arrayPrintToScreen( inSFRD)
                        + "  \n  FIELDS: ");
       byte resp=inSFRD[0];
       System.out.println("  sfrd- EGTS_PT_RESPONSE #0 "+ inSFRD[0]);

       return new byte[1];
    }

    private RESPONSE createCRC8(RESPONSE bdr) {
        log.info("CRC8 create for  " + bdr + " start");
        byte crcV8 = (byte) crc.calculate8(bdr.getHeadBody());
        byte[] hb = ArrayUtils.addByteToTail(bdr.getHeadBody(), crcV8);
        bdr.setHeadBody(hb);
        log.info("CRC8 create for  finish");
        return bdr;
    }

    private RESPONSE createHeadBody(RESPONSE bdr) {
      //  bdr.getHeadBody()[1]++;
        bdr.getHeadBody()[7] = 1;
        bdr.getHeadBody()[8] = 1;
        return bdr;
    }

    private RESPONSE changeDataResponseType(RESPONSE bdr, byte resultCode) {
        bdr.getHeadBody()[ByteFixedPositions.PACKAGE_TYPE_INDEX] =0  ;
        //resultCode
        return bdr;
    }

    private RESPONSE changeDataFDL(RESPONSE bdr) {
        bdr.getHeadBody()[5] = 3;
        bdr.getHeadBody()[6] = 0;
        return bdr;
    }

    private RESPONSE changeDataBodyLength(RESPONSE bdr) {
        bdr.getHeadBody()[ByteFixedPositions.HEAD_LENGTH_INDEX] =
                ByteFixValues.HEAD_MIN_LENGTH;
        return bdr;
    }
}
