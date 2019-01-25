package com.example.rpcclient;

import com.example.rpcclient.client.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@RestController
public class RpcClientApplication {

    @Autowired
    private SendMessage sendMessage;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RpcClientApplication.class, args);

    }

    @RequestMapping("/hello")
    public String getName() {
        return sendMessage.sendName("hh");
    }
}

