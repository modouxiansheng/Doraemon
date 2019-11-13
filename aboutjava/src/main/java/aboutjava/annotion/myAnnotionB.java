package aboutjava.annotion;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @program: springBootPractice
 * @description: 自定义注解处理器
 * @author: hu_pf
 * @create: 2019-10-29 16:23
 **/
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("aboutjava.annotion.TestAnno")
public class myAnnotionB extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.out.printf("11");

        return false;
    }
}
