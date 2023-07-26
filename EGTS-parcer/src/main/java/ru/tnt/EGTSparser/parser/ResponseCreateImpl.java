package ru.tnt.EGTSparser.parser;

import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.dataProcessing.ResponseNormalCreate;
import ru.tnt.EGTSparser.data.BodyData_RESPONSE;
import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;
import ru.tnt.EGTSparser.util.ProcessingResultCodeConstants;

@Service
public class ResponseCreateImpl implements ResponseNormalCreate {


    @Override
    public Outcoming createNormalResponce(HeaderData hd) {
        System.out.println("==");
        System.out.println( hd);

        System.out.println("==");

        BodyData_RESPONSE bdr= BodyData_RESPONSE.builder()
                .rpid(hd.getPid())
                .rp(ProcessingResultCodeConstants.EGTS_PC_OK)
                .build();

        return bdr;
    }

    @Override
    public Outcoming createProgressResponse(HeaderData hd) {
        BodyData_RESPONSE bdr= BodyData_RESPONSE.builder()
                .rpid(hd.getPid())
                .rp(ProcessingResultCodeConstants.EGTS_PC_IN_PROGRESS)

                .build();

        return bdr;
    }

    @Override
    public Outcoming createDoubleProcessingResponse(HeaderData hd) {
        BodyData_RESPONSE bdr= BodyData_RESPONSE.builder()
                .rpid(hd.getPid())
                .rp(ProcessingResultCodeConstants.EGTS_PC_DBL_PROC)

                .build();

        return bdr; }

}
