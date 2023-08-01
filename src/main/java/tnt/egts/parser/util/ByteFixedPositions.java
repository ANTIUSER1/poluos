package tnt.egts.parser.util;

/**
 * positions && types of incoming array constants
 */
public class ByteFixedPositions {

    /**
     * index of input byte array where is the PRV (Protocol Version) information
     */
    public static final int PACKAGE_PRV_INDEX = 0;
    /**
     * index of input byte array  PRV (Protocol Version) value information
     */
    public static final int PACKAGE_PRV_VALUE = 0x01;

    /**
     * index of input byte array where is the PRF (Preffix) information
     * --bits 6,7
     */
    public static final int PACKAGE_PRF_INDEX = 2;
    /**
     * index of input byte array where is the PRF (Preffix) information
     * --bits 6,7
     */
    public static final String PACKAGE_PRF_VALUE="00";

    /**
     * index of input byte array where is the package-type information
     */
    public static final int PACKAGE_TYPE_INDEX = 9;

    /**
     * index of input byte array where is the package-headerLength information
     */
    public static final int HEAD_LENGTH_INDEX = 3;

    /**
     * minimal head length
     */
    public static final int HEAD_MIN_LENGTH = 11;
    /**
     * maximal head length
     */
    public static final int HEAD_MAX_LENGTH = 16;

    /**
     *  HCS  index   information
     */
    public static final int PACKAGE_HCS_MAX_INDEX = 11;

    /**
     * minimal appdata length information
     */
    public static final int PACKAGE_APPDATA_MIN_LENGTH = 7;

    /**
     * minimal appdata record-data length information
     */
    public static final int PACKAGE_APPDATA_RECORD_DATA_MIN_LENGTH = 3;

    /**
     * the response package type
     */
    public static final int TYPE_RESPONSE = 0;

    /**
     * the main data type
     */
    public static final int TYPE_APPDATA = 1;

    /**
     * the main signed data type
     */
    public static final int TYPE_SIGNED_APPDATA = 2;

    public  static  int getHCSIndex(byte[] data){
    if(data[HEAD_LENGTH_INDEX]==11) return 10;
    return 15;
}
}
