package com.example.rpcclient.client;

import Domain.Response;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-06-18 18:39
 **/
public class MessageFuture {

    private volatile boolean success = false;
    private Response response;
    private final Object object = new Object();

    public Response getMessage(){
        synchronized (object){
            while (!success){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public void setMessage(Response response){
        synchronized (object){
            this.response = response;
            this.success = true;
            object.notify();
        }
    }
}
