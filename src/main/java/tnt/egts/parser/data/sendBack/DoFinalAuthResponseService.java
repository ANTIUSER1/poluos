package tnt.egts.parser.data.sendBack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.OutcomeIdentFinalCreate;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.response.ptResponse.PacketTypeResponse;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;
import tnt.egts.parser.util.StringFixedBeanNames;

@Service (StringFixedBeanNames.AUTH_FINAL_RESPONSE_DATA_GENERATOR_BEAN)
@Slf4j
public class DoFinalAuthResponseService implements OutcomeIdentFinalCreate {

    @Autowired
    @Qualifier ("pt")
    OutcomeIdentCreate creator;

    @Autowired
    private CRC crc;

    @Override
    public OutcomeIdent createAuthResponse(IncomeDataStorage storage, byte code) throws NumberArrayDataException {
        int headLengthIndexIndex = ByteFixPositions.HEAD_LENGTH_INDEX;

        int headLen=storage.getPackageHeader().length;
        int headLen1=storage.getPackageHeader() [headLengthIndexIndex];

        PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage);

        System.out.println("    PP PT:  "+pt.getResponsePacketID());
        PrepareedResponseData out = PrepareedResponseData.builder()
                .sfrd(pt.getData()).code(code)
                .responseHead(storage.getPackageHeader())
                .build();
        out.prepareAuthData();
System.out.println(" ******  from out T PT:  "+out );
//
//System.out.println();
//System.out.println();
//System.out.println();

        byte[] mainHead=
                ArrayUtils.getFixedLengthSubArray(out.getProperPackageHeader(),
                        0,out.getProperPackageHeader().length-1);
       long crc8 =   crc.calculateHead(out.getResponseHead());
        short crc16 = (short) crc.calculate16(out.getSfrd());

        System.out.println( " ********************   " );
        System.out.println( " ******** code   " +code);
        System.out.println( " CRC8::   "+(byte) crc8);
        System.out.println( " CRC16::   "+crc16);
        System.out.println( " out.getSfrd()::   "+ArrayUtils.arrayPrintToScreen(out.getSfrd()));
        System.out.println( " out.getSfrd() lrn::   "+out.getSfrd().length);
        System.out.println( " CRC16 AS Arr::   "+ArrayUtils.arrayPrintToScreen(
                ArrayUtils.shortToByteArray(crc16)
        ));
        System.out.println( " CRC16::   "+crc16);
        System.out.println( " ********************   " );
        System.out.println( " ********************   " );

        out.getResponseHead()[9] = 0;//tmp
        byte[] crc16Array = ArrayUtils.shortToByteArray(crc16);
        crc16Array = ArrayUtils.inverse(crc16Array);

        byte[] data = out.getData();
       data [headLen-1] = (byte) crc8;
        data = ArrayUtils.joinArrays(data, crc16Array);
        out.setData(data);
        log.info("Response data generate finish: "+out);

System.out.println("-------------");
System.out.println("---------  "+out);
System.out.println("-------------");
        return out;
    }
//
//    private void testCRC8() {
//        System.out.println();
//        System.out.println();
//        System.out.println("TEST CRC8 ");
//        for(byte k=0;k<5; k++){
//            byte[] b=new byte[1]   ;
//            b[0]=k;
//            long ccrrcc=crc.calculate8(  b );
//            String hex=Long.toHexString(ccrrcc);
//            System.out.println(k+":   "+ ccrrcc+"   hex   "+hex+"   as byte: " +
//                               "  "+ (  (byte) ccrrcc )  );
//        }
//        System.out.println();
//        System.out.println();
//        byte[] bb={11,11,35};
//                //{1, 0, 2, 11, 0, 16, 0, 12, 0, 0, 0};
//        long ccrrcc=crc.calculate8(  bb );
//        String hex=Long.toHexString(ccrrcc);
//        System.out.println( "fixed:   "+ ccrrcc+"   hex   "+hex +"   as byte: " +
//                            "  "+ (  (byte) ccrrcc ));
//        System.out.println();
//        System.out.println();
//        System.out.println();
//    }
}
