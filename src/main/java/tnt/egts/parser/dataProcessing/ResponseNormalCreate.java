package tnt.egts.parser.dataProcessing;


import tnt.egts.parser.data.*;

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
    Outcoming createNormalResponse(HeaderData hd, byte resultCode);


}
