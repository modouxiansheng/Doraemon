package com.example.springdemo.springdemo.selenium;

import com.example.springdemo.springdemo.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-05-15 17:30
 **/
@Service
public class SeleiumService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SeleniumConfig seleniumConfig;

    private static List<String> ALL_PHONES = new ArrayList<>();

    private static WebDriver driver;

    static {
        driver = new FirefoxDriver();
    }

    /**
    * @Description: 所有文章点赞
    * @Param: []
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/6/2
    */
    public void allPages(){
        sleep(1000);
        driver.get("https://juejin.im/user/5b7286a76fb9a009c624342f");
        driver.findElement(By.xpath("/html/body/div/div[2]/main/div[3]/div[1]/div[2]/div/div[1]/div/a[2]/div[1]")).click();
        login("13425190352");
        scroll("//div[contains(@class, 'row abstract-row')]/a[1]","不学无数——SpringBoot入门Ⅰ");
        List<WebElement> noAvtice = driver.findElements(By.xpath("//span[contains(@class, 'count likedCount')]"));
        List<WebElement> active = driver.findElements(By.xpath("//span[contains(@class, 'count likedCount active')]"));
        boolean b = noAvtice.removeAll(active);
        sleep(1000);
        noAvtice.forEach(webElement -> {
            sleep(10000);
            webElement.click();
        });
    }

    /**
    * @Description: 单篇文章点赞
    * @Param: [url]
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/6/2
    */
    public void pages(String url){
        List<String> allPhones = seleniumConfig.getAllPhones();
        String exitPhones = userMapper.selectExitPhone(url);
        List<String> exitPhonesList = new ArrayList<>();
        if (!StringUtils.isEmpty(exitPhones)){
            exitPhonesList = Arrays.asList(exitPhones.split(SeleiumConstants.SPLIT));
        }else {
            exitPhones = StringUtils.EMPTY;
        }
        allPhones.removeAll(exitPhonesList);
        int i = 0;
        for (String s : allPhones) {
            if (i == SeleiumConstants.LIMIT_NUM){
                break;
            }
            sleep(2000);
            driver.get(url);
            login(s);
            try {
                clickByXpath("//*[@class=\"like-btn panel-btn like-adjust with-badge\"]");
                i++;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("点过赞了");
            }
            StringBuilder stringBuilder = new StringBuilder(exitPhones);
            stringBuilder.append(SeleiumConstants.SPLIT+s);
            ThumbsUpRecordDto thumbsUpRecordDto = ThumbsUpRecordDto.builder()
                    .url(url)
                    .phones(stringBuilder.toString())
                    .build();
            if (StringUtils.isEmpty(exitPhones)){
                userMapper.insertIntoThumbsUpRecord(thumbsUpRecordDto);
            }else {
                userMapper.updateIntoThumbsUpRecord(thumbsUpRecordDto);
            }
            exitPhones = stringBuilder.toString();
            exit();
        }
    }


    /**
    * @Description: 评论点赞
    * @Param: [url]
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/6/2
    */
    public void comment(String url) {
        String exitPhones = userMapper.selectExitPhone(url);
        ALL_PHONES = seleniumConfig.getAllPhones();
        List<String> exitPhonesList = new ArrayList<>();
        if (!StringUtils.isEmpty(exitPhones)){
            exitPhonesList = Arrays.asList(exitPhones.split(","));
        }
        Integer maxNum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : ALL_PHONES) {
            if (!exitPhonesList.contains(s)) {
                driver.get(url);
                if (maxNum > 2){
                    break;
                }
                login(s);
                WebDriverWait wait = new WebDriverWait(driver, 10, 1);
//                String num = driver.findElement(By.xpath("//*[@id=\"juejin\"]/div[2]/main/div[3]/div[1]/div/div[5]/div/div[2]/div/span")).getText();
                sleep(1000);
                String num = driver.findElement(By.xpath("//div[contains(@class, 'action-box sticky')]/div[2]")).getText();
                if (num.contains("评论")) {
                    System.out.println(num);
                }
                scroll("//*[@class=\"user-content-box\"]", "//*[@class=\"content-box comment-divider-line\"]", Integer.valueOf(num));
                List<WebElement> elements = driver.findElements(By.xpath("//*[@class=\"content-box comment-divider-line\"]"));
//                List<WebElement> elements = driver.findElements(By.xpath("//*[@class=\"user-content-box\"]"));
                for (WebElement element : elements) {
                    String text = element.findElement(By.xpath(".//*[@class=\"username ellipsis\"]")).getText();
//                    String text = element.findElement(By.xpath(".//*[@class=\"user-popover-box\"]")).getText();
                    if ("不学无数的程序员".equals(text)) {
                        WebElement webElement = getWebElement(element, ".//*[@class=\"like-action action\"]");
                        if (webElement != null) {
                            webElement.click();
                            maxNum++;
                            stringBuilder.append(s + ",");
                            System.out.println(s);
                        } else {
                            System.out.println(s);
                            stringBuilder.append(s + ",");
                        }
                        break;
                    }
                }
//                exit();
                driver.close();
            }
        }
        System.out.println(stringBuilder.toString());
    }

    public WebElement getWebElement(WebElement element,String xpath){
        List<WebElement> elements = element.findElements(By.xpath(xpath));
        if (elements.size() == 0){
            return null;
        }else {
            return elements.get(0);
        }
    }

    public void getCommentMax(String url) throws Exception{
        driver.get(url);
        login("18983773470");
        WebDriverWait wait = new WebDriverWait(driver,10,1);
        sleep(1000);
        String num = driver.findElement(By.xpath("//div[contains(@class, 'action-box sticky')]/div[2]")).getText();
        if (num.contains("评论")){
            System.out.println(num);
        }
        scroll("//*[@class=\"user-content-box\"]","//*[@class=\"content-box comment-divider-line\"]",Integer.valueOf(num));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='like-action action']/span"));
        List<Integer> commentList = new ArrayList<>();

        for (WebElement element : elements) {
            if (!StringUtils.isEmpty(element.getText())){
                commentList.add(Integer.valueOf(element.getText()));
            }
        }

        commentList.stream().sorted().forEach(System.out::println);
