package com.example.rpcserver.rpcHandle;

import Domain.ClassTypeAdapterFactory;
import Domain.Request;
import Domain.Response;
import com.example.rpcserver.configuration.InitRpcConfig;
import com.example.rpcserver.service.SendMessageImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

@Log4j
public class ServerThread implements Runnable {

    private Socket client = null;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
            Gson gson = gsonBuilder.create();
            //获取Socket的输出流，用来向客户端发送数据
            PrintStream out = new PrintStream(client.getOutputStream());
            //获取Socket的输入流，用来接收从客户端发送过来的数据
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            boolean flag = true;
            while (flag) {
                //接收从客户端发送过来的数据
                String str = buf.readLine();
                Request request = gson.fromJson(str, Request.class);
                if (str == null || "".equals(str)) {
                    flag = false;
                } else {
                    Response response = invokeMethod(request);
                    String res = gson.toJson(response);
                    out.println(res);
                }
            }
            out.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response invokeMethod(Request request) {
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