package tnt.egts.parser.crc.generator;

import org.springframework.stereotype.Component;
import tnt.egts.parser.util.ArrayUtils;

@Component("crc16")
public class CRC16Calculator {
    public     long crc16calc( byte[] bytes) {
            long crc = 0xFFFF;

            for (int j = 0; j < bytes.length; j++) {
                crc = ((crc >>> 8) | (crc << 8)) & 0xffff;
                crc ^= (bytes[j] & 0xff);// byte to int, trunc sign
                crc ^= ((crc & 0xff) >> 4);
                crc ^= (crc << 12) & 0xffff;
                crc ^= ((crc & 0xFF) << 5) & 0xffff;
            }/*w ww  .j av a 2  s  .com*/
            crc &= 0xffff;
            System.out.println(crc+ "HEX: "+Long.toHexString(crc));
            return crc;

        }
 }
