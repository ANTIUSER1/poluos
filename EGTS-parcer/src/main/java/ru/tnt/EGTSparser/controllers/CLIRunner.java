package ru.tnt.EGTSparser.controllers;

import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.crc.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.tnt.EGTSparser.controllers.receiver.SocketConnector;
import ru.tnt.EGTSparser.util.StringArrayUtils;

@Service
@Slf4j
public class CLIRunner implements CommandLineRunner {

    @Autowired
    private SocketConnector connector;
    @Autowired
    private  String socketHost;
    @Autowired
    private  int socketPort;

    @Autowired
   private CRC crc;

    @Override
    public void run(String... args) throws Exception {


//test join array=============================================
//        System.out.println("+++");
//        byte[] bFST={1,2,3,4,5 ,6,7,8,9,0};
//        byte[] bSCD={31,32,100};
//        byte[] red= StringArrayUtils.joinArrays(  bFST, bSCD);
//        System.out.println("+  test  bFST\n "+StringArrayUtils.arrayPrintToScreen(bFST));
//        System.out.println("+  test  bSCD \n"+StringArrayUtils.arrayPrintToScreen(bSCD));
//        System.out.println("+  test  red\n "+StringArrayUtils.arrayPrintToScreen(red));
//
//
//        System.out.println("+++");
//test join array=============================================

 connector.connect(socketPort);
    }
}
