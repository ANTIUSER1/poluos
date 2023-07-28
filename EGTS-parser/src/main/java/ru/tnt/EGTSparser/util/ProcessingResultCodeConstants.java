package ru.tnt.EGTSparser.util;

import java.util.Map;
import java.util.TreeMap;

/**
 * Table A.14 constants
 * Processing Result CODES
 */
public class ProcessingResultCodeConstants {


    /**
     * data processing finished ok
     */
    public  static final byte EGTS_PC_OK = 0;

    /**
     * data processing in progress
     */
    public  static final byte EGTS_PC_IN_PROGRESS = 1;
    /**
     * Unsupported protocol
     */
    public  static final byte EGTS_PC_UNS_PROTOCOL = (byte) 128;
    /**
     * Decoding error
     */
    public  static final byte EGTS_PC_DECRYPT_ERROR = (byte) 129;

    /**
     *  processing denied
     */
    public  static final byte EGTS_PC_PROC_DENIED = (byte) 130;

    /**
     * incorrect header format
     */
    public  static final byte EGTS_PC_INC_HEADERFORM = (byte) 131;

    /**
     * incorrect data format
     */
    public  static final byte EGTS_PC_INC_DATAFORM = (byte) 132;

    /**
     * unsupported type
     */
    public  static final byte EGTS_PC_UNS_TYPE = (byte) 133;

    /**
     * incorrect params number
     */
    public  static final byte EGTS_PC_NOTEN_PARAMS = (byte) 134;

    /**
     * Re-processing attempt
     */
    public  static final byte EGTS_PC_DBL_PROC = (byte) 135;
    /**
     * source  processing denied
     */
    public  static final byte EGTS_PC_SRC_DENIED = (byte) 136;


    /**
     * header crc error
     */
    public  static final byte EGTS_PC_HEADER_CRCERROR = (byte) 137;


    /**
     * data crc error
     */
    public  static final byte EGTS_PC_DATACRC_ERRR = (byte) 138;


    /**
     * incorrect data length
     */
    public  static final byte EGTS_PC_INVDATALEN = (byte) 139;

    /**
     * ROUTE NOT FOUND
     */
    public  static final byte EGTS_PC_ROUTE_NFOUND = (byte) 140;

    /**
     * ROUTE closed
     */
    public  static final byte EGTS_PC_ROUTE_CLOSED = (byte) 141;

    /**
     * ROUTE denied
     */
    public  static final byte EGTS_PC_ROUTE_DENIED = (byte) 142;

    /**
     * incorrect address
     */
    public  static final byte EGTS_PC_INVADDR = (byte) 143;

    /**
     * data-retranslation number expired
     */
    public  static final byte EGTS_PC_TTLEXPIRED = (byte) 144;

    /**
     * no confirmation
     */
    public  static final byte EGTS_PC_NO_ACK = (byte) 145;

    /**
     * object not found
     */
    public  static final byte EGTS_PC_OBJ_NFOUND = (byte) 146;

    /**
     * event not found
     */
    public  static final byte EGTS_PC_EVNT_NFOUND = (byte) 147;

    /**
     * service not found
     */
    public  static final byte EGTS_PC_SRVC_NFOUND = (byte) 148;

    /**
     * service not denied
     */
    public  static final byte EGTS_PC_SRVC_DENIED = (byte) 149;

    /**
     * service unknown
     */
    public  static final byte EGTS_PC_SRVC_UNKN = (byte) 150;

    /**
     * authorization denied
     */
    public  static final byte EGTS_PC_AUTH_DENIED = (byte) 151;

    /**
     * object allready exists
     */
    public  static final byte EGTS_PC_ALLREADY_EXISTS = (byte) 152;

    /**
     * id not found
     */
    public  static final byte EGTS_PC_ID_NFOUND = (byte) 153;

    /**
     * incorrect date/time
     */
    public  static final byte EGTS_PC_INC_DATE_TIME = (byte) 154;

    /**
     * io error
     */
    public  static final byte EGTS_PC_IO_ERROR = (byte) 155;

    /**
     * Insufficient resources
     */
    public  static final byte EGTS_PC_NO_RES_AVAIL = (byte) 156;

    /**
     * module inner fault
     */
    public  static final byte EGTS_PC_MODULE_FAULT = (byte) 157;

    /**
     * module power supply fault
     */
    public  static final byte EGTS_PC_MODULE_PWR_FLT = (byte) 158;

    /**
     * module processor fault
     */
    public  static final byte EGTS_PC_MODULE_PROC_FLT = (byte) 159;

    /**
     * module software fault
     */
    public  static final byte EGTS_PC_MODULE_SW_FLT = (byte) 160;

    /**
     * module inner program fault
     */
    public  static final byte EGTS_PC_MODULE_FW_FLT = (byte) 161;

    /**
     * module io fault
     */
    public  static final byte EGTS_PC_MODULE_IO_FLT = (byte) 162;

    /**
     * module inner memory fault
     */
    public  static final byte EGTS_PC_MODULE_MEM_FLT = (byte) 163;

    /**
     * module test fault
     */
    public  static final byte EGTS_PC_MODULE_TEST_FLT = (byte) 164;





}
