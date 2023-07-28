package ru.tnt.EGTSparser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * HEADER
 * Container for incoming byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class HeaderData implements  Incoming{

    private boolean hasOptions;
/* ГОСТ Р 56361-2015 Таблица А.3 */
    /**
     * (Protocol Version)
     */
    private Byte prv;

    /**
     * (Security Key ID)
     */
    private Byte skid;

    /**
     * flags
     */
    private Byte prf;

    /**
     * (Header Length)
     */
    private Byte hl;

    /**
     * (Header Encoding)
     */
    private Byte he;

    /**
     * (Frame Data Length)
     */
    private Byte[] fdl;

    /**
     * (Packet Identifier)
     */
    private Byte[] pid;

    /**
     * (Packet Type)
     */
    private Byte pt;

    /**
     * (Peer Address) length 2 opt
     */
    private Byte[] pra;

    /**
     * (Recipient Address) length 2 opt
     */
    private Byte[] rca;

    /**
     * (Time To Live) length 2 opt
     */
    private Byte ttl;

    /**
     * (Header Check Sum)
     */
    private Byte hcs;


}
