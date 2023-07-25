package ru.tnt.EGTSparser.controllers.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfig {

    @Value("${socket.port}")
    private  int port;

    @Value("${socket.host}")
    private  String host;

    @Bean
    public int socketPort() {
        return port;
    }

    @Bean
    public String socketHost() {
        return host;
    }
}
