package ru.tnt.EGTSparser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
/**
 * EGTS_PT_RESPONSE
 *
 * Container for incoming byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class BodyData_RESPONSE implements  Outcoming{

    private HeaderData header;

    private   byte[] headBody;
    private   byte[] responseBody;
    /**
     * (Processing Result)
     */
    private Byte pr;


}
