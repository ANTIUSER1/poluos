package tnt.egts.parser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * HEADER
 * Container for header byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class HeaderData implements  Incoming{

    private boolean hasOptions;
    private byte[] content;


}
