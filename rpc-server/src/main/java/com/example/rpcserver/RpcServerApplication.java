package com.example.rpcserver;

import Domain.ClassTypeAdapterFactory;
import Domain.Request;
import Domain.Response;
import com.example.rpcserver.configuration.InitRpcConfig;
import com.example.rpcserver.rpcHandle.ServerThread;
import com.example.rpcserver.service.SendMessageImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class RpcServerApplication {

	public static void main(String[] args) throws NoSuchMethodException {
		SpringApplication.run(RpcServerApplication.class, args);
    }
}

