package aboutjava.other;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: springBootPractice
 * @description: 关于wait和notify的相关知识
 * @author: hu_pf
 * @create: 2020-01-11 22:21
 **/
public class AboutWaitAndNotify {

    private static Object lock = new Object();

    private volatile static int num = 0;

    private volatile static char letter = 'a';

    private volatile static boolean flag = true;

    public static void main(String[] args) {

        new Thread(()->{
            synchronized (lock){
                while (num<26){
                    if (flag){
                        System.out.println("Thread 1 :"+ letter++);
                        num++;
                        flag = false;
                        lock.notify();
                    }else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(()->{
            synchronized (lock){
                while (num<26){
                    if(!flag){
                        System.out.println("Thread 2 :"+ letter++);
                        num++;
                        flag = true;
                        lock.notify();
                    }else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

    }
}
