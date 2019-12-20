package aboutjava.other;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: springBootPractice
 * @description: 关于线程池
 * @author: hu_pf
 * @create: 2019-12-16 17:13
 **/
public class AboutThread {

    public static void main(String[] args) {
        // 线程池饥饿死锁
//        ThreadDeadLock threadDeadLock = new ThreadDeadLock();
//        threadDeadLock.threadDeadLock();

        customerThread();

    }

    public static void customerThread(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,Integer.MAX_VALUE,1, TimeUnit.SECONDS,new SynchronousQueue<>(),
                new CustomerThreadFactory("customerThread"));

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.printf(Thread.currentThread().getName());
                    System.out.printf("\n");
                }
            });
        }
    }


    public static void initThread(){
        Executors.newFixedThreadPool(10);
        Executors.newSingleThreadExecutor();

        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0,Integer.MAX_VALUE,1, TimeUnit.SECONDS,new SynchronousQueue<>());
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {

                }
            });
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("1");
                    System.out.printf("\n");
                }
            });
        }
    }
}

class ThreadDeadLock{
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    public void threadDeadLock(){
        Future<String> taskOne  = executorService.submit(new TaskOne());
        try {
            System.out.printf(taskOne.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public class TaskOne implements Callable{

        @Override
        public Object call() throws Exception {
            Future<String> taskTow = executorService.submit(new TaskTwo());
            return "TaskOne" + taskTow.get();
        }
    }

    public class TaskTwo implements Callable{

        @Override
        public Object call() throws Exception {
            return "TaskTwo";
        }
    }
}

class CustomerThreadFactory implements ThreadFactory{

    private String name;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    CustomerThreadFactory(String name){
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,name+threadNumber.getAndIncrement());
        return thread;
    }
}

