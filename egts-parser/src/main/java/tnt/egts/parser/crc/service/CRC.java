package tnt.egts.parser.crc.service;

public interface CRC {

    long calculate8(byte[] data);
    long calculate16(byte[] data);

}
