package com.example;


import com.example.watchfile.ITest;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2019-07-12 23:55
 **/
public class WatchDog implements Runnable{

    private Map<String,FileDefine> fileDefineMap;

    public WatchDog(Map<String,FileDefine> fileDefineMap){
        this.fileDefineMap = fileDefineMap;
    }

    @Override
    public void run() {
        File file = new File(FileDefine.WATCH_PACKAGE);
        File[] files = file.listFiles();
        for (File watchFile : files){
            long newTime = watchFile.lastModified();
            FileDefine fileDefine = fileDefineMap.get(watchFile.getName());
            long oldTime = fileDefine.getLastDefine();
            //如果文件被修改了,那么重新生成累加载器加载新文件
            if (newTime!=oldTime){
                fileDefine.setLastDefine(newTime);
                loadMyClass();
            }
        }
    }

    public void loadMyClass(){
        try {
            CustomClassLoader customClassLoader = new CustomClassLoader();
            Class<?> cls = customClassLoader.loadClass("com.example.watchfile.Test",false);
            ITest test = (ITest) cls.newInstance();
            test.test();
//            Method method = cls.getMethod("test");
//            method.invoke(test);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
