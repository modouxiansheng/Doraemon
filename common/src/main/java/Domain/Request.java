package Domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf@suixingpay.com
 * @create: 2019-01-17 20:46
 **/
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = 3933918042687238629L;
    private String className;
    private String methodName;
    private Class<?> [] parameTypes;
    private Object [] parameters;
}
