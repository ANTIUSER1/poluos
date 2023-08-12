package tnt.egts.parser.util;

import tnt.egts.parser.errors.NumberArrayDataException;

public class ByteFixValues {

    public static final short CRC16_VALUE_SIZE = 2;

    private ByteFixValues() {
    }

    /**
     * index of input byte array where is the PRF (Preffix) information
     * --bits 6,7
     */
    public static final String PACKAGE_PRF_VALUE="00";


    /**
     * index of input byte array  PRV (Protocol Version) value information
     */
    public static final int PACKAGE_PRV_VALUE = 0x01;

    /**
     * minimal head length
     */
    public static final int HEAD_MIN_LENGTH = 11;
    /**
     * maximal head length
     */
    public static final int HEAD_MAX_LENGTH = 16;

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
    /**
     * Preffix length
     */
    public static final int PRF_LENGTH = 2;

    /**
     * minimal appdata length information
     */
    public static final int PACKAGE_APPDATA_MIN_LENGTH = 7;

    /**
     * minimal appdata record-data length information
     */
    public static final int PACKAGE_APPDATA_RECORD_DATA_MIN_LENGTH = 3;


    public static byte[] getFDLByteValue(byte[] income, int startAPPDATA) {
        byte[] fdl = ArrayUtils.getSubArrayFromTo(income,
                ByteFixPositions.FDL_START_INDEX,
                ByteFixPositions.FDL_START_INDEX + 2);
        return ArrayUtils.inverse(fdl);
    }
    public static short getFDLNumberValue(byte[] byteValue) throws NumberArrayDataException {
        return ArrayUtils.byteArrayToShort(byteValue);
    }

}
