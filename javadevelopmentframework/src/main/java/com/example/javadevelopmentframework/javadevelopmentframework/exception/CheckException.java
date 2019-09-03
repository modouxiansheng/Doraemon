package com.example.javadevelopmentframework.javadevelopmentframework.exception;

import com.example.javadevelopmentframework.javadevelopmentframework.common.ResponseInfoEnum;

/**
 * @program: springBootPractice
 * @description: 自定义异常处理
 * @author: hu_pf
 * @create: 2019-09-02 19:29
 **/
public class CheckException extends RuntimeException{

    public CheckException() {
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(ResponseInfoEnum responseInfoEnum,String ...strings) {
        super(String.format(responseInfoEnum.getMessage(),strings));
    }
}
