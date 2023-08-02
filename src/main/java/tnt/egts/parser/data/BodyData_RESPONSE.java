package tnt.egts.parser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * EGTS_PT_RESPONSE
 *
 * Container for outcoming byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class BodyData_RESPONSE implements  Outcoming{


    private   byte[] headBody;
    private   byte[] responseBody;
    /**
     * (Processing Result)
     */
    private Byte pr;


}
