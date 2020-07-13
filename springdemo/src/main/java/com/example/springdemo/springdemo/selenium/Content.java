package com.example.springdemo.springdemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springBootPractice
 * @description: 内容本身
 * @author: hu_pf
 * @create: 2020-07-13 17:36
 **/
@Service("content")
public class Content implements FeiDian{

    @Autowired
    private SeleiumService seleiumService;

    @Autowired
    @Qualifier("xPath")
    private ClickByXpath clickByXpath;

    @Override
    public Integer invoke(String name, Integer maxNum) {
        clickByXpath.click("//div[@class='like-action action']",SeleiumService.driver);
        maxNum++;
        return maxNum;
    }
}
