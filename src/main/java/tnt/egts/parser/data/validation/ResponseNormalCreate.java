package tnt.egts.parser.data.validation;


import tnt.egts.parser.data.*;

/**
 * Table A.14
 * Creating responses
 */
public interface ResponseNormalCreate {

    /**
     * for normal response
     * @param income
     * @return
     */
    Outcoming createNormalResponse( byte[] income, byte resultCode);


}
