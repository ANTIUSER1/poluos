package tnt.egts.parser.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tnt.egts.parser.controllers.receiver.SocketConnector;

@Component
@Slf4j
public class CLIRunner implements CommandLineRunner {


    @Autowired
    private SocketConnector connector;
    @Autowired
    private String socketHost;
    @Autowired
    private int socketPort;




    @Override
    public void run(String... args) throws Exception {
        log.info("Start parser service");
      connector.connect(socketPort);
    }
}
