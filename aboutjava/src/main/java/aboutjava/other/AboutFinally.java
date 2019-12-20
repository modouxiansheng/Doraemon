package aboutjava.other;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-12-18 15:57
 **/
public class AboutFinally {
    private static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {
        try {
            while (true){
                try{
                    System.out.printf(String.valueOf(num.get()));
                    System.out.printf("\n");
                    if (num.getAndIncrement() == 10){
                        int i = 1/0;
                    }
                }catch (Exception e){
                    System.out.printf("2");
                    System.out.printf("\n");
                    throw e;
                }finally {
                    System.out.printf("3");
                    System.out.printf("\n");
                }
            }
        }finally {
            System.out.printf("finally ----");
            System.out.printf("\n");
        }

    }
}
