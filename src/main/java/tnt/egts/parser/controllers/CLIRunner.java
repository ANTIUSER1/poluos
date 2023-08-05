package tnt.egts.parser.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tnt.egts.parser.receiver.SocketConnector;
import tnt.egts.parser.util.StringFixedBeanNames;

@Component
@Slf4j
public class CLIRunner implements CommandLineRunner {


    @Autowired
    @Qualifier(StringFixedBeanNames.EGTS_CONNECTOR_BEAN)
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
