package tnt.egts.parser.data.store;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.data.analysis.ProcessingPriority;

import java.io.Serializable;


@Builder
@Data
@ToString
public class ResponseDataStorage implements Serializable {

    private byte[] fullPacket;

    /**
     * only PackageHead
     */
    private byte[] packageHeader;

    /**
     * Services frame data (SFRD)
     */
    private byte[] packagSFRD;

    // 3-d byte as bit string
    private String flags;

    //OID
    private int objectIdentifier;

    //EVID
    private int eventIdentifier;

    //TM
    private int time;

    private short recNum;

    /**
     * Head Checker Sum (HCM)
     */
    private byte crc8;

    /**
     * Services frame data Checker Sum  (SFRCS)
     */
    private short crc16;

    /**
     * FLD
     */
    private short frameDataLength;

    /**
     * PID
     */
    private short packetIdentifier;

    /**
     * PT
     */
    private byte packageType;

    private byte sst;

    private ServiceType serviceType;


    private boolean inGroup;

    private boolean reciplentServiceOnDevice;

    private boolean sourceServiceOnDevice;

    private ProcessingPriority processingPriority;

    private int lengthToRD;

    private int sstIndex;

    public ServiceType getByTypeID(int id) {
        for (ServiceType t : ServiceType.values()) {
            if (id == t.getSrvTypeNo()) return t;
        }
        return null;
    }
}
