package ru.tnt.crc.make.CRC.controllers;

import ru.tnt.EGTSparser.crc.service.CRC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CLIR {
    @Autowired
    private CRC crc;


//    @Override
    public void run(String... args) throws Exception {


        long[] dat=new long[]{
            //    0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39
              00, 0x12 , 0x15
        };

        long res= crc.calculate8(dat);
        System.out.println(   " [ 8 ] uuu  "+Long.toHexString(res)  );

        res= crc.calculate16(dat);
        System.out.println(   "       [ 16 ] uuu  "+Long.toHexString(res)  );

for(Long b :dat){
    dat=new long[]{b};
    res= crc.calculate8(dat);
    System.out.println(   b+"  [ 8 ] "+Long.toHexString(res) +"   "+res );
    res= crc.calculate16(dat);
    System.out.println(   b+"        [ 16 ] "+Long.toHexString(res) +"   "+res );
}



    }
}
