package aboutjava.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: springBootPractice
 * @description: 为什么重写equals要重写hashCode
 * @author: hu_pf
 * @create: 2019-11-26 19:52
 **/
public class EqualsAndHashCode {

    public static void main(String[] args) {

        Map<CustomizedKey, Integer> data = getData();

        CustomizedKey key = CustomizedKey.builder().id(1).name("key").build();

        Integer integer = data.get(key);

        System.out.printf(String.valueOf(integer));
    }

    private static Map<CustomizedKey,Integer> getData(){
        Map<CustomizedKey,Integer> customizedKeyIntegerMap = new HashMap<>();
        CustomizedKey key = CustomizedKey.builder().id(1).name("key").build();
        customizedKeyIntegerMap.put(key,10);
        return customizedKeyIntegerMap;
    }

    private static void validTwoEquals(){
        Integer i = 5;
        Integer j = 5;
        String s = "s";
        String k = new String("s");
        System.out.print(String.valueOf(i.equals(j)));
        System.out.print(String.valueOf(s == k));
    }
}

@Builder
@NoArgsConstructor
@AllArgsConstructor
@lombok.EqualsAndHashCode
class CustomizedKey{
    private Integer id;
    private String name;
}