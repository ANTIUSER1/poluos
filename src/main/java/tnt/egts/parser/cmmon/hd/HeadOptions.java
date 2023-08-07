package tnt.egts.parser.cmmon.hd;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.IncomeIdent;

@Builder
@Data
@ToString
public class HeadOptions  implements IncomeIdent  {

    /** PRA */
    private  short peerAddres;

    /** RCA */
    private short recipientAddress;

    /** TTL */
    private byte timeToLive;

    @Override
    public void createData() {

    }
}
