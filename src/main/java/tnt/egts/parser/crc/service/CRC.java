package tnt.egts.parser.crc.service;

/**
 * calculate CRC data
 */
public interface CRC {

    /**
     * returns crc8 for given byte array
     * @param data
     * @return
     */
    long calculate8(byte[] data);

    long calculateHead(byte[] data);

    /**
     * returns crc16 for given byte array
     * @param data
     * @return
     */
    long calculate16(byte[] data);
    long calculateSfrd(byte[] data);


}
