//package com.example.ioc.config;
//
//import com.example.ioc.annotion.MyIoc;
//import com.example.ioc.annotion.MyIocUse;
//import com.example.ioc.core.MyBeanFactory;
//import com.example.ioc.core.MyBeanFactoryImpl;
//import com.example.ioc.domain.BeanDefinition;
//import com.example.ioc.domain.MyIocUseDemo;
//import com.example.ioc.domain.Student;
//import com.example.ioc.domain.User;
//import org.reflections.Reflections;
//import org.reflections.scanners.SubTypesScanner;
//import org.reflections.scanners.TypeAnnotationsScanner;
//import org.reflections.util.ConfigurationBuilder;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.net.URL;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @program: springBootPractice
// * @description:
// * @author: hu_pf
// * @create: 2019-01-23 20:07
// **/
//@Component
//@Order(value = 2)
//@MyIoc
//public class IocInitUseConfig implements CommandLineRunner {
//
//    @MyIocUse
//    private MyIocUseDemo myIocUseDemo;
//
//    @Override
//    public void run(String... args) throws Exception {
//        ConcurrentHashMap<String, BeanDefinition> beanDefineMap = MyBeanFactoryImpl.getBeanDefineMap();
//        MyBeanFactoryImpl myBeanFactory = new MyBeanFactoryImpl();
//        for (BeanDefinition beanDefinition : beanDefineMap.values()){
//            String className = beanDefinition.getClassName();
//            Class<?> clazz = Class.forName(className);
//            Field[] declaredFields = clazz.getDeclaredFields();
//            setFild(declaredFields,myBeanFactory,clazz);
//        }
//        System.out.println("1");
//    }
//
//    public void setFild(Field[] fields,MyBeanFactory myBeanFactory,Class clazz) throws Exception {
//        for (Field field : fields){
//            MyIocUse annotation = field.getAnnotation(MyIocUse.class);
//            if (annotation != null){
////                Object beanByName = myBeanFactory.getBeanByName(field.getType().getName());
//                Object clazzBean = myBeanFactory.getBeanByName(clazz.getName());
//                System.out.println("1");
//            }
//        }
//    }
//
//    /**
//     * 获取某包下所有类
//     * @param packageName 包名
//     * @return 类的完整名称
//     */
//    public static Set<String> getClassName(String packageName) {
//        Set<String> classNames = null;
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        String packagePath = packageName.replace(".", "/");
//
//        URL url = loader.getResource(packagePath);
//        if (url != null) {
//            String protocol = url.getProtocol();
//            if (protocol.equals("file")) {
//                classNames = getClassNameFromDir(url.getPath(), packageName);
//            }
//        }
//        return classNames;
//    }
//
//    private static Set<String> getClassNameFromDir(String filePath, String packageName) {
//        Set<String> className = new HashSet<String>();
//        File file = new File(filePath);
//        File[] files = file.listFiles();
//        for (File childFile : files) {
//            if (childFile.isDirectory()) {
//                className.addAll(getClassNameFromDir(childFile.getPath(), packageName+"."+childFile.getName()));
//            } else {
//                String fileName = childFile.getName();
//                if (fileName.endsWith(".class") && !fileName.contains("$")) {
//                    className.add(packageName+ "." + fileName.replace(".class", ""));
//                }
//            }
//        }
//        return className;
//    }
//}
