package com.example.springredislettuce.lettuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@SpringBootApplication
public class LettuceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LettuceApplication.class, args);
    }
}
