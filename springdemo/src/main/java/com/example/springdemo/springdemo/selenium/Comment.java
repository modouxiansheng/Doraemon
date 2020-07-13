package com.example.springdemo.springdemo.selenium;

import com.example.springdemo.springdemo.mapper.UserMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springBootPractice
 * @description: 评论相关
 * @author: hu_pf
 * @create: 2020-07-10 16:51
 **/
@Service("comment")
public class Comment implements FeiDian{

    @Autowired
    private SeleiumService seleiumService;

    @Override
    public Integer invoke(String name,Integer maxNum) {
        WebDriverWait wait = new WebDriverWait(SeleiumService.driver, 10, 1);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'action-box sticky')]/div[2]")));
        seleiumService.sleep(1000);
        String num = SeleiumService.driver.findElement(By.xpath("//div[contains(@class, 'action-box sticky')]/div[2]")).getText();
        seleiumService.scroll("//*[@class=\"user-content-box\"]", "//*[@class=\"content-box comment-divider-line\"]", Integer.valueOf(num));
        List<WebElement> elements = SeleiumService.driver.findElements(By.xpath("//*[@class=\"content-box comment-divider-line\"]"));
        for (WebElement element : elements) {
            String text = element.findElement(By.xpath(".//*[@class=\"username ellipsis\"]")).getText();
            if (name.equals(text)) {
                WebElement webElement = seleiumService.getWebElement(element, ".//*[@class=\"like-action action\"]");
                if (webElement != null) {
                    webElement.click();
                    maxNum++;
                }
                break;
            }
        }
        return maxNum;
    }
}
