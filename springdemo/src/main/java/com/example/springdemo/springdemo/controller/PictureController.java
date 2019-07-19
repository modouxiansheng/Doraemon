package com.example.springdemo.springdemo.controller;

import com.example.springdemo.springdemo.Exception.NullOrEmptyException;
import com.example.springdemo.springdemo.common.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-07-18 17:14
 **/
@Controller
public class PictureController {

    @RequestMapping("")
    public String index(){
        return "index";
    }


    @RequestMapping("/pic")
    @ResponseBody
    public ResponseEntity<String> pic(MultipartFile [] pictures) throws Exception {
        ResponseEntity<String> responseEntity = new ResponseEntity<>();
        long count = Arrays.asList(pictures).stream().
                map(MultipartFile::getOriginalFilename).
                filter(String::isEmpty).count();
        if (count == pictures.length){
            responseEntity.setCode(ResponseEntity.ERROR);
            throw new NullOrEmptyException("图片不能同时为空");
        }
        responseEntity.setCode(ResponseEntity.OK);
        responseEntity.setMessage("上传成功");
        return responseEntity;
    }
}
