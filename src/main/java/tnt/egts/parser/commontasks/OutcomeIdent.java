package tnt.egts.parser.commontasks;

/**
 * preparing data for responses
 */
public interface OutcomeIdent {

    /**
     * preparing data for auth-response
     */
    void prepareAuthData();

    byte[] getData();
}
