package com.example.springredis.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeyExpiredEventMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("监听到的信息：{},值是:{}", new String(message.getChannel()), new String(message.getBody()));
    }
}