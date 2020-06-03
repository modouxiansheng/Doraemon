package com.example.springdemo.springdemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-06-03 14:18
 **/
@Service("xPath")
public class ClickByXpath implements ClickInterface{
    @Override
    public void click(String name, WebDriver driver) {
        waitDriver(name,driver);
        WebElement element = driver.findElement(By.xpath(name));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Override
    public void waitDriver(String name, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,10,1);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(name)));
    }
}
