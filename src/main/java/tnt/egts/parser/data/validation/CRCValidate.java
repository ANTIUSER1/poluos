package tnt.egts.parser.data.validation;

public interface CRCValidate {

    boolean CRC16Correct(byte[] income);
    boolean CRC8Correct(byte[] income);

}
