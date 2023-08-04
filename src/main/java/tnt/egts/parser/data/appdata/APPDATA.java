package tnt.egts.parser.data.appdata;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.data.Incoming;

import java.util.Map;

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

    private byte[] content;

    private byte[] head;

    private short recNum;

    private short recLength;

    private Map<String, Boolean> flags;

    private int oid;

    private int evid;

    private int tm;

    private byte rst;

    private byte[] recordData;


}
