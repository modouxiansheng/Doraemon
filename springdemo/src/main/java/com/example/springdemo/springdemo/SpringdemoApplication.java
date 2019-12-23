package com.example.springdemo.springdemo;

import com.example.springdemo.springdemo.dataSource.SycAndTrans;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.example.springdemo.springdemo.mapper")
public class SpringdemoApplication implements CommandLineRunner {

    @Autowired
    private SycAndTrans sycAndTrans;


    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            final int index = i;
            log.info("线程{}开始",index);
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    sycAndTrans.testAdd();
                }
                log.info("线程{}结束",index);
            }).start();
        }
    }
}
