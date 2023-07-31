package tnt.egts.parser.parser.tools;

import org.springframework.stereotype.Service;
import tnt.egts.parser.data.BodyData_APPDATA;
import tnt.egts.parser.data.HeaderData;
import tnt.egts.parser.util.ByteFixedPositions;

@Service
public class AdditionalDataCreatorTool {

    public HeaderData additionalCreate(HeaderData hd, byte[] data) {
        hd.getPid()[0] = data[7];
        hd.getPid()[1] = data[8];
        hd.getFdl()[0] = data[5];
        hd.getFdl()[1] = data[6];
        return hd;
    }

    public BodyData_APPDATA additionalCreate(BodyData_APPDATA bda, byte[] data, int headerLength) {
        bda.getRl()[0] = data[headerLength];
        bda.getRl()[1] = data[headerLength + 1];

        bda.getRn()[0] = data[headerLength + 2];
        bda.getRn()[1] = data[headerLength + 3];
        return bda;
    }

    public BodyData_APPDATA tailCreate(BodyData_APPDATA bda, byte[] data, int headerLength1) {
        bda.setSst(data[headerLength1 + 1]);//25
        bda.setRst(data[headerLength1 + 2]);//26
        headerLength1 = data[ByteFixedPositions.HEAD_LENGTH_INDEX] + 18;

        //setting RD
        Byte[] rd = new Byte[data.length - headerLength1];
        for (int k = headerLength1; k < data.length; k++) {
            rd[k - headerLength1] = data[k];
        }
        bda.setRd(rd);
        return bda;
    }

}
