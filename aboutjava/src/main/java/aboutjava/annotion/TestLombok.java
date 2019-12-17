package aboutjava.annotion;

import lombok.Data;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: springBootPractice
 * @description: 测试Lombok
 * @author: hu_pf
 * @create: 2019-12-10 17:27
 **/
@Data
public class TestLombok {
    private String name;
    private Integer age;

    public static void main(String[] args) {
        TestLombok testLombok = new TestLombok();
        testLombok.setAge(12);
        testLombok.setName("zs");
    }
}
