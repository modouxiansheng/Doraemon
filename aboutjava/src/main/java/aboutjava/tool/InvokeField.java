package aboutjava.tool;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @program: springBootPractice
 * @description: 对属性设值
 * @author: hu_pf
 * @create: 2019-10-15 12:47
 **/
@Data
public class InvokeField {

    private Field field;

    private Object object;
}