//        driver.close();
    }



    /**
    * @Description: 退出登录
    * @Param: []
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/6/2
    */
    public void exit(){
        clickByXpath("//*[@id=\"juejin\"]/div[2]/div/header/div/nav/ul/li[5]/div");
        clickByXpath("//*[@id=\"juejin\"]/div[2]/div/header/div/nav/ul/li[5]/ul/div[4]/li");
        driver.switchTo().alert().accept();
    }

    public void login(String userName){

//        WebDriverWait wait = new WebDriverWait(driver,10,1);
//        WebElement element = driver.findElement(By.className("login"));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        // 点击登录按钮
        clickByClassName("login");
        // 账号
        driver.findElement(By.name("loginPhoneOrEmail")).sendKeys(userName);
        // 密码
        driver.findElement(By.name("loginPassword")).sendKeys("hpy911213");
        // 登录按钮
        clickByXpath("//*[@id=\"juejin\"]/div[1]/div[3]/form/div[2]/button");
        sleep(1000);
    }

    /**
     * @Description: 点击-ClassName
     * @Param: [xpath]
     * @return: void
     * @Author: hu_pf
     * @Date: 2020/6/2
     */
    public void clickByClassName(String name){
        waitDriverByClassName(name);
        WebElement element = driver.findElement(By.className(name));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * @Description: 等待页面元素
     * @Param: [path]
     * @return: void
     * @Author: hu_pf
     * @Date: 2020/6/2
     */
    private void waitDriverByClassName(String name){
        WebDriverWait wait = new WebDriverWait(driver,10,1);
        wait.until(ExpectedConditions.elementToBeClickable(By.className(name)));
    }

    /**
    * @Description: 点击-xpath
    * @Param: [xpath]
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/6/2
    */
    public void clickByXpath(String xpath){
        waitDriverByXpath(xpath);
        WebElement element = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
    * @Description: 等待页面元素
    * @Param: [path]
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/6/2
    */
    private void waitDriverByXpath(String path){
        WebDriverWait wait = new WebDriverWait(driver,10,1);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
    }


    /**
    * @Description: 向下翻滚页面,评论翻滚
    * @Param: [comment, sonComment, stopNum]
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/5/15
    */
    public void scroll(String comment,String sonComment,Integer stopNum){
        Boolean flag = true;
        int i = 1;
        while (flag){
            List<WebElement> elements = driver.findElements(By.xpath(comment));
            List<WebElement> elements2 = driver.findElements(By.xpath(sonComment));
            sleep(500);
            if (stopNum.intValue() == (elements.size()+elements2.size())){
                flag = false;
                i++;
            }
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,150000)");
        }
    }


    /**
    * @Description: 向下翻滚页面
    * @Param: [xPath, stop]
    * @return: void
    * @Author: hu_pf
    * @Date: 2020/5/15
    */
    public void scroll(String xPath,String stop){
        Boolean flag = true;
        int bef = 0;
        int aft = 0;
        while (flag){
            List<WebElement> elements = driver.findElements(By.xpath(xPath));
            bef = elements.size();
            sleep(500);
            if (aft != bef){
                for (WebElement webElement : elements){
                    String text = webElement.getText();
                    if (stop.equals(text)){
                        flag = false;
                    }
                }
                aft = bef;
            }
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,50000)");
        }
    }


    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
