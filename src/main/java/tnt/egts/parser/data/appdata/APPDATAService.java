package tnt.egts.parser.data.appdata;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class APPDATAService {

    private byte[] appDataRD;

    private byte[] packageHead;

}
