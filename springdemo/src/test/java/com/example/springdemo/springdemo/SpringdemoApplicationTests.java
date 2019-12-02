package com.example.springdemo.springdemo;

import com.example.springdemo.springdemo.dataSource.SycAndTrans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringdemoApplicationTests {

    @Autowired
    private SycAndTrans sycAndTrans;

    @Test
    public void contextLoads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        for (int i = 0; i < 2; i++) {
           new Thread(()->{
               try {
                   latch.await();
                   for (int j = 0; j < 10000; j++) {
                       sycAndTrans.testAdd();
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }).start();
        }
    }

}
