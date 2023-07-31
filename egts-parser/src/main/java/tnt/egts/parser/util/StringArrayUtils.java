package tnt.egts.parser.util;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class StringArrayUtils {

    public static <T> String arrayPrintToScreen(T[] data) {
      return Arrays.toString(data);
    }
    public static String arrayPrintToScreen(byte[] data) {
     return    Arrays.toString(data);
    }
    public static String arrayPrintToScreen(long[] data) {
        StringBuffer out=new StringBuffer();
        for (int k = 0; k < data.length; k++) {
            out.append( " v[" + k + "]=" + data[k] + "  ");
        }return out.toString();
    }

    public static    short calcShortFromArray(byte[] data){
       if(data.length==2) {
           ByteBuffer buffer = ByteBuffer.wrap(data);
           return buffer.getShort();
       }else {
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
        for (int k = 0; k < dataFirst.length; k++) out[k] = dataFirst[k];
        for (int k = dataFirst.length; k < out.length; k++) out[k] = dataSecond[k - dataFirst.length];
        return out;
    }

    public static byte[] convertToByteArray(long[] data){
        byte[] out = new byte[data.length];
        for(int k=0;k<data.length;k++)out[k]= (byte) data[k];
        return out;
    }
    public static byte[] shortToByteArray(short N ) {
        return new byte[] { (byte) (N >> 8), (byte) (N & 255) };
    }
    public static byte[] inverse(byte[] data){
        byte[] out = new byte[data.length ];
        for(int k=0;k<data.length;k++){
            out[k]=data[data.length-k-1];
        }
        return out;
    }
    public  static byte[] addByteToTail(byte[] data, byte b){
        byte[] out = new byte[data.length + 1];
        for(int k=0;k<data.length;k++)out[k]=data[k];
        out[out.length-1]=b;
        return out;
    }

    public static byte[] createSubArray(byte[] inData, int from,int to){
     if(from+to>inData.length) to=inData.length;
       if(inData.length==0 || from>=to || from<0 || to<0 ) throw new IllegalArgumentException("array borders error");
        byte[] outData =new byte[to-from];
        for(int k=from;k<to;k++)outData[k-from]=inData[k];
        return outData;
    }

    public static  byte[] rndByte(int length){
        byte[] out=new byte[length];
        for(int k=0;k<length;k++)out[k]= (byte) (1000*Math.random());
    return out;
    }

    public static long[] byteToLong(byte[] data){
        long[] out =new long[data.length];
        for(int k=0;k<data.length;k++){
            out[k]=data[k] & 0xff;
        }
        return out;
    }

    public static    boolean  arraysEquals(byte[] data1, byte[] data2){
        ByteBuffer bbf1=ByteBuffer.wrap(data1);
        ByteBuffer bbf2=ByteBuffer.wrap(data2);
        return bbf1.equals(bbf2);
    }
}
