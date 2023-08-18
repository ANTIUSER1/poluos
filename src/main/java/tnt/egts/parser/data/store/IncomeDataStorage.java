package tnt.egts.parser.data.store;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Builder
@Data
@ToString
public class IncomeDataStorage {

    private byte[] fullPacket;
    /**
     * only PackageHead
     */
    private byte[] packageHeader;

    /**
     * Services frame data (SFRD)
     */
    private byte[] packagSFRD;

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
    private  byte packageType;


    public ServiceType getByTypeID(int id){
        for(ServiceType t:ServiceType.values()){
            if(id==t.getSrvTypeNo())return t;
        }
        return null;
    }
}
