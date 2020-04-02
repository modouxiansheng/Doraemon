package aboutjava.asm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: springBootPractice
 * @description: 自定义的类加载器
 * @author: hu_pf
 * @create: 2020-04-02 13:41
 **/
public class MyClassLoader extends ClassLoader{
    private static final String CLASS_FILE_SUFFIX = ".class";

    //AppClassLoader的父类加载器
    private ClassLoader extClassLoader;

    public MyClassLoader(){
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
        if ("aboutjava.asm.Substitution".equals(name)){
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
//        for (int i = 0; i < bt.length; i++) {
//            System.out.println(bt[i]);
//        }
        updateByte(bt);
        return defineClass(name, bt, 0, bt.length);
    }

    private void updateByte(byte[] bt){
        for (int i = 0; i < bt.length; i++) {
            Byte b = bt[i];
            Byte b1 = bt[i+1];
            Byte b2 = bt[i+2];
            Byte b3 = bt[i+3];
            if (b.equals(new Byte("1"))&&b1.equals(new Byte("0"))&&b2.equals(new Byte("1"))&&b3.equals(new Byte("97"))){
                bt[i+3] = new Byte("99");
                break;
            }
        }
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
