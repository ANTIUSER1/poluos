package tnt.egts.parser.data.cmmon.hd;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class HeadOptions {

    /** PRA */
    private  short peerAddres;

    /** RCA */
    private short recipientAddress;

    /** TTL */
    private byte timeToLive;
}
