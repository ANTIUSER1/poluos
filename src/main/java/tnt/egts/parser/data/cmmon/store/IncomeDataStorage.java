package tnt.egts.parser.data.cmmon.store;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;


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

    /**
     * Head Checker Sum (HCM)
     */
    private byte crc8;

    /**
     * Services frame data Checker Sum  (SFRCS)
     */
    private short crc16;

}
