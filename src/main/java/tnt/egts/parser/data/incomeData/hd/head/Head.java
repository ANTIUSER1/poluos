package tnt.egts.parser.data.incomeData.hd.head;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import tnt.egts.parser.commontasks.IncomeIdent;

@Builder
@Data
@ToString
public class Head implements IncomeIdent  {

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

    private byte[] data;

    /** options */
    private HeadOptions headOptions;

    /** PT */
    private byte packetType;
    /** HCS */
    private byte crc8;


    @Override
    public void createData() {

    }
}
