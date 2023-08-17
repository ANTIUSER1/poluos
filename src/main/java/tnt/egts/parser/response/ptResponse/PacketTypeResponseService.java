package tnt.egts.parser.response.ptResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.OutcomeIdentCreate;
import tnt.egts.parser.data.store.IncomeDataStorage;
import tnt.egts.parser.response.separate.SeparateRecord;
import tnt.egts.parser.errors.NumberArrayDataException;
import tnt.egts.parser.util.ArrayUtils;

@Service("pt")
@Slf4j
public class PacketTypeResponseService implements OutcomeIdentCreate {


    @Autowired
    @Qualifier ("separate")
    private OutcomeIdentCreate creator;

    @Override
    public OutcomeIdent create(IncomeDataStorage storage ) throws NumberArrayDataException {
        log.info("Storage Packet Type data start");
        SeparateRecord sr= (SeparateRecord) creator.create(storage);

        PacketTypeResponse out =PacketTypeResponse.builder()
                     .responsePacketID(storage.getPacketIdentifier())
                     .processingResult((byte) 0)
                     .separateRecord(sr)
                .build();
        out.prepareAuthData();


        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYY");

//System.out.println("Db   GGGFFFDDDSS SEPARATE_RESP    "+sr);
//System.out.println("Db   GGGFFFDDDSS SEPARATE_RESP    " );
//System.out.println("Db   GGGFFFDDDSS SEPARATE_RESP    " );
//System.out.println("Db   GGGFFFDDD " );
//System.out.println("Db   GGGFFFDDD " );
//System.out.println("Db   GGGFFFDDD " );
//System.out.println("Db   GGGF   !!!! OUT:::-->>>>OOOOOOOO  "+out );
//System.out.println("Db   GGGFFFDDDSS SEPARATE_RESP    " );
//        System.out.println();
//        System.out.println("SRSRSR DATA:   "+ArrayUtils.arrayPrintToScreen(sr.getData()));
//        System.out.println();
//        System.out.println("GGGGGGGGGGGGGG BBBBBBBBBBBBBB RRRRR");
//        System.out.println("MMMM NNNN BBBB "+ArrayUtils.arrayPrintToScreen(out.getData()));
//        System.out.println();
//        System.out.println("GFFFFFFFFFFGGGGGGGGF");
//        System.out.println("GFFFFFFFFFFGGGGGGGGF");
//        System.out.println("GF FGF   "+out);
//        System.out.println("GFFFFFFFFFFGGGGGGGGF");
//        System.out.println();
//        System.out.println();
        log.info("Storage Packet Type data finish: "+out);
        return out;
    }
}
