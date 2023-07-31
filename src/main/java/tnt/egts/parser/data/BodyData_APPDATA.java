package tnt.egts.parser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * EGTS_PT_APPDATA
 * <p>
 * Container for incoming byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class BodyData_APPDATA implements Incoming {

    /* EGTS_PT_APPDATA */
    /*  ГОСТ Р 56361-2015 Таблица В.1 */
    /**
     * (Record Length) length 2 , m
     */
    private Byte[] rl;

    /**
     * (Record Number) length 2 , m
     */
    private Byte[] rn;

    /**
     * (Record Flags)
     */
    private Byte rfl;
    /**
     * (Object Identifier) length 4 , opt
     */
    private Byte[] oid = new Byte[4];

    /**
     * (Event Identifier) length 4 , opt
     */
    private Byte[] evid = new Byte[4];

    /**
     * (Time)  length 4 , opt
     */
    private Byte[] tm = new Byte[4];

    /**
     * (Source Service Type)
     */
    private Byte sst;

    /**
     * (Recipient Service Type)
     */
    private Byte rst;

    /**
     * (Record Data)  length 3...65498
     */
    private Byte[] rd;

}
