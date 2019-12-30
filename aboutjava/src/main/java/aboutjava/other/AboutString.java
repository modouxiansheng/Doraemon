package aboutjava.other;

import java.lang.reflect.Field;

/**
 * @program: springBootPractice
 * @description: 关于String的知识
 * @author: hu_pf
 * @create: 2019-12-26 09:57
 **/
public class AboutString {

    public static void main(String[] args) throws Exception {

        String abc = "abc";
        String bbb = "abc";
        updateChar("abc");
        System.out.println(abc);
        System.out.println(bbb);
    }
    public static void updateChar(String str) throws Exception {
        final Field value = String.class.getDeclaredField("value");
        value.setAccessible(true);
        char [] chars = new char[2];
        chars[0] =  "1".charAt(0);
        chars[1] =  "2".charAt(0);
        value.set(str,chars);
    }
}
