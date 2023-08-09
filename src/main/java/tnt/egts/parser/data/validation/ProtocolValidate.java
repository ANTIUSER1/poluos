package tnt.egts.parser.data.validation;

public interface ProtocolValidate {

    /**
     * [0] index must be 0x01
     * @param income
     * @return
     */
    boolean validPRV(byte[] income);

    /**
     * [2] index bits 6, 7 must be 00
     * @param income
     * @return
     */
    boolean validPackageType(byte[] income);

    /**
     * [2] index bits 6, 7 must be 00
     * @param income
     * @return
     */
    boolean validPRF(byte[] income);




}
