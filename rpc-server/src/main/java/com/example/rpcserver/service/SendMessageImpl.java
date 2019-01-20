package com.example.rpcserver.service;

import org.springframework.stereotype.Service;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf@suixingpay.com
 * @create: 2019-01-20 15:45
 **/
@Service
public class SendMessageImpl implements SendMessage{
    @Override
    public String sendName(String name) {
        return "rpc-server echo: "+name;
    }
}
