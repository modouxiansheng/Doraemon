package com.example.springdemo.springdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-04-26 09:45
 **/
@Controller
@Slf4j
@RequestMapping("/get/message")
public class TestGetMessage {

    @GetMapping("")
    @ResponseBody
    public String index(){
        log.info("接收到消息啦!!!!!");
        return "index";
    }

    @PostMapping("notify")
    @ResponseBody
    public String notifyInfo(@RequestBody ReceiveData notifyTransferRes){
        log.info("接收到消息啦!!!!!{}",notifyTransferRes.toString());

        return "haha";
    }

}
