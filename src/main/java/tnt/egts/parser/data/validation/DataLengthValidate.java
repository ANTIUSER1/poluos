package tnt.egts.parser.data.validation;

public interface DataLengthValidate {
   boolean validDataLength(byte[] income);
    boolean validHeaderLength(byte[] income);
    boolean validPackageLength(byte[] income);
}
