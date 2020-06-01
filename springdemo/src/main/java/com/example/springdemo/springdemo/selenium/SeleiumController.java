package com.example.springdemo.springdemo.selenium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-06-01 18:50
 **/
@Controller
public class SeleiumController {

    @Autowired
    private SeleiumService seleiumService;


    @RequestMapping("/sel")
    public void testSele(){
        String url = "https://juejin.im/post/5ed4b62751882543464b14ff";
        seleiumService.comment(url);
    }
}
