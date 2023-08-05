package tnt.egts.parser.cmmon.app;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.cmmon.IncomeIdent;

@Data
@Builder
@ToString
public class CommonAPPDATA  implements IncomeIdent {

    /**
     * RL
     */
    private short recordLength;

    /**
     * RN
     */
    private short recordNum;

    /**
     * flag byte
     */
    private byte flags;

    /**
     * SST
     */
    private byte sourceServiceType;

    private AppDataOptions options;

    /**
     * RST
     */
    private byte recipientServiceType;

    /**
     * RD
     */
    private byte[] recordData;


}
