package aboutjava.tool;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AliasForCompare {

    String describe() default "";
}
