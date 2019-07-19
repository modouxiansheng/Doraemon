package com.example.springdemo.springdemo.Exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-07-19 14:12
 **/
public class NullOrEmptyException extends Exception{

    @Getter
    @Setter
    protected String message;

    public NullOrEmptyException(String message){
        this.message = message;
    }
}
