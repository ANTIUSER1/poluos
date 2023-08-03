package tnt.egts.parser.data.appdata;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.data.Incoming;

/**
 * EGTS_PT_APPDATA
 * <p>
 * Container for incoming byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class APPDATA implements Incoming {

    private  byte[] content;

}
