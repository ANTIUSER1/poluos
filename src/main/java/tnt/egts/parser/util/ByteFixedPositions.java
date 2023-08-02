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
     * index of input byte array where is the PRF (Preffix) information
     * --bits 6,7
     */
    public static final int PACKAGE_PRF_INDEX = 2;

    /**
     * index of input byte array where is the package-type information
     */
    public static final int PACKAGE_TYPE_INDEX = 9;

    /**
     * index of input byte array where is the package-headerLength information
     */
    public static final int HEAD_LENGTH_INDEX = 3;

    /**
     *  HCS  index   information
     */
    public static final int PACKAGE_HCS_MAX_INDEX = 11;

    public static  int FDL_START_INDEX=5;
    public static  int PID_START_INDEX=7;

public  static int getAPPDATAStart(byte[] data){return getHCSIndex(data)+1;}


    public  static  int getHCSIndex(byte[] data){
    if(data[HEAD_LENGTH_INDEX]==11) return 10;
    return 15;
}
}
