package com.example.springredis.redis;

import com.example.springredis.hotkey.HotKey;
import com.example.springredis.hotkey.HotKeyAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class KeyExpiredEventMessageListener implements MessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getChannel());
        key = key.substring(key.indexOf(":")+1);
        String action = new String(message.getBody());
        if (HotKey.containKey(key)){
            String value = redisTemplate.opsForValue().get(key)+"";
            switch (action){
                case "set":
                    log.info("热点Key:{} 修改",key);
                    HotKeyAction.UPDATE.action(key,value);
                    break;
                case "expired":
                    log.info("热点Key:{} 到期删除",key);
                    HotKeyAction.REMOVE.action(key,null);
                    break;
                case "del":
                    log.info("热点Key:{} 删除",key);
                    HotKeyAction.REMOVE.action(key,null);
                    break;
            }
        }
        log.info("监听到的信息：{},值是:{}", new String(message.getChannel()), new String(message.getBody()));
    }
}