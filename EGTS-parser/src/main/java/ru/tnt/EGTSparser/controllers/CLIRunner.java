package ru.tnt.EGTSparser.controllers;

import org.springframework.stereotype.Service;
import ru.tnt.EGTSparser.crc.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.tnt.EGTSparser.controllers.receiver.SocketConnector;
import ru.tnt.EGTSparser.util.StringArrayUtils;

import java.nio.ByteBuffer;

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

           connector.connect(socketPort);
    }

}
