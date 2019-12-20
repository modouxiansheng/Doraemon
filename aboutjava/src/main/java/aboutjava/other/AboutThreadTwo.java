package aboutjava.other;

import lombok.Data;
import lombok.Synchronized;

import java.util.concurrent.*;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-12-18 16:18
 **/
public class AboutThreadTwo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,1, TimeUnit.SECONDS,new SynchronousQueue<>(),
                new CustomerThreadFactory("customerThread"));
//
//        Future<?> submit1 = threadPoolExecutor.submit(() -> sayHi("x"));
//        Future<?> submit2 = threadPoolExecutor.submit(() -> sayHi("x"));
//
//        Future<?> submit3 = threadPoolExecutor.submit(() -> sayHi("x"));
//
//        if (true || test()){
//            System.out.printf("2");
//        }

        threadPoolExecutor.execute(()->sayHi("x"));
//        threadPoolExecutor.execute(()->sayHi("x"));
//        threadPoolExecutor.execute(()->sayHi("xxxx"));
//        threadPoolExecutor.execute(()->sayHi("x"));
//        threadPoolExecutor.execute(()->sayHi("x"));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                Thread.currentThread().interrupt();
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.printf("1");
                    System.out.printf("\n");
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    if (i == 9999){
                        System.out.printf("end");
                        thread.interrupt();
                    }
                }
            }
        }).start();
    }
    private static Boolean test(){
        System.out.printf("1");
        return false;
    }

    public static void sayHi(String name){
        String str = Thread.currentThread().getName();
        if ("xxxx".equals(name)){
            System.out.printf(str+"Exception");
            System.out.printf("\n");
            throw new RuntimeException("xxxx");
        }
        System.out.printf(str + " --- no");
        System.out.printf("\n");
    }
}

@Data
class MyThread implements Runnable{

    private Runnable task;
    private Thread thread;

//    MyThread(Runnable task){
//        this.task = task;
//        this.thread = new Thread(task);
//    }


    @Override
    public void run() {
        System.out.printf(Thread.currentThread().getName() + "begin:");
        System.out.printf("\n");
        throw new RuntimeException("xxxx");
    }

//    private void runTask(){
//        task.run();
//    }
}

class CustomerThreadExectors extends ThreadPoolExecutor{

    public CustomerThreadExectors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CustomerThreadExectors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CustomerThreadExectors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CustomerThreadExectors(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        this.setCorePoolSize(5);
        synchronized(this){

        }
    }
}
