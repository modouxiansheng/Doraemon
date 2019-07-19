package com.example.springdemo.springdemo.common;

import com.example.springdemo.springdemo.Exception.NullOrEmptyException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-07-19 13:59
 **/
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullOrEmptyException.class)
    @ResponseBody
    public ResponseEntity<String> nullOrEmptyExceptionHandler(HttpServletRequest request, NullOrEmptyException exception){
        log.info("nullOrEmptyExceptionHandler");
        return handleErrorInfo(request, exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest request, Exception exception){
        log.info("defaultErrorHandler");
        return handleErrorInfo(request, exception.getMessage());
    }

    private ResponseEntity<String> handleErrorInfo(HttpServletRequest request, String message) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>();
        responseEntity.setMessage(message);
        responseEntity.setCode(ResponseEntity.ERROR);
        responseEntity.setData(message);
        responseEntity.setUrl(request.getRequestURL().toString());
        return responseEntity;
    }
}
