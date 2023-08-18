package tnt.egts.parser.util;

import tnt.egts.parser.errors.NumberArrayDataException;

import java.nio.ByteBuffer;
import java.util.Arrays;


public class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * output array to screen
     * @param data
     * @return
     */
    public static String arrayPrintToScreen(byte[] data) {
        String hex=arrayAsHEX(data) ;
        return "Data: \n "+
                Arrays.toString(data)+" \n [" +
               hex+
               "] of length "+data.length;
    }

    /**
     * byte exposing in hex type
     * @param data
     * @return
     */
    public static String arrayAsHEX(byte[] data) {
        StringBuffer out=new StringBuffer(' ');
        for(Byte b: data){
            int n= b & 0xff;
            out.append("0x").append(Integer.toHexString(n)).append(' ');
        }
        return out.toString();
    }


    /**
     * join two arrays
     * @param dataFirst
     * @param dataSecond
     * @return
     */
    public static byte[] joinArrays(byte[] dataFirst, byte[] dataSecond) {
        byte[] out = new byte[dataFirst.length + dataSecond.length];
        System.arraycopy(dataFirst, 0, out, 0, dataFirst.length);
        if (out.length - dataFirst.length >= 0)
            System.arraycopy(dataSecond, 0, out, dataFirst.length, out.length - dataFirst.length);
        return out;
    }


    public static byte[] shortToByteArray(short N) {
        return new byte[]{(byte) (N >> 8), (byte) (N & 255)};
    }

    /**
     * inverse array
     * @param data
     * @return
     */
    public static byte[] inverse(byte[] data) {
        byte[] out = new byte[data.length];
        for (int k = 0; k < data.length; k++) {
            out[k] = data[data.length - k - 1];
        }
        return out;
    }

    /**
     * add one bye to given array at tail
     * @param data
     * @param b
     * @return
     */
    public static byte[] addByteToTail(byte[] data, byte b) {
        byte[] out = new byte[data.length + 1];
        System.arraycopy(data, 0, out, 0, data.length);
        out[out.length - 1] = b;
        return out;
    }

    /**
     * add one bye to given array at start
     * @param data
     * @param b
     * @return
     */
    public static byte[] addByteToStart(byte[] data, byte b) {
        byte[] out = new byte[data.length + 1];
        // System.arraycopy(data, 1, out, 0, data.length);

        out[0] = b;
        System.arraycopy(data, 0, out, 1, out.length - 1);
        return out;
    }

    /**
     * slice subarray  from start pos to finish pos
     * @param inData
     * @param from
     * @param to
     * @return
     */
    public static byte[] getSubArrayFromTo(byte[] inData, int from, int to) {
        if (inData.length == 0 || from >= to || from < 0 || to < 0)
            throw new IllegalArgumentException("Array borders error: Given " + "array: " + arrayPrintToScreen(inData));
        byte[] outData = new byte[to - from];
        System.arraycopy(inData, from, outData, 0, to - from);
        return outData;
    }

    /**
     * slice subarray of given length from start pos
     * @param inData
     * @param from
     * @param length
     * @return
     */
    public static byte[] getFixedLengthSubArray(byte[] inData, int from, int length) {
        if (inData.length == 0 || from < 0 || from + length > inData.length - 1)
            throw new IllegalArgumentException("array borders error: Given " + "array: " + arrayPrintToScreen(inData)+ "\n from: "+from
            +" length: "+length+" \n ");
        byte[] outData = new byte[length];
        System.arraycopy(inData, from, outData, 0, from + length - from);
        return outData;
    }

    /**
     * get tail of given array from given pos
     * @param inData
     * @param from
     * @return
     */
    public static byte[] getSubArrayToEnd(byte[] inData, int from) {
        if (inData.length == 0 || from < 0 || from > inData.length - 1)
            throw new IllegalArgumentException("array borders error: Given " + "array: " + arrayPrintToScreen(inData));
        byte[] outData = new byte[inData.length - from];
        System.arraycopy(inData, from, outData, 0, inData.length - from);
        return outData;
    }




    public static long[] byteArrayToLongArray(byte[] data) {
        if (data.length != 8)
            throw new IllegalArgumentException("array borders error: Given " + "array: " + arrayPrintToScreen(data));
        long[] out = new long[data.length];
        for (int k = 0; k < data.length; k++) {
            out[k] = data[k] & 0xff;
        }
        return out;
    }

    /**
     * test when arrays has equals data
     * @param data1
     * @param data2
     * @return true if  arrays has equals data
     */
    public static boolean arraysEquals(byte[] data1, byte[] data2) {
        ByteBuffer bbf1 = ByteBuffer.wrap(data1);
        ByteBuffer bbf2 = ByteBuffer.wrap(data2);
        return bbf1.equals(bbf2);
    }
}
