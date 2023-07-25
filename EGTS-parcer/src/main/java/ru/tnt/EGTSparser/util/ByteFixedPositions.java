package ru.tnt.EGTSparser.util;

/**
 * positions && types of incoming array constants
 */
public class ByteFixedPositions {

    /**
     * index of input byte array where is the package-type information
     */
    public static final int PACKAGE_TYPE_INDEX = 9;

    /**
     * index of input byte array where is the package-headerLength information
     */
    public static final int PACKAGE_HEAD_LENGTH_INDEX = 3;

    /**
     *  minimal  header length   information
     */
    public static final int PACKAGE_HEAD_MIN_LENGTH = 11;

    /**
     * minimal appdata length information
     */
    public static final int PACKAGE_APPDATA_MIN_LENGTH = 7;

    /**
     * minimal appdata length information
     */
    public static final int PACKAGE_КУЫЗЩТЫУ_MIN_LENGTH = 3;

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


}
