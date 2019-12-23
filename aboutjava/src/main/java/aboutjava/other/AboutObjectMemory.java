package aboutjava.other;

import com.sshtools.j2ssh.util.Hash;
import lombok.AllArgsConstructor;
import org.openjdk.jol.info.ClassLayout;

import java.util.*;

/**
 * @program: springBootPractice
 * @description: 关于一个对象占用多少内存
 * @author: hu_pf
 * @create: 2019-12-20 14:56
 **/
public class AboutObjectMemory {

    public static void main(String[] args) throws InterruptedException {
//        System.out.printf(String.valueOf("1234567".getBytes().length));
        String s = "1234567";
         byte [] b = new byte[10];
        System.out.print(ClassLayout.parseClass(Integer.class).toPrintable());

//        List<Animal> animals = new ArrayList<>(20000000);
//        Thread.sleep(20000);
//        System.out.printf("start");
//        for (int i = 0; i < 20000000; i++) {
//            Animal animal = new Animal();
//            animals.add(animal);
//        }
//        Thread.sleep(10000);


//        List<String> mylist = new ArrayList();
//        Thread.sleep(20000);
//        AboutObjectMemory aboutObjectMemory = new AboutObjectMemory();
//        for (int i = 0; i < 10000000; i++) {
//            mylist.add("1");
//        }
//        Thread.sleep(50000);


        Map<Animal,Animal> map = new HashMap<>(20000000);
        Thread.sleep(20000);
        for (int i = 0; i < 20000000; i++) {
            map.put(new Animal(),new Animal());
        }
        Thread.sleep(50000);

    }

    private String getString(int i){
        StringBuffer stringBuffer = new StringBuffer(i+"");
        int length = 36 - stringBuffer.length();
        for (int j = 0; j < length; j++) {
            stringBuffer.append(0);
        }
        return stringBuffer.toString();
    }

    private String getUUid(){
        return UUID.randomUUID().toString();
    }

}




class Animal{

    private int age;

//    private String name;

//    Animal(String name){
//        this.name = name;
//    }
}

