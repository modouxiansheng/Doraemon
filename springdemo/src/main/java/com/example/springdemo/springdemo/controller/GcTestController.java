package com.example.springdemo.springdemo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class GcTestController {

    private List<Greeting> objListCache = new ArrayList<>(100000);

    @RequestMapping("/greeting")
    public Greeting greeting() {
        Greeting greeting = new Greeting();
        if (objListCache.size() >= 100000) {
            log.info("clean the List!!!!!!!!!!");
            objListCache.clear();
        } else {
            objListCache.add(greeting);
        }
        return greeting;
    }
}

@Data
class Greeting {
    private String message1;
    private String message2;
    private String message3;
    private String message4;
    private String message5;
    private String message6;
    private String message7;
    private String message8;
    private String message9;
    private String message10;
    private String message11;
    private String message12;
    private String message13;
    private String message14;
    private String message15;
    private String message16;
    private String message17;
    private String message18;
    private String message19;
    private String message20;
}