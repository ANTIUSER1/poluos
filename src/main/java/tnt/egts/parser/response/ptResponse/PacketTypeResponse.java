package tnt.egts.parser.response.ptResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.response.separate.SeparateRecord;
import tnt.egts.parser.util.ArrayUtils;

@Builder
@Data
@ToString
public class PacketTypeResponse implements OutcomeIdent {

    /**
     * RPID
     */
    private short responsePacketID;

    /**
     * pr
     */
    private byte processingResult;

    /**
     * additional separate record
     */
    private SeparateRecord separateRecord;

    private byte[] data;

    @Override
    public void prepareAuthData() {
        //   RPID
      data =  ArrayUtils.shortToByteArray(responsePacketID);
      //RPID  continue -- add
        data = ArrayUtils.inverse(data);

        // PR   === EGTS_PC_OK
       data=ArrayUtils.addByteToTail(data, (byte) 0) ;

        //  SEPARATE_RECORD
          data=ArrayUtils.joinArrays( data,separateRecord.getData());


//
//
//        data=ArrayUtils.joinArrays( data,
//               ArrayUtils.shortToByteArray((short) separateRecord.getData().length) );


        System.out.println( " *******  DATA OF PID as  array "+ ArrayUtils.arrayPrintToScreen(data));
        System.out.println( " *****VVVVVVVVVVV\nGGFF\n\n "+ ArrayUtils.arrayPrintToScreen(data));
        System.out.println( " *****ZZZZZZZ\nGGFF\n\n "+ ArrayUtils.arrayPrintToScreen(data));
        System.out.println("DDDD");
        System.out.println("- -"+separateRecord.getData().length);
        System.out.println("-AAAAA separateRecord.getData()AAAAA  -"+
                           ArrayUtils.arrayPrintToScreen(separateRecord.getData()));
        System.out.println("--"+ArrayUtils.arrayPrintToScreen(data));
        System.out.println("--");
        System.out.println("DDDD");

//        data = ArrayUtils.addByteToTail(data, processingResult);
//        data=ArrayUtils.joinArrays(data, separateRecord.getData());
    }
}
