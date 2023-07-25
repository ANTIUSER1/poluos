package ru.tnt.EGTSparser.dataProcessing;

import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;

/**
 * Table A.14
 * Creating responses
 */
public interface ResponseNormalCreate {

    Outcoming createNormalResponce(HeaderData hd);
    Outcoming createProgressResponse(HeaderData hd);
    Outcoming createDoubleProcessingResponse(HeaderData hd);
}
