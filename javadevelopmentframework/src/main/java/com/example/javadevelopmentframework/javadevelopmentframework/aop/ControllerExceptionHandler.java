package com.example.javadevelopmentframework.javadevelopmentframework.aop;

import com.example.javadevelopmentframework.javadevelopmentframework.common.ResponseResult;
import com.example.javadevelopmentframework.javadevelopmentframework.exception.CheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: springBootPractice
 * @description: 全局Controller异常统一处理
 * @author: hu_pf
 * @create: 2019-09-02 19:08
 **/
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult<String> defaultErrorHandler(HttpServletRequest request, Exception exception){
        log.error(ControllerLog.getLogPrefix()+"Exception: {}"+exception);
        return handleErrorInfo(exception.getMessage());
    }

    @ExceptionHandler(CheckException.class)
    @ResponseBody
    public ResponseResult<String> checkExceptionHandler(HttpServletRequest request, CheckException exception){
        return handleErrorInfo(exception.getMessage());
    }

    private ResponseResult<String> handleErrorInfo(String message) {
        ResponseResult<String> responseEntity = new ResponseResult<>();
        responseEntity.setMessage(message);
        responseEntity.setCode(ResponseResult.ERROR);
        responseEntity.setData(message);
        ControllerLog.destoryThreadLocal();
        return responseEntity;
    }
}
