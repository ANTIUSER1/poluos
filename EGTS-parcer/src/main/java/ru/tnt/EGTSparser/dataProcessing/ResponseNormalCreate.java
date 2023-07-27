package ru.tnt.EGTSparser.dataProcessing;

import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;

/**
 * Table A.14
 * Creating responses
 */
public interface ResponseNormalCreate {

    /**
     * for normal response
     * @param hd
     * @return
     */
    Outcoming createNormalResponce(HeaderData hd);


}
