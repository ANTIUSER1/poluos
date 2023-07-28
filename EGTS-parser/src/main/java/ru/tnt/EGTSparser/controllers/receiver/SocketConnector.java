package ru.tnt.EGTSparser.controllers.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class SocketConnector {

    @Autowired
    private ReceiverData receiverData;


    public void connect(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService execService = Executors.newFixedThreadPool(20);
        while (true) {
            log.info("Socket connects to " + port);
            Socket socket = serverSocket.accept();
            receiverData.setSocket(socket);
            execService.submit(receiverData);
        }
    }

}
