package aboutjava.other;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: springBootPractice
 * @description: 关于线程池
 * @author: hu_pf
 * @create: 2019-12-16 17:13
 **/
public class AboutThread {

    public static void main(String[] args) {
        Executors.newFixedThreadPool(10);
        Executors.newSingleThreadExecutor();

        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.printf("1");
            }
        });
    }
}
