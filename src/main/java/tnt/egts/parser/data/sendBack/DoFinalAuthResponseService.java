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
               PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage);

       // System.out.println("    PP PT:  "+pt.getResponsePacketID());
        PrepareedResponseData out = PrepareedResponseData.builder()
                .sfrd(pt.getData()).code(code)
                .responseHead(storage.getPackageHeader())
                .build();
        out.prepareAuthData();


System.out.println(" ****PT:  " +pt);
System.out.println(" ****PT:  " +pt.getData().length);
System.out.println(" ****PT:  " );
System.out.println(" ****PT:  " );
System.out.println(" **!!!!**PT ### OOOOO-->>>:  "+out );
System.out.println(" ****PT: ******************************* " );
System.out.println(" ****PT:  " );
System.out.println(" ****PT:  " );
System.out.println( " ******** code   " +code);

 modyfySFRD(out, storage);
        log.info("Response data generate finish: "+out);

        System.out.println(" ****PT:  " );
        System.out.println(" ****PT: " +
                           ":::::::::::::::::::::::::::::::::::::::::;" +
                           " " );
        System.out.println(" ****PT:  " );
        System.out.println(" **!!!!**PT ### OOOOO-->>>:  "+out );
        System.out.println(" ****PT:  " );
        System.out.println(" ****PT:  " );
        System.out.println(" ****PT:  " );
        System.out.println(" ****PT:  " );
        System.out.println(" ****PT:  " );

        return out;
    }

    private void modyfySFRD(PrepareedResponseData out,IncomeDataStorage storage) {
        int headLengthIndexIndex = ByteFixPositions.HEAD_LENGTH_INDEX;

        int headLen=storage.getPackageHeader().length;
        int headLen1=storage.getPackageHeader() [headLengthIndexIndex];
        long crc8 =   crc.calculateHead(out.getResponseHead());
        long crc16 =  crc.calculateSfrd(out.getData());
        long crc16SFRD =  crc.calculate16(out.getSfrd());

        byte[] t16= {0x3,0x2,0x4};
        long testCRC=crc.calculate16(t16) ;

        byte[] crc16Array = ArrayUtils.shortToByteArray((short) crc16SFRD);
        crc16Array = ArrayUtils.inverse(crc16Array);

        byte[] data = out.getData();
        data [headLen-1] = (byte) crc8;
        data = ArrayUtils.joinArrays(data, crc16Array);
        out.setData(data);


        System.out.println( " *********** testCRC  "+testCRC +" "+Long.toHexString(testCRC) );
        System.out.println( " ********************  t16  "
                            +ArrayUtils.arrayPrintToScreen(t16));
        System.out.println( " ********************   " );

        System.out.println( " CRC8::   "+(byte) crc8);
        System.out.println( " CRC16::   "+crc16+ " HEX: "+Long.toHexString(crc16));
        System.out.println( " CRC16SFRD::   "+crc16SFRD+ " HEX: "+Long.toHexString(crc16SFRD));
        System.out.println( " out.getSfrd()::   "+ArrayUtils.arrayPrintToScreen(out.getSfrd()));
        System.out.println( " out.getData()::   "+ArrayUtils.arrayPrintToScreen(out.getData()));
        System.out.println( " out.getSfrd() lrn::   "+out.getSfrd().length);
        System.out.println( " out.getData() lrn::   "+out.getData().length);
        System.out.println( " CRC16 AS Arr::   "+ArrayUtils.arrayPrintToScreen(
                ArrayUtils.shortToByteArray((short) crc16)
        ));
        System.out.println( " CRC16SFRD AS Arr::   "+ArrayUtils.arrayPrintToScreen(
                ArrayUtils.shortToByteArray((short) crc16SFRD)
        ));

        System.out.println( " ********************   " );
        System.out.println( " ********************   " );


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
