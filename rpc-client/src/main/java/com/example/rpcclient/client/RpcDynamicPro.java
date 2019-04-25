package com.example.rpcclient.client;

import Domain.ClassTypeAdapterFactory;
import Domain.Request;
import Domain.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-18 14:20
 **/
@Component
@Slf4j
public class RpcDynamicPro implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String requestJson = objectToJson(method, args);
        Socket client = new Socket("127.0.0.1", 20006);
        client.setSoTimeout(10000);
        //获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        //获取Socket的输入流，用来接收从服务端发送过来的数据
        BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //发送数据到服务端
        out.println(requestJson);
        Response response = new Response();
        Gson gson = new Gson();
        try {
            //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
            String responsJson = buf.readLine();
            response = gson.fromJson(responsJson, Response.class);
        } catch (SocketTimeoutException e) {
            log.info("Time out, No response");
        }
        if (client != null) {
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
            client.close(); //只关闭socket，其关联的输入输出流也会被关闭
        }
        return response.getResult();
    }

    public String objectToJson(Method method, Object[] args) {
        Request request = new Request();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String className = method.getDeclaringClass().getName();
        request.setMethodName(methodName);
        request.setParameTypes(parameterTypes);
        request.setParameters(args);
        request.setClassName(getClassName(className));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        Gson gson = gsonBuilder.create();
        return gson.toJson(request);
    }

    private String getClassName(String beanClassName) {
        String className = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        return className;
    }
}
