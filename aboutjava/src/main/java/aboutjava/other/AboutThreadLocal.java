package aboutjava.other;

/**
 * @program: springBootPractice
 * @description: 关于ThreadLocal的验证
 * @author: hu_pf
 * @create: 2020-01-05 22:46
 **/
public class AboutThreadLocal {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("1");
        new Thread(()->{
           threadLocal.set("2");
        }).start();
        System.out.println(threadLocal.get());
    }
}
