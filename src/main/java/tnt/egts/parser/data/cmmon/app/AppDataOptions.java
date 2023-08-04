package tnt.egts.parser.data.cmmon.app;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class AppDataOptions {

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
