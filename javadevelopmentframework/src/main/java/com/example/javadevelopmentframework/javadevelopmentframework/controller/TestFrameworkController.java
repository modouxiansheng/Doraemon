package com.example.javadevelopmentframework.javadevelopmentframework.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-09-03 20:31
 **/
@RestController
public class TestFrameworkController {



    @RequestMapping("/success/{value}")
    public String success(@PathVariable String value){
        return "Return "+value;
    }

    @RequestMapping("/error/{value}")
    public String error(@PathVariable String value){
        int i = 10/0;
        return "Return "+value;
    }
}
