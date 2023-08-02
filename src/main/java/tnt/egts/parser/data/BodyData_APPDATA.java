package tnt.egts.parser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * EGTS_PT_APPDATA
 * <p>
 * Container for incoming byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class BodyData_APPDATA implements Incoming {


    private  byte[] content;

}
