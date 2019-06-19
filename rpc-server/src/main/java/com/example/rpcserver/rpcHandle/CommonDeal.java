package com.example.rpcserver.rpcHandle;

import Domain.ClassTypeAdapterFactory;
import Domain.Request;
import Domain.Response;
import com.example.rpcserver.configuration.InitRpcConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-06-17 17:40
 **/
@Log4j
public class CommonDeal {

    public static String getInvokeMethodMes(String str){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        Gson gson = gsonBuilder.create();
        Request request = gson.fromJson(str, Request.class);
        return gson.toJson(invokeMethod(request));
    }

    private static Response invokeMethod(Request request) {
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?>[] parameTypes = request.getParameTypes();
        Object o = InitRpcConfig.rpcServiceMap.get(className);
        Response response = new Response();
        try {
            Method method = o.getClass().getDeclaredMethod(methodName, parameTypes);
            Object invokeMethod = method.invoke(o, parameters);
            response.setResult(invokeMethod);
        } catch (NoSuchMethodException e) {
            log.info("没有找到" + methodName);
        } catch (IllegalAccessException e) {
            log.info("执行错误" + parameters);
        } catch (InvocationTargetException e) {
            log.info("执行错误" + parameters);
        }
        return response;
    }
}
