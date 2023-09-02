package tnt.egts.parser.response;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class ResponseServiceAbstract {


    protected  void sendData(Socket socket, byte[] data){
        OutputStream output = null;
        try {
            output = socket.getOutputStream();
            output.write(data);
            log.info("Sending back TELEDATA-response to BNSO finish. ");
        } catch (IOException e) {
            log.error("Error while response to  attempt");
            e.printStackTrace();
        }
    }
}
