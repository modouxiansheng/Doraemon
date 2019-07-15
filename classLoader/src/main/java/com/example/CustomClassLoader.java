package com.example;

import java.io.*;


/**
 * @program: springBootPractice
 * @description: 定制化的类加载器
 * @author: hu_pf
 * @create: 2019-07-05 17:48
 **/
public class CustomClassLoader extends ClassLoader{

    private static final String CLASS_FILE_SUFFIX = ".class";

    //AppClassLoader的父类加载器
    private ClassLoader extClassLoader;

    public CustomClassLoader(){
        ClassLoader j = String.class.getClassLoader();
        if (j == null) {
            j = getSystemClassLoader();
            while (j.getParent() != null) {
                j = j.getParent();
            }
        }
        this.extClassLoader = j ;
    }

    protected Class<?> loadClass(String name, boolean resolve){

        Class cls = null;
        cls = findLoadedClass(name);
        if (cls != null){
            return cls;
        }
        if ("com.example.watchfile.ITest".equals(name)){
            try {
                cls = getSystemClassLoader().loadClass(name);
            } catch (ClassNotFoundException e) {

            }
            return cls;
        }
        //获取ExtClassLoader
        ClassLoader extClassLoader = getExtClassLoader() ;
        //确保自定义的类不会覆盖Java的核心类
        try {
            cls = extClassLoader.loadClass(name);
            if (cls != null){
                return cls;
            }
        }catch (ClassNotFoundException e ){

        }
        cls = findClass(name);
        return cls;
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] bt = loadClassData(name);
        return defineClass(name, bt, 0, bt.length);
    }

    private byte[] loadClassData(String className) {
        // 读取Class文件呢
        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/")+CLASS_FILE_SUFFIX);
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        // 写入byteStream
        int len =0;
        try {
            while((len=is.read())!=-1){
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转换为数组
        return byteSt.toByteArray();
    }

    public ClassLoader getExtClassLoader(){
        return extClassLoader;
    }
}
