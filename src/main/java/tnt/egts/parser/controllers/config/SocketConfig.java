package tnt.egts.parser.controllers.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfig {

    @Value("${socket.port}")
    private  int port;
    @Value("${threads}")
    private  int threads;

    @Value("${socket.host}")
    private  String host;

    @Value("${validate}")
    public boolean validatePacket;

    public int threadCount() {
        return threads;
    }

    @Bean
    public int socketPort() {
        return port;
    }

    @Bean
    public String socketHost() {
        return host;
    }

    @Bean
    public boolean isValidatePacket() {
        return validatePacket;
    }

    @Bean
    public  byte code(){return 0;}
}
