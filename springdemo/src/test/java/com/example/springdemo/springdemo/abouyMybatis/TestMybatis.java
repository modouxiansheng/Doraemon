package com.example.springdemo.springdemo.abouyMybatis;

import com.example.springdemo.springdemo.SpringdemoApplicationTests;
import com.example.springdemo.springdemo.domain.User;
import com.example.springdemo.springdemo.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: springBootPractice
 * @description: Mybatis的测试类
 * @author: hu_pf
 * @create: 2019-12-22 22:51
 **/
public class TestMybatis extends SpringdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void testAdd(){
        Map<String,Object> insertMap = new HashMap<>();
        insertMap.put("name","xiaol2i");
        insertMap.put("age","11");
        userMapper.addUser(insertMap);
    }

    @Test
    public void testSelect(){
        List<Map<String,Integer>> users = userMapper.queryAllUserMap();
        System.out.printf("1");
    }

    @Test
    public void testSel(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Collection<Class<?>> mappers = sqlSession.getConfiguration().getMapperRegistry().getMappers();
        mappers.forEach(cls ->{
            Object mapper = sqlSession.getMapper(cls);
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                Method declaredMethod = declaredMethods[i];
                Map<String,Object> insertMap = new HashMap<>();
                insertMap.put("name","xiaol2i");
                insertMap.put("age","11");
                try {
                    declaredMethod.invoke(mapper,insertMap);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.printf("1");
    }

}
