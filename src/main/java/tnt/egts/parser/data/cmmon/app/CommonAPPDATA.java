package tnt.egts.parser.data.cmmon.app;


import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CommonAPPDATA {

    /**
     * RL
     */
    private short recordLength;

    /**
     * RN
     */
    private  short recordNum;

    /**
     * flag byte
     */
    private  byte flags;

    /**
     * SST
     */
    private  byte sourceServiceType;

private AppDataOptions options;

    /**
     * RST
     */
    private  byte recipientServiceType;

    /**
     * RD
     */
    private byte[]  recordData;


public  void init(){
    System.out.println("MMMMMMMMMMMMMMM");
}
}
