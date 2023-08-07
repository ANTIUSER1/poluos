package tnt.egts.parser.cmmon.authService.response.separate;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.OutcomeIdent;

@Builder
@Data
@ToString
public class SeparateRecordOptions   implements OutcomeIdent {


    /**
     * OID
     */
    private  int objectId;
    /**
     * EVID
     */
    private  int eventId;
    /**
     * TM
     */
    private  int time;

    @Override
    public void createData() {

    }
}
