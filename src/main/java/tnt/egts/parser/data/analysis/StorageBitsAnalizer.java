package tnt.egts.parser.data.analysis;

public interface StorageBitsAnalizer {

    /**
     * Source Service On Device analysis (SSOD)
     */
    boolean sourceServiceOnDevice(String data);

    /**
     * Recipient Service On Device (RSOD)
     */
    boolean reciplentServiceOnDevice(String data);

    /**
     * Group (GRP)
     */
    boolean inGroup(String data);

    /**
     * Record Processing Priority (RPP)
     */
    ProcessingPriority getRecordProcessingPriority(String data );

    /**
     * Time Field Exists (TMFE)
     */
    boolean timeFieldExists(String data);

    /**
     * Event ID Field Exists (EVFE)
     */
    boolean eventFieldExists(String data);

    /**
     * Object ID Field Exists (OBFE)
     */
    boolean objectFieldExists(String data);



}
