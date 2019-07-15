# 如何自己手写一个热加载

> 热加载：在不停止程序运行的情况下，对类（对象）的动态替换

## Java ClassLoader 简述

Java中的类从被加载到内存中到卸载出内存为止，一共经历了七个阶段：加载、验证、准备、解析、初始化、使用、卸载。

![](http://ww1.sinaimg.cn/large/006tNc79ly1g50iakc3iaj313e0gy7c0.jpg)

接下来我们重点讲解加载和初始化这两步

### 加载

在加载的阶段，虚拟机需要完成以下三件事：

* 通过一个类的全限定名来获取定义此类的**二进制字节流**
* 将这个字节流所代表的的**静态存储结构**转化为**方法区**的运行时数据结构
* 在内存中生成一个代表这个类的`java.lang.Class`对象，作为方法区这个类的各种数据的访问入口。

这三步都是通过类加载器来实现的。而官方定义的Java类加载器有`BootstrapClassLoader`、`ExtClassLoader`、`AppClassLoader`。这三个类加载器分别负责加载不同路径的类的加载。并形成一个父子结构。

![](http://ww4.sinaimg.cn/large/006tNc79ly1g50j38p2qnj30uy0ladle.jpg)

|类加载器名称|负责加载目录|
|----------|----------|
|`BootstrapClassLoader `|处于类加载器层次结构的最高层，负责 sun.boot.class.path 路径下类的加载，默认为 jre/lib 目录下的核心 API 或 -Xbootclasspath 选项指定的 jar 包|
|`ExtClassLoader `|加载路径为 java.ext.dirs，默认为 jre/lib/ext 目录或者 -Djava.ext.dirs 指定目录下的 jar 包加载|
|`AppClassLoader `|加载路径为 java.class.path，默认为环境变量 CLASSPATH 中设定的值。也可以通过 -classpath 选型进行指定|

> 默认情况下，例如我们使用关键字`new`或者`Class.forName`都是通过`AppClassLoader `类加载器来加载的

正因为是此父子结构，所以默认情况下如果要加载一个类，会优先将此类交给其父类进行加载（直到顶层的`BootstrapClassLoader `也没有），如果父类都没有，那么才会将此类交给子类加载。这就是类加载器的双亲委派规则。

### 初始化

当我们要使用一个类的执行方法或者属性时，类必须是加载到内存中并且完成初始化的。那么类是什么时候被初始化的呢？有以下几种情况

* 使用new关键字实例化对象的时候、读取或者设置一个类的静态字段、以及调用一个类的静态方法。
* 使用`java.lang.reflect`包的方法对类进行反射调用时，如果类没有进行初始化，那么先进行初始化。
* 初始化一个类的时候，如果发现其父类没有进行初始化，则先触发父类的初始化。
* 当虚拟机启动时，用户需要制定一个执行的主类(包含main()方法的那个类)虚拟机会先初始化这个主类。

## 如何实现热加载？

在上面我们知道了在默认情况下，类加载器是遵循双亲委派规则的。所以我们要实现热加载，那么我们需要加载的那些类就不能交给系统加载器来完成。所以我们要自定义类加载器来写我们自己的规则。

### 实现自己的类加载器

要想实现自己的类加载器，只需要继承`ClassLoader`类即可。而我们要打破双亲委派规则，那么我们就必须要重写`loadClass`方法，因为默认情况下`loadClass`方法是遵循双亲委派的规则的。

```
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

```

为什么要先获取`ExtClassLoader`类加载器呢？其实这里是借鉴了Tomcat里面的设计，是为了避免我们自定义的类加载器覆盖了一些核心类。例如`java.lang.Object`。

为什么是获取`ExtClassLoader `类加载器而不是获取`AppClassLoader `呢？这是因为如果我们获取了`AppClassLoader `进行加载，那么不还是双亲委派的规则了吗？

### 监控class文件

这里我们使用`ScheduledThreadPoolExecutor `来进行周期性的监控文件是否修改。在程序启动的时候记录文件的最后修改时间。随后周期性的查看文件的最后修改时间是否改动。如果改动了那么就重新生成类加载器进行替换。这样新的文件就被加载进内存中了。

首先我们建立一个需要监控的文件

```
public class Test {

    public void test(){
        System.out.println("Hello World! Version one");
    }
}

```

我们通过在程序运行时修改版本号，来动态的输出版本号。接下来我们建立周期性执行的任务类。

```
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
            Object test = cls.newInstance();
            Method method = cls.getMethod("test");
            method.invoke(test);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
```

![](http://ww1.sinaimg.cn/large/006tNc79ly1g50lcx54pxg314r0mfx6p.gif)

可以看到在上面的gif演示图中我们简单的实现了热加载的功能。

### 优化

在上面的方法调用中我们是使用了`getMethod()`方法来调用的。此时或许会有疑问，为什么不直接将`newInstance()`强转为`Test`类呢？

如果我们使用了强转的话，代码会变成这样`Test test = (Test) cls.newInstance()`。但是在运行的时候会抛`ClassCastException `异常。这是为什么呢？因为在Java中确定两个类是否相等，除了看他们两个类文件是否相同以外还会看他们的类加载器是否相同。所以即使是同一个类文件，如果是两个不同的类加载器来加载的，那么它们的类型就是不同的。

`WatchDog`类是由我们new出来的。所以默认是`AppClassLoader `来加载的。所以`test `变量的声明类型是`WatchDog `方法中的一个属性，所以也是由`AppClassLoader `来加载的。因此两个类不相同。

该如何解决呢？问题就出在了`=`号双方的类不一样，那么我们给它搞成一样不就行了吗？怎么搞？答案就是接口。默认情况下，如果我们实现了一个接口，那么此接口一般都是以子类的加载器为主的。意思就是如果没有特殊要求的话，例如`A implements B` 如果A的加载器是自定义的。那么B接口的加载器也是和子类是一样的。

所以我们要将接口的类加载器搞成是`AppClassLoader `来加载。所以自定义加载器中加入这一句

```
if ("com.example.watchfile.ITest".equals(name)){
    try {
        cls = getSystemClassLoader().loadClass(name);
    } catch (ClassNotFoundException e) {

    }
    return cls;
}
```

建立接口

```
public interface ITest {

    void test();
}

```

这样我们就能愉快的调用了。直接调用其方法。不会抛异常，因为`=`号双方的类是一样的。

```
CustomClassLoader customClassLoader = new CustomClassLoader();
Class<?> cls = customClassLoader.loadClass("com.example.watchfile.Test",false);
ITest test = (ITest) cls.newInstance();
test.test();

```

## [源代码地址Github]()

## 参考文章

* [https://www.ibm.com/developerworks/cn/java/j-lo-hotswapcls/index.html](https://www.ibm.com/developerworks/cn/java/j-lo-hotswapcls/index.html) 
* [Java虚拟机]()
