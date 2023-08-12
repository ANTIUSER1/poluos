package tnt.egts.parser.util;

import java.util.Map;
import java.util.TreeMap;

/**
 * Table A.14 constants
 * Processing Result CODES
 */
public class ProcessingResultCodeConstants {

    private ProcessingResultCodeConstants() {
    }

    /**
     * data processing finished ok
     */
    public  static final int EGTS_PC_OK = 0;

    /**
     * data processing in progress
     */
    public  static final int EGTS_PC_IN_PROGRESS = 1;
    /**
     * Unsupported protocol
     */
    public  static final int EGTS_PC_UNS_PROTOCOL =  128;
    /**
     * Decoding error
     */
    public  static final int EGTS_PC_DECRYPT_ERROR =   129;

    /**
     *  processing denied
     */
    public  static final int EGTS_PC_PROC_DENIED =   130;

    /**
     * incorrect header format
     */
    public  static final int EGTS_PC_INC_HEADERFORM =   131;

    /**
     * incorrect data format
     */
    public  static final int EGTS_PC_INC_DATAFORM =   132;

    /**
     * unsupported type
     */
    public  static final int EGTS_PC_UNS_TYPE =   133;

    /**
     * incorrect params number
     */
    public  static final int EGTS_PC_NOTEN_PARAMS =   134;

    /**
     * Re-processing attempt
     */
    public  static final int EGTS_PC_DBL_PROC =   135;
    /**
     * source  processing denied
     */
    public  static final int EGTS_PC_SRC_DENIED =   136;


    /**
     * header crc error
     */
    public  static final int EGTS_PC_HEADER_CRCERROR =   137;


    /**
     * data crc error
     */
    public  static final int EGTS_PC_DATACRC_ERRR =   138;


    /**
     * incorrect data length
     */
    public  static final int EGTS_PC_INVDATALEN =   139;

    /**
     * ROUTE NOT FOUND
     */
    public  static final int EGTS_PC_ROUTE_NFOUND =   140;

    /**
     * ROUTE closed
     */
    public  static final int EGTS_PC_ROUTE_CLOSED =   141;

    /**
     * ROUTE denied
     */
    public  static final int EGTS_PC_ROUTE_DENIED =   142;

    /**
     * incorrect address
     */
    public  static final int EGTS_PC_INVADDR =   143;

    /**
     * data-retranslation number expired
     */
    public  static final int EGTS_PC_TTLEXPIRED =   144;

    /**
     * no confirmation
     */
    public  static final int EGTS_PC_NO_ACK =   145;

    /**
     * object not found
     */
    public  static final int EGTS_PC_OBJ_NFOUND =   146;

    /**
     * event not found
     */
    public  static final int EGTS_PC_EVNT_NFOUND =   147;

    /**
     * service not found
     */
    public  static final int EGTS_PC_SRVC_NFOUND =   148;

    /**
     * service not denied
     */
    public  static final int EGTS_PC_SRVC_DENIED =   149;

    /**
     * service unknown
     */
    public  static final int EGTS_PC_SRVC_UNKN =   150;

    /**
     * authorization denied
     */
    public  static final int EGTS_PC_AUTH_DENIED =   151;

    /**
     * object allready exists
     */
    public  static final int EGTS_PC_ALLREADY_EXISTS =   152;

    /**
     * id not found
     */
    public  static final int EGTS_PC_ID_NFOUND =   153;

    /**
     * incorrect date/time
     */
    public  static final int EGTS_PC_INC_DATE_TIME =   154;

    /**
     * io error
     */
    public  static final int EGTS_PC_IO_ERROR =   155;

    /**
     * Insufficient resources
     */
    public  static final int EGTS_PC_NO_RES_AVAIL =   156;

    /**
     * module inner fault
     */
    public  static final int EGTS_PC_MODULE_FAULT =   157;

    /**
     * module power supply fault
     */
    public  static final int EGTS_PC_MODULE_PWR_FLT =   158;

    /**
     * module processor fault
     */
    public  static final int EGTS_PC_MODULE_PROC_FLT =   159;

    /**
     * module software fault
     */
    public  static final int EGTS_PC_MODULE_SW_FLT =   160;

    /**
     * module inner program fault
     */
    public  static final int EGTS_PC_MODULE_FW_FLT =   161;

    /**
     * module io fault
     */
    public  static final int EGTS_PC_MODULE_IO_FLT =   162;

    /**
     * module inner memory fault
     */
    public  static final int EGTS_PC_MODULE_MEM_FLT =   163;

    /**
     * module test fault
     */
    public  static final int EGTS_PC_MODULE_TEST_FLT =   164;

    /**
     * Critical Error
     */
    public  static final int EGTS_PC_CRITICAL_ERROR =   -1;





}
