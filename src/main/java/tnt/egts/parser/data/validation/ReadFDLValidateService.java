package tnt.egts.parser.data.validation;

import org.springframework.stereotype.Service;
import tnt.egts.parser.util.StringArrayUtils;

import java.nio.ByteBuffer;
@Service
public class ReadFDLValidateService implements  ReadFDLValidate{

    @Override
    public boolean readFDL(byte[] income) {
        byte[] fdl= StringArrayUtils.createSubArray(income,5,7);
        ByteBuffer bbf=ByteBuffer.wrap(fdl);
        return bbf.getShort(0)>0;
    }
}
