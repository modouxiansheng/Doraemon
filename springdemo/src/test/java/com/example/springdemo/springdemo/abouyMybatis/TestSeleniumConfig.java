package com.example.springdemo.springdemo.abouyMybatis;

import com.example.springdemo.springdemo.SpringdemoApplicationTests;
import com.example.springdemo.springdemo.selenium.Comment;
import com.example.springdemo.springdemo.selenium.Content;
import com.example.springdemo.springdemo.selenium.SeleiumService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-06-01 18:26
 **/
public class TestSeleniumConfig extends SpringdemoApplicationTests {

    @Autowired
    private SeleiumService seleiumService;

    @Autowired
    @Qualifier("comment")
    private Comment comment;

    @Autowired
    @Qualifier("content")
    private Content content;

    @Test
    public void testSele(){
        String url = "https://juejin.im/post/5ed4b62751882543464b14ff";
        seleiumService.pages(url);
    }

    @Test
    public void getMaxComment() throws Exception {

        seleiumService.getCommentMax("https://juejin.im/pin/5ef7eba76fb9a05a216a206e");
    }


    @Test
    public void commentClick() throws Exception{

//        seleiumService.comment("https://juejin.im/pin/5efd3fcef265da1bac60b9bd","沉默小小二",content);
//        seleiumService.comment("https://juejin.im/pin/5efd3fcef265da1bac60b9bd","不学无数的程序员",content);
//         18696706091
        seleiumService.comment("https://juejin.im/pin/5efd3fcef265da1bac60b9bd","树洞roobot",content);
    }

    /**
    * @Description: 内容本身点赞
    * @Param: []
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/7/13
    */
    @Test
    public void contenttClick() throws Exception{

//        seleiumService.comment("https://juejin.im/pin/5ef7eba76fb9a05a216a206e","沉默小小二",comment);
//        seleiumService.comment("https://juejin.im/pin/5ee0325df265da1bac60ab27","不学无数的程序员",comment);
//         18696706091
        seleiumService.comment("https://juejin.im/pin/5ef7eba76fb9a05a216a206e","树洞roobot",comment);
    }
}
