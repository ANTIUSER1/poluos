package tnt.egts.parser.cmmon.hd;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class Head {

    /**  PV     */
    private byte protocolVersion;
    /** SKID */
    private byte securityKeyID;

    private byte flag;

    /**  HL */
    private byte headLength;

    /** HE */
    private byte headEncoding;

    /** FDL */
    private short frameDataLength;
    /** PID */
    private short packetIdentifier;

    /** options */
    private HeadOptions headOptions;

    /** PT */
    private byte packetType;
    /** HCS */
    private byte crc8;





}
