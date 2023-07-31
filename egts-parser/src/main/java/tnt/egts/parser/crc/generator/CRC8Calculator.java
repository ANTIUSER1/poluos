package tnt.egts.parser.crc.generator;

import org.springframework.stereotype.Component;

@Component("crc8")
public class CRC8Calculator {
    public static final long poly = 0x31;
    private long crc = 0x00;
    public long value() {
        return (crc & 0xFF);
    }

    public void reset() {
        crc = 0xff;
    }
    public void update(byte[] input) {
        update(input, input.length);
    }

    private void update(byte[] input, final int len) {
        for (int i = 0; i < len; i++) {
            update(input[i]);
        }
    }

    private  void update(final byte b) {
        crc ^= b;
        for (int j = 0; j < 8; j++) {
            if ((crc & 0x80) != 0) {
                crc = ((crc << 1) ^ poly);
            } else {
                crc <<= 1;
            }
        }
    }
    private void update(final int b) {
        update((byte) b);
    }

}
