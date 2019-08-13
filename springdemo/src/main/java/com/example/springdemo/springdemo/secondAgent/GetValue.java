package com.example.springdemo.springdemo.secondAgent;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-08-12 20:26
 **/
@Data
@Component
public class GetValue {

    @Value("${test.value}")
    public String testValue;
}
