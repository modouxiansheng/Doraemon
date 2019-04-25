package Domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-01-17 20:57
 **/
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = -2393333111247658778L;
    private Object result;
}
