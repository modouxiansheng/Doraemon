package com.example.rpcclient.client;

import Domain.Response;
import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @program: springBootPractice
 * @description:客户端连接
 * @author: hu_pf
 * @create: 2019-06-18 18:31
 **/
public class NettyClientConnect {

    private final Map<Long,MessageFuture> futureMap = new ConcurrentHashMap<>();
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void connect(String requestJson,Long threadId){
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().
                        addLast(new StringDecoder()).
                        addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                Gson gson = new Gson();
                                Response response = gson.fromJson(msg, Response.class);
                                MessageFuture messageFuture = futureMap.get(threadId);
                                messageFuture.setMessage(response);
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                futureMap.put(threadId,new MessageFuture());
                                countDownLatch.countDown();
                                ByteBuf encoded = ctx.alloc().buffer(4 * requestJson.length());
                                encoded.writeBytes(requestJson.getBytes());
                                ctx.writeAndFlush(encoded);
                            }
                        });
            }
        }).connect("127.0.0.1", 20006);
    }

    public Response getResponse(Long threadId){
        MessageFuture messageFuture = null;
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messageFuture = futureMap.get(threadId);
        return messageFuture.getMessage();
    }
}
