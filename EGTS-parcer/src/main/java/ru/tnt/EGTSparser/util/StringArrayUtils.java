package ru.tnt.EGTSparser.util;

import java.util.Arrays;

public class StringArrayUtils {

    public static <T> void arrayPrintToScreen(T[] data) {
        for (int k = 0; k < data.length; k++) {
            System.out.print(" v[" + k + "]=" + data[k] + "  ");
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

    public static byte[] joinArrays(byte[] data1, byte[] data2) {
       byte[] outData = Arrays.copyOf(data1, data2.length + data2.length);
        System.arraycopy(data1, 0, outData, data2.length, data2.length);
        return outData;
    }

    public static byte[] createSubArray(byte[] inData, int from,int to){
       if(inData.length==0 || from>=to || from<0 || to<0 || from+to>inData.length) throw new IllegalArgumentException("array borders error");
        byte[] outData =new byte[to-from];
        for(int k=from;k<to;k++)outData[k-from]=inData[k];
        return outData;
    }

    public static  byte[] rndByte(int length){
        byte[] out=new byte[length];
        for(int k=0;k<length;k++)out[k]= (byte) (1000*Math.random());
    return out;
    }


}
