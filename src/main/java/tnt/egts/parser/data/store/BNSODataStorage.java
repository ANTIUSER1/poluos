package tnt.egts.parser.data.store;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Data
@ToString
public class BNSODataStorage implements Serializable {

    //  fields take up from  IncomeDataStorage  class
    /**
     * only PackageHead
     */
    private byte[] packageHeader;

    /**
     * Services frame data (SFRD)
     */
    private byte[] packagSFRD;


}
