package aboutjava.threadSafe;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-01-01 18:57
 **/
public class AboutInvokeSychronized {

    public static void main(String[] args) {
        AboutSychronized aboutSychronized = new AboutSychronized();
       new Thread(new Runnable() {
           @Override
           public void run() {
               aboutSychronized.test();
           }
       }).start();
        AboutSychronized aboutSychronized2 = new AboutSychronized();
        new Thread(new Runnable() {
            @Override
            public void run() {
                aboutSychronized2.test();
            }
        }).start();

    }
}
