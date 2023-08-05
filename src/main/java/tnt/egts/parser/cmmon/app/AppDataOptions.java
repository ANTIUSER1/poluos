package tnt.egts.parser.cmmon.app;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.IncomeIdent;

@Builder
@Data
@ToString
public class AppDataOptions implements IncomeIdent {

    /**
     * OID
     */
    private int objectID;

    /**
     * EVID
     */
    private int eventID;

    /**
     * TM
     */
    private int time;

}
