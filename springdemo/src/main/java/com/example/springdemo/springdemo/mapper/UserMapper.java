package com.example.springdemo.springdemo.mapper;

import com.example.springdemo.springdemo.domain.User;

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
}
