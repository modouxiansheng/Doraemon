package aboutjava.annotion.generate;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-12-27 16:41
 **/
@GeneratePrint("111")
public class TestAnnotion {

    private String xx = "xx";

    public void test(){
        print(new TestInterfaceImpl());
    }

    public static void print(TestInterface testInterface){
        testInterface.print();
    }
}
