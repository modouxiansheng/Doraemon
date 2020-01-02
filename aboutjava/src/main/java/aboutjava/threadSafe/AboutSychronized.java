package aboutjava.threadSafe;

/**
 * @program: springBootPractice
 * @description: 关于线程安全
 * @author: hu_pf
 * @create: 2020-01-01 14:03
 **/
public class AboutSychronized {

    public Object object = new Object();

    public void test(){
        synchronized (object){
            System.out.println("test");
//            int i = 1/0;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            test2();
        }
    }

    public synchronized void test2(){
        synchronized (AboutSychronized.class){
            System.out.println("test2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            test();
        }
    }

    public void test3(){
        Object o = new Object();
        synchronized (o){

        }
    }
}
