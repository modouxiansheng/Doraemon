package com.example.rpcclient.client;

@RpcClient
public interface SendMessage {

    public String sendName(String name);
}
