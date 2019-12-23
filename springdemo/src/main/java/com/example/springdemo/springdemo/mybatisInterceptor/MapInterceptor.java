package com.example.springdemo.springdemo.mybatisInterceptor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

@Intercepts({ @Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }) })
@Component
public class MapInterceptor implements Interceptor {
    @SuppressWarnings("unused")
    private Properties properties;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
        Class<? extends ResultSetHandler> clazz = resultSetHandler.getClass();
        // 反射读取boundSql 获得sql语句
        Field field = clazz.getDeclaredField("boundSql");
        field.setAccessible(true);
        BoundSql boundsql = (BoundSql) field.get(resultSetHandler);
        String sql = boundsql.getSql();
        // 如果sql中包含 MAP_KEY 和 MAP_VALUE 则被认为需要该将结果集转为Map类型
        if (sql.contains("map_key") && sql.contains("map_value")) {
            try {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                Statement statement = (Statement) invocation.getArgs()[0]; // 取得方法的参数Statement
                ResultSet rs = statement.getResultSet(); // 取得结果集
                while (rs.next()) {
                    String mapKey = rs.getString("map_key");
                    Object mapValue = rs.getInt("map_value");
                    map.put(mapKey, mapValue); // 取得结果集后K、V关联后放到MAP当中
                }
                list.add(map);
                // 这里返回list，而不是直接返回map
                return list;
            } catch (Exception e) {
                // 如果出现异常，可能是冲突，则...
                return invocation.proceed();
            }
        }
        // 如果没有进行拦截处理，则执行默认逻辑
        return invocation.proceed();
    }
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}