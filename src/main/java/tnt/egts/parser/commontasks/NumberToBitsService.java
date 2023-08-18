package tnt.egts.parser.commontasks;

import org.springframework.stereotype.Service;

@Service
public class NumberToBitsService implements NumberToBits{

    @Override
    public String bitsFromLong(long data) {
        return String.format("%8s", Long.toBinaryString(data)).replace(' ',
                '0');
    }

    @Override
    public String bitsFromInt(int data) {
        return String.format("%8s", Integer.toBinaryString(data)).replace(' ', '0');
    }

    @Override
    public String bitsFromShort(short data) {
        return String.format("%8s", Integer.toBinaryString(data)).replace(' ', '0');
    }

    @Override
    public String bitsFromByte(byte data) {
        return String.format("%8s", Integer.toBinaryString(data & 0xFF)).replace(' ', '0');
    }
}
