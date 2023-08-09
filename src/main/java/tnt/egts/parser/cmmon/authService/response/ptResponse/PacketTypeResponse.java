package tnt.egts.parser.cmmon.authService.response.ptResponse;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;
import tnt.egts.parser.cmmon.authService.authInfo.AuthRecordData;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecord;
import tnt.egts.parser.cmmon.authService.response.separate.SeparateRecordService;
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
    public void createData() {
        System.out.println("PT RESPONSE: "+responsePacketID );
        System.out.println("PT RESPONSE: "+responsePacketID );
        System.out.println("PT RESPONSE: "+responsePacketID );
        System.out.println("PT RESPONSE: "+responsePacketID );

      data =  ArrayUtils.shortToByteArray(responsePacketID);
        data = ArrayUtils.inverse(data);
        System.out.println( "   DATA OF PID as array "+ ArrayUtils.arrayPrintToScreen(data));
        data=ArrayUtils.addByteToTail(data, (byte) 0) ;
        data=ArrayUtils.joinArrays( data,
               ArrayUtils.shortToByteArray((short) separateRecord.getData().length) );
//        data = ArrayUtils.shortToByteArray(responsePacketID);
        System.out.println("DDDD");
        System.out.println("--"+separateRecord.getData().length);
        System.out.println("--"+ArrayUtils.arrayPrintToScreen(data));
        System.out.println("--");
        System.out.println("DDDD");

        data = ArrayUtils.addByteToTail(data, processingResult);
        data=ArrayUtils.joinArrays(data, separateRecord.getData());
    }
}
