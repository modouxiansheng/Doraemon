package aboutjava.other;

/**
 * @program: springBootPractice
 * @description: 关于ThreadLocal的验证
 * @author: hu_pf
 * @create: 2020-01-05 22:46
 **/
public class AboutThreadLocal {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("1");
        String s = "11";
        s.substring(1);
        new Thread(()->{
           threadLocal.set("2");
        }).start();
        System.out.println(threadLocal.get());
        Thread thread = new Thread();
        thread.start();
        thread.join();
    }
}
