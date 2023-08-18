package tnt.egts.parser.commontasks;

public interface NumberToBits {

    String bitsFromLong(long data);
    String bitsFromInt(int data);
    String bitsFromShort(short data);
    String bitsFromByte(byte data);


    // return String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0');
}
