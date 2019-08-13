package com.example.springdemo.springdemo.common;

import com.example.springdemo.springdemo.secondAgent.GetValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-08-12 20:27
 **/
@Component
public class PrintValue implements CommandLineRunner {

    @Autowired
    private GetValue getValue;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(getValue.testValue);
    }
}
