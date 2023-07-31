package tnt.egts.parser.crc.generator;

import org.springframework.stereotype.Component;

@Component("crc16")
public class CRC16Calculator {

    long crc= 0xFFFF;
    byte[] data;

    public long value() {
        calculate();
        return crc;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    private  void calculate(){

            for (int j = 0; j < data.length; j++) {
                crc = ((crc >>> 8) | (crc << 8)) & 0xffff;
                crc ^= (data[j] & 0xff);// byte to int, trunc sign
                crc ^= ((crc & 0xff) >> 4);
                crc ^= (crc << 12) & 0xffff;
                crc ^= ((crc & 0xFF) << 5) & 0xffff;
            }/*w ww  .j av a 2  s  .com*/
            crc &= 0xffff;
    }

 }
