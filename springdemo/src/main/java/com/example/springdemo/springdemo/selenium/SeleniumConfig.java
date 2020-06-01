package com.example.springdemo.springdemo.selenium;

import com.beust.jcommander.internal.Lists;
import com.example.springdemo.springdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-05-15 18:02
 **/
@Component
public class SeleniumConfig implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    private List<String> allPhones = Lists.newArrayList();

    @Override
    public void run(String... args) throws Exception {

        List<String> strings = userMapper.selectAllPhones();

        allPhones = strings;
    }

    public List<String> getAllPhones(){
        return allPhones;
    }
}
