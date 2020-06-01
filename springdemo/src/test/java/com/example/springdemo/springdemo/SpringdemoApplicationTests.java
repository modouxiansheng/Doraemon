package com.example.springdemo.springdemo;

import com.example.springdemo.springdemo.dataSource.SycAndTrans;
import com.example.springdemo.springdemo.mapper.UserMapper;
import com.example.springdemo.springdemo.selenium.LoginInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringdemoApplicationTests {

    @Autowired
    private SycAndTrans sycAndTrans;

    @Autowired
    private UserMapper userMapper;

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

    @Test
    public void insertPhone(){
        String phones = "16532795712,18866674203,16532795624,16532795430,15263819407,16530800938,17112460105,17195777689,17131948032,18866478704,18866478849,16530801176,16530801181,18866478504,17771934420,18866674206,16530801186,18866670045,18866674189,18866478714,18866478544,15263819432,18866478974,18866674223,18866674208,15239117618,16733208024,16733208054,16733207994,16733208048,16733208049,16733207974,18983152614,15523329573,13579973017,18696716103,13425190352,13410956869,13579880464,13565971628,15383778433,19864871605,16539342293,15394685267,17044687374,16516742571,18094301653,18094338185,15351504962,15371219271,15312892027,18014673702,14705191967,18052919357,18962054613,18021477306,17751553287,18851928646,18112448701,18021470587,18052915712,16501246846,18066140761,15345283685,13049895383,15371108730,18021860273,17352384189,18112445890,13327982373,18020451171,15371125693,18051266701,19850412236,13148512986,18961920713,13671348080,17714615837,13382479517,17347698874,15340516309,15334554701,17754921863,17754927164,17347914763,17723604584,17784080389,17723266584,17702309146,15310160642,17725161742,18914617893,17347894174,18983773470,17358455429,15340517494,18696706091,18584517813,15696386371,13042348615";

        List<String> strings = Arrays.asList(phones.split(","));

        strings.forEach(s -> {
            userMapper.insertIntoLoginInfo(LoginInfoDto.builder().loginName(s).password("hpy911213").build());
        });
    }
}
