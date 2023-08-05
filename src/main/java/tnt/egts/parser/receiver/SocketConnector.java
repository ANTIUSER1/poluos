package tnt.egts.parser.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tnt.egts.parser.util.StringFixedBeanNames;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service(StringFixedBeanNames.EGTS_CONNECTOR_BEAN)
public class SocketConnector implements SCoonnect {

    @Autowired
    private ReceiverData receiverData;

    @Autowired
    private int threadCount;

    public void connect(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService execService = Executors.newFixedThreadPool(threadCount);
        while (true) {
            log.info("Socket connects to " + port);
            Socket socket = serverSocket.accept();
            receiverData.setSocket(socket);
            execService.submit(receiverData);
        }
    }

}
