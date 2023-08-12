package tnt.egts.parser.util;

import tnt.egts.parser.errors.NumberArrayDataException;

import java.nio.ByteBuffer;
import java.util.Arrays;


public class ArrayUtils {

    private ArrayUtils() {
    }

    public static <T> String arrayPrintToScreen(T[] data) {
        return Arrays.toString(data);
    }

    public static String arrayPrintToScreen(byte[] data) {
        String hex=arrayAsHEX(data) ;
        return "Data: \n "+
                Arrays.toString(data)+" \n [" +
               hex+
               "] of length "+data.length;
    }

    public static String arrayAsHEX(byte[] data) {
        StringBuffer out=new StringBuffer(' ');
        for(Byte b: data){
            int n= b & 0xff;
            out.append("0x").append(Integer.toHexString(n)).append(' ');
        }
        return out.toString();
    }

    public static String arrayPrintToScreen(long[] data) {
        StringBuffer out = new StringBuffer();
        for (int k = 0; k < data.length; k++) {
            out.append(" v[" + k + "]=" + data[k] + "  ");
        }
        return out.toString();
    }

    public static short calcShortFromArray(byte[] data) {
        if (data.length == 2) {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            return buffer.getShort();
        } else {
            System.out.println("Data is not short value ");
            throw new IllegalArgumentException("Data is not short value ");
        }
    }

    public static Byte[] convertToObject(byte[] data) {
        Byte[] result = new Byte[data.length];
        for (int k = 0; k < data.length; k++) {
            result[k] = data[k];
        }
        return result;
    }

    public static String byteToBinary(byte value) {
        return String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0');
    }

    public static byte[] joinArrays(byte[] dataFirst, byte[] dataSecond) {
        byte[] out = new byte[dataFirst.length + dataSecond.length];
        System.arraycopy(dataFirst, 0, out, 0, dataFirst.length);
        if (out.length - dataFirst.length >= 0)
            System.arraycopy(dataSecond, 0, out, dataFirst.length, out.length - dataFirst.length);
        return out;
    }

    public static byte[] convertToByteArray(long[] data) {
        byte[] out = new byte[data.length];
        for (int k = 0; k < data.length; k++) out[k] = (byte) data[k];
        return out;
    }

    public static byte[] shortToByteArray(short N) {
        return new byte[]{(byte) (N >> 8), (byte) (N & 255)};
    }

    public static byte[] inverse(byte[] data) {
        byte[] out = new byte[data.length];
        for (int k = 0; k < data.length; k++) {
            out[k] = data[data.length - k - 1];
        }
        return out;
    }

    public static byte[] addByteToTail(byte[] data, byte b) {
        byte[] out = new byte[data.length + 1];
        System.arraycopy(data, 0, out, 0, data.length);
        out[out.length - 1] = b;
        return out;
    }

    public static byte[] addByteToStart(byte[] data, byte b) {
        byte[] out = new byte[data.length + 1];
        // System.arraycopy(data, 1, out, 0, data.length);

        out[0] = b;
        System.arraycopy(data, 0, out, 1, out.length - 1);
        return out;
    }

    public static byte[] getSubArrayFromTo(byte[] inData, int from, int to) {
        if (inData.length == 0 || from >= to || from < 0 || to < 0)
            throw new IllegalArgumentException("Array borders error: Given " + "array: " + arrayPrintToScreen(inData));
        byte[] outData = new byte[to - from];
        System.arraycopy(inData, from, outData, 0, to - from);
        return outData;
    }

    public static byte[] getFixedLengthSubArray(byte[] inData, int from, int length) {
        if (inData.length == 0 || from < 0 || from + length > inData.length - 1)
            throw new IllegalArgumentException("array borders error: Given " + "array: " + arrayPrintToScreen(inData)+ "\n from: "+from
            +" length: "+length+" \n ");
        byte[] outData = new byte[length];
        System.arraycopy(inData, from, outData, 0, from + length - from);
        return outData;
    }

    public static byte[] getSubArrayToEnd(byte[] inData, int from) {
        if (inData.length == 0 || from < 0 || from > inData.length - 1)
            throw new IllegalArgumentException("array borders error: Given " + "array: " + arrayPrintToScreen(inData));
        byte[] outData = new byte[inData.length - from];
        System.arraycopy(inData, from, outData, 0, inData.length - from);
        return outData;
    }


    public static byte[] rndByte(int length) {
        byte[] out = new byte[length];
        for (int k = 0; k < length; k++) out[k] = (byte) (1000 * Math.random());
        return out;
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

    public static long byteArrayToLong(byte[] data) throws NumberArrayDataException {
        if (data.length != 8)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + arrayPrintToScreen(data));
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getLong(0);
    }

    public static long byteArrayInverseToLong(byte[] data) throws NumberArrayDataException {
        if (data.length != 8)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + arrayPrintToScreen(data));
        data = inverse(data);
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getLong(0);
    }

    public static int byteArrayToInt(byte[] data) throws NumberArrayDataException {
        if (data.length != 4)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + arrayPrintToScreen(data));
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getInt(0);
    }

    public static int byteArrayInverseToInt(byte[] data) throws NumberArrayDataException {
        if (data.length != 4)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + arrayPrintToScreen(data));
        data = inverse(data);
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getInt(0);
    }

    public static short byteArrayToShort(byte[] data) throws NumberArrayDataException {
        if (data.length != 2)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + arrayPrintToScreen(data));

        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getShort(0);
    }

    public static short byteArrayInverseToShort(byte[] data) throws NumberArrayDataException {
        if (data.length != 2)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + arrayPrintToScreen(data));

        data = inverse(data);
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getShort(0);
    }

    public static boolean arraysEquals(byte[] data1, byte[] data2) {
        ByteBuffer bbf1 = ByteBuffer.wrap(data1);
        ByteBuffer bbf2 = ByteBuffer.wrap(data2);
        return bbf1.equals(bbf2);
    }
}
