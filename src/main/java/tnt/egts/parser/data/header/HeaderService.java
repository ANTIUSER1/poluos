package tnt.egts.parser.data.header;


import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class HeaderService {
private byte[] headerContent;
    private byte[] packageHead;
}
