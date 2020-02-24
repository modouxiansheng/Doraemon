package aboutjava.other;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springBootPractice
 * @description: 测试关于垃圾回收参数设置
 * @author: hu_pf
 * @create: 2019-12-27 18:01
 **/
public class AboutCollectionGarbage {


    public static void main(String[] args) {
        System.gc();
        List<CollectionData> collectionData = new ArrayList<>(100000);

        for (int i = 0; i < 100000; i++) {
            collectionData.add(new CollectionData());
        }

    }

}

@Data
class  CollectionData{
    private String message1;
    private String message2;
    private String message3;
    private String message4;
    private String message5;
    private String message6;
    private String message7;
    private String message8;
    private String message9;
    private String message10;
    private String message11;
    private String message12;
    private String message13;
    private String message14;
    private String message15;
    private String message16;
    private String message17;
    private String message18;
    private String message19;
    private String message20;
}
