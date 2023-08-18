package tnt.egts.parser.util;

import tnt.egts.parser.errors.NumberArrayDataException;

import java.nio.ByteBuffer;

public class NumberUtils {

    public static long byteArrayToLong(byte[] data) throws NumberArrayDataException {
        if (data.length != 8)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + ArrayUtils.arrayPrintToScreen(data)     +" of "+data.length+" bytes,. " +
                                               "Expected 8");
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getLong(0);
    }

    public static long byteArrayInverseToLong(byte[] data) throws NumberArrayDataException {
        if (data.length != 8)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + ArrayUtils.arrayPrintToScreen(data)    +" of "+data.length+" bytes,. Expected 8");
        data = ArrayUtils.inverse(data);
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getLong(0);
    }

    public static int byteArrayToInt(byte[] data) throws NumberArrayDataException {
        if (data.length != 4)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + ArrayUtils.arrayPrintToScreen(data)
                        +" of "+data.length+" bytes,. Expected 4");
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getInt(0);
    }

    public static int byteArrayInverseToInt(byte[] data) throws NumberArrayDataException {
        if (data.length != 4)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + ArrayUtils.arrayPrintToScreen(data)
            +" of "+data.length+" bytes,. Expected 4");
        data = ArrayUtils.inverse(data);
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getInt(0);
    }

    public static short byteArrayToShort(byte[] data) throws NumberArrayDataException {
        if (data.length != 2)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + ArrayUtils.arrayPrintToScreen(data)  +" of "+data.length+" bytes,. " +
                                               "Expected 2");

        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getShort(0);
    }

    public static short byteArrayInverseToShort(byte[] data) throws NumberArrayDataException {
        if (data.length != 2)
            throw new NumberArrayDataException("Invalid " + "incoming data" + " " + ArrayUtils.arrayPrintToScreen(data)      +" of "+data.length+" bytes,. Expected 2");
        data = ArrayUtils.inverse(data);
        ByteBuffer bbf = ByteBuffer.wrap(data);
        return bbf.getShort(0);
    }

}
