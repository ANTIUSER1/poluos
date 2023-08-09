package tnt.egts.parser.cmmon.sendBack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.cmmon.OutcomeIdentFinalCreate;
import tnt.egts.parser.cmmon.authService.response.ptResponse.PacketTypeResponse;
import tnt.egts.parser.cmmon.store.IncomeDataStorage;
import tnt.egts.parser.crc.service.CRC;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;

@Service ("readyToSend")
@Slf4j
public class DoResponseService implements OutcomeIdentFinalCreate {

    @Autowired
    @Qualifier ("pt")
    OutcomeIdentCreate creator;

    @Autowired
    private CRC crc;

    @Override
    public OutcomeIdent create(IncomeDataStorage storage, byte code) throws NumberArrayDataException {
        log.info("Response data generate start");
        PacketTypeResponse pt = (PacketTypeResponse) creator.create(storage);

        System.out.println("    PP PT:  "+pt.getResponsePacketID());
        DoResponse out = DoResponse.builder()
                .sfrd(pt.getData()).code(code)
                .responseHead(storage.getPackageHeader())
                .build();
        out.createData();
System.out.println("   from out T PT:  "+out );
System.out.println();
System.out.println();
System.out.println();
        short crc8 = (short) crc.calculate8(out.getProperPackageHeader());
        short crc16 = (short) crc.calculate16(out.getSfrd());
        out.getResponseHead()[9] = 0;//tmp
        byte[] crc16Array = ArrayUtils.shortToByteArray(crc16);
        crc16Array = ArrayUtils.inverse(crc16Array);

        byte[] data = out.getData();
       data [10] = (byte) crc8;
        data = ArrayUtils.joinArrays(data, crc16Array);
        out.setData(data);
        System.out.println();
        System.out.println();
        System.out.println("DATA: "+data[10] +" HEX: " +Long.toHexString(crc8));
        System.out.println(ArrayUtils.arrayPrintToScreen(data));
        System.out.println("CRC8: "+crc8);
        System.out.println();
        System.out.println();

        log.info("Response data generate finish: "+out);


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
