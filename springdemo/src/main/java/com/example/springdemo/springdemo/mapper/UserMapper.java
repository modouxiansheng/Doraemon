package com.example.springdemo.springdemo.mapper;

import com.example.springdemo.springdemo.domain.User;
import com.example.springdemo.springdemo.selenium.LoginInfoDto;
import com.example.springdemo.springdemo.selenium.ThumbsUpRecordDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-12-22 22:52
 **/
public interface UserMapper {

    void addUser(Map<String,Object> map);

    List<User> queryAllUser();

    List<Map<String,Integer>> queryAllUserMap();

    void insertIntoLoginInfo(LoginInfoDto loginInfoDto);

    void insertIntoThumbsUpRecord(ThumbsUpRecordDto thumbsUpRecordDto);
    void updateIntoThumbsUpRecord(ThumbsUpRecordDto thumbsUpRecordDto);

    List<String> selectAllPhones();

    String selectExitPhone(@Param("url") String url);
    String selectExitPhoneAndName(@Param("url") String url,@Param("userName") String userName);
}
