package ru.tnt.EGTSparser.parser;

import org.springframework.stereotype.Component;
import ru.tnt.EGTSparser.dataProcessing.NormalProcessingCreate;
import ru.tnt.EGTSparser.data.BodyData_RESPONSE;
import ru.tnt.EGTSparser.data.Outcoming;
import ru.tnt.EGTSparser.util.ProcessingResultCodeConstants;

@Component
public class NormalProcessingImpl implements NormalProcessingCreate {
    @Override
    public Outcoming prepareData(byte[] data) {
       Byte[] pid= new Byte[2];
        pid[0]=data[8];
        pid[1]=data[9];
        BodyData_RESPONSE brs=BodyData_RESPONSE.builder()
                .pr(ProcessingResultCodeConstants.EGTS_PC_INC_HEADERFORM)
                //.rpid(pid)
                .build();
        return brs;
    }
}
