package com.example.springdemo.springdemo.abouyMybatis;

import com.example.springdemo.springdemo.SpringdemoApplicationTests;
import com.example.springdemo.springdemo.selenium.SeleiumService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-06-01 18:26
 **/
public class TestSeleniumConfig extends SpringdemoApplicationTests {

    @Autowired
    private SeleiumService seleiumService;


    @Test
    public void testSele(){
        String url = "https://juejin.im/post/5ed4b62751882543464b14ff";
        seleiumService.pages(url);
    }

    @Test
    public void getMaxComment() throws Exception {

        seleiumService.getCommentMax("https://juejin.im/pin/5ee0325df265da1bac60ab27");
    }


    @Test
    public void commentClick() throws Exception{

//        seleiumService.comment("https://juejin.im/pin/5ee0325df265da1bac60ab27","嬷嬷茶");
//        seleiumService.comment("https://juejin.im/pin/5ee0325df265da1bac60ab27","不学无数的程序员");
//         18696706091
        seleiumService.comment("https://juejin.im/pin/5ee0325df265da1bac60ab27","前端小白X");
    }
}
