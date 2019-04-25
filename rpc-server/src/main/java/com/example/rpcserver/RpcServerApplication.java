package com.example.rpcserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class RpcServerApplication {

    public static void main(String[] args) throws NoSuchMethodException {
        SpringApplication.run(RpcServerApplication.class, args);
    }
}

