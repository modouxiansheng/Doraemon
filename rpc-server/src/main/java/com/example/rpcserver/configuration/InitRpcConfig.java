package com.example.rpcserver.configuration;

import com.example.rpcserver.rpcHandle.CommonDeal;
import com.example.rpcserver.rpcHandle.ServerThread;
import com.example.rpcserver.service.SendMessageImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-20 15:47
 **/
@Component
@Log4j
public class InitRpcConfig implements CommandLineRunner {
    @Autowired
    private ApplicationContext applicationContext;

    public static Map<String, Object> rpcServiceMap = new HashMap<>();

    @Override
    public void run(String... args) throws IOException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Service.class);
        for (Object bean : beansWithAnnotation.values()) {
            Class<?> clazz = bean.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> inter : interfaces) {
                rpcServiceMap.put(getClassName(inter.getName()), bean);
                log.info("已经加载的服务:" + inter.getName());
            }
        }
        startPort();
    }

    private String getClassName(String beanClassName) {
        String className = beanClassName.substring(beanClassName.lastIndexOf(".") + 1);
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        return className;
    }

    public void startPort() throws IOException{
//        //服务端在20006端口监听客户端请求的TCP连接
//        ServerSocket server = new ServerSocket(20006);
//        Socket client = null;
//        boolean f = true;
//        while (f) {
//            //等待客户端的连接，如果没有获取连接
//            client = server.accept();
//            System.out.println("与客户端连接成功！");
//            //为每个客户端连接开启一个线程
//            new Thread(new ServerThread(client)).start();
//        }
//        server.close();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boos, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                //获得实现类处理过后的返回值
                                String invokeMethodMes = CommonDeal.getInvokeMethodMes(msg);
                                ByteBuf encoded = ctx.alloc().buffer(4 * invokeMethodMes.length());
                                encoded.writeBytes(invokeMethodMes.getBytes());
                                ctx.writeAndFlush(encoded);
                            }
                        });
                    }
                }).bind(20006);
    }
}
