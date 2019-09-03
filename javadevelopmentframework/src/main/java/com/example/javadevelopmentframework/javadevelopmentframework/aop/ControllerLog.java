package com.example.javadevelopmentframework.javadevelopmentframework.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

/**
 * @program: springBootPractice
 * @description: 日志统一打印 - 入参 出参 执行时间
 * @author: hu_pf
 * @create: 2019-09-02 18:42
 **/
@Slf4j
@Aspect
@Component
public class ControllerLog {

    private static final ThreadLocal<Long> START_TIME_THREAD_LOCAL =
            new NamedThreadLocal<>("ThreadLocal StartTime");

    private static final ThreadLocal<String> LOG_PREFIX_THREAD_LOCAL =
            new NamedThreadLocal<>("ThreadLocal LogPrefix");

    /**
     * <li>Before       : 在方法执行前进行切面</li>
     * <li>execution    : 定义切面表达式</li>
     * <p>public * com.example.javadevelopmentframework.javadevelopmentframework.controller..*.*(..))
     *      <li>public :匹配所有目标类的public方法，不写则匹配所有访问权限</li>
     *      <li>第一个* :方法返回值类型，*代表所有类型 </li>
     *      <li>第二个* :包路径的通配符</li>
     *      <li>第三个..* :表示impl这个目录下所有的类，包括子目录的类</li>
     *      <li>第四个*(..) : *表示所有任意方法名,..表示任意参数</li>
     * </p>
     * @param
     */
    @Pointcut("execution(public * com.example.javadevelopmentframework.javadevelopmentframework.controller..*.*(..))")
    public void exectionMethod(){}


    @Before("exectionMethod()")
    public void doBefore(JoinPoint joinPoint){
        START_TIME_THREAD_LOCAL.set(System.currentTimeMillis());
        StringBuilder argsDes = new StringBuilder();
        //获取类名
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            argsDes.append("第" + (i + 1) + "个参数为:" + args[i]+"\n");
        }
        String logPrefix = className+"."+methodName;
        LOG_PREFIX_THREAD_LOCAL.set(logPrefix);
        log.info(logPrefix+"Begin 入参为:{}",argsDes.toString());
    }

    @AfterReturning(pointcut="exectionMethod()",returning = "rtn")
    public Object doAfter(Object rtn){
        long endTime = System.currentTimeMillis();
        long begin = START_TIME_THREAD_LOCAL.get();
        log.info(LOG_PREFIX_THREAD_LOCAL.get()+"End 出参为:{},耗时:{}",rtn,endTime-begin);
        destoryThreadLocal();
        return rtn;
    }

    public static String getLogPrefix(){
        return LOG_PREFIX_THREAD_LOCAL.get();
    }

    public static void destoryThreadLocal(){
        START_TIME_THREAD_LOCAL.remove();
        LOG_PREFIX_THREAD_LOCAL.remove();
    }

}
