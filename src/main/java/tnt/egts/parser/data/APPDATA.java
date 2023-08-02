package tnt.egts.parser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class APPDATA implements Incoming {

    private  byte[] content;
}
