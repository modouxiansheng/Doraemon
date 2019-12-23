package com.example.springdemo.springdemo.mybatisHandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: springBootPractice
 * @description: String和Data类型的类型处理器
 * @author: hu_pf
 * @create: 2019-12-22 23:16
 **/
//@MappedJdbcTypes(JdbcType.VARCHAR)
//@MappedTypes(String.class)
public class StringAndDataTypeHandler implements TypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        System.out.printf("1");
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
