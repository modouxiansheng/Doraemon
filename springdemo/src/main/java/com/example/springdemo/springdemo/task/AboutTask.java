package com.example.springdemo.springdemo.task;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-01-15 14:55
 **/
@Slf4j
@Configuration
@EnableScheduling
public class AboutTask {

    @Scheduled(cron = "* * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void print(){
        log.info("test");
    }
}
