package com.example.springdemo.springdemo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: springBootPractice
 * @description: 关于CPU飙高的排查
 * @author: hu_pf
 * @create: 2020-01-17 14:17
 **/
@Controller
@Slf4j
public class CpuTestController {

    private static Map<Integer, List<KeyAndValueObject>> DATA_MAP = new HashMap<>();

    private AtomicInteger num = new AtomicInteger(0);

    static {
        for (int i = 0; i < 10; i++) {
            List<KeyAndValueObject> keyAndValueObjectList = new ArrayList<>();
            for (int j = 0; j < 10000; j++) {
                keyAndValueObjectList.add(new KeyAndValueObject(j,j));
            }
            DATA_MAP.put(i,keyAndValueObjectList);
        }
    }

    @RequestMapping(value = "/test0")
    public String test0(HttpServletRequest request) {
        ThreadLocal localVariable = new ThreadLocal();
        localVariable.set(new Byte[4096*1024]);// 为线程添加变量
        return "success";
    }

    @RequestMapping("/cpu")
    @ResponseBody
    public String cpuTest(){

        getNumFromMap();

        return "ok";
    }

    private Integer getNumFromMap(){
        List<KeyAndValueObject> keyAndValueObjects = DATA_MAP.get(1);

        Random random = new Random();
        int i = random.nextInt(10000);
        for(KeyAndValueObject data : keyAndValueObjects){
            if (data.getKey() == i){
                return data.getKey();
            }
        }
        return 0;
    }
}

@Data
@AllArgsConstructor
class KeyAndValueObject{
    private Integer key;
    private Integer value;

}
