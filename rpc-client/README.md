# 徒手撸一个简单的RPC框架

之前在[牛逼哄哄的 RPC 框架，底层到底什么原理](https://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247487507&idx=1&sn=7511f822bf95b25a2586dfdb0c06546f&chksm=eb539525dc241c33507a02d137bd48b9d9e2a33c8b76030f6cc372d0cfa478c8d83d230a9e96&scene=21#wechat_redirect)得知了RPC（远程过程调用）简单来说就是调用远程的服务就像调用本地方法一样，其中用到的知识有序列化和反序列化、动态代理、网络传输、动态加载、反射这些知识点。发现这些知识都了解一些。所以就想着试试自己实现一个简单的RPC框架，即巩固了基础的知识，也能更加深入的了解RPC原理。当然一个完整的RPC框架包含了许多的功能，例如服务的发现与治理，网关等等。本篇只是简单的实现了一个调用的过程。

## 传参出参分析

一个简单请求可以抽象为两步

![](https://ws1.sinaimg.cn/large/006tNc79ly1fz9uxqtrg8j30zs0ngn4d.jpg)

 那么就根据这两步进行分析，在请求之前我们应该发送给服务端什么信息？而服务端处理完以后应该返回客户端什么信息？

### 在请求之前我们应该发送给服务端什么信息？

由于我们在客户端调用的是服务端提供的接口，所以我们需要将客户端调用的信息传输过去，那么我们可以将要传输的信息分为两类

* 第一类是服务端可以根据这个信息找到相应的接口实现类和方法
* 第二类是调用此方法传输的参数信息

那么我们就根据要传输的两类信息进行分析，什么信息能够找到相应的实现类的相应的方法？要找到方法必须要先找到类，这里我们可以简单的用Spring提供的Bean实例管理ApplicationContext进行类的寻找。所以要找到类的实例只需要知道此类的名字就行，找到了类的实例，那么如何找到方法呢？在反射中通过反射能够根据方法名和参数类型从而找到这个方法。那么此时第一类的信息我们就明了了，那么就建立相应的是实体类存储这些信息。

```
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = 3933918042687238629L;
    private String className;
    private String methodName;
    private Class<?> [] parameTypes;
    private Object [] parameters;
}
```

### 服务端处理完以后应该返回客户端什么信息？

上面我们分析了客户端应该传输什么信息给服务端，那么服务端处理完以后应该传什么样的返回值呢？这里我们只考虑最简单的情况，客户端请求的线程也会一直在等着，不会有异步处理这一说，所以这么分析的话就简单了，直接将得到的处理结果返回就行了。

```
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = -2393333111247658778L;
    private Object result;
}
```

> 由于都涉及到了网络传输，所以都要实现序列化的接口

## 如何获得传参信息并执行？-客户端

上面我们分析了客户端向服务端发送的信息都有哪些？那么我们如何获得这些信息呢？首先我们调用的是接口，所以我们需要写自定义注解然后在程序启动的时候将这些信息加载在Spring容器中。有了这些信息那么我们就需要传输了，调用接口但是实际上执行的确实网络传输的过程，所以我们需要动态代理。那么就可以分为以下两步

* 初始化信息阶段：将key为接口名，value为动态接口类注册进Spring容器中
* 执行阶段：通过动态代理，实际执行网络传输

### 初始化信息阶段

由于我们使用Spring作为Bean的管理，所以要将接口和对应的代理类注册进Spring容器中。而我们如何找到我们想要调用的接口类呢？我们可以自定义注解进行扫描。将想要调用的接口全部注册进容器中。

创建一个注解类，用于标注哪些接口是可以进行Rpc的

```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcClient {
}

```

然后创建对于`@RpcClient`注解的扫描类`RpcInitConfig`，将其注册进Spring容器中

```
public class RpcInitConfig implements ImportBeanDefinitionRegistrar{
    
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider provider = getScanner();
        //设置扫描器
        provider.addIncludeFilter(new AnnotationTypeFilter(RpcClient.class));
        //扫描此包下的所有带有@RpcClient的注解的类
        Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents("com.example.rpcclient.client");
        for (BeanDefinition beanDefinition : beanDefinitionSet){
            if (beanDefinition instanceof AnnotatedBeanDefinition){
                //获得注解上的参数信息
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                String beanClassAllName = beanDefinition.getBeanClassName();
                Map<String, Object> paraMap = annotatedBeanDefinition.getMetadata()
                        .getAnnotationAttributes(RpcClient.class.getCanonicalName());
                //将RpcClient的工厂类注册进去
                BeanDefinitionBuilder builder = BeanDefinitionBuilder
                        .genericBeanDefinition(RpcClinetFactoryBean.class);
                //设置RpcClinetFactoryBean工厂类中的构造函数的值
                builder.addConstructorArgValue(beanClassAllName);
                builder.getBeanDefinition().setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                //将其注册进容器中
                registry.registerBeanDefinition(
                        beanClassAllName ,
                        builder.getBeanDefinition());
            }
        }
    }
    //允许Spring扫描接口上的注解
    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
            }
        };
    }
}
```

由于上面注册的是工厂类，所以我们建立一个工厂类`RpcClinetFactoryBean`继承Spring中的`FactoryBean`类，由其统一创建`@RpcClient`注解的代理类

```
@Data
public class RpcClinetFactoryBean implements FactoryBean {

    @Autowired
    private RpcDynamicPro rpcDynamicPro;

    private Class<?> classType;


    public RpcClinetFactoryBean(Class<?> classType) {
        this.classType = classType;
    }

    @Override
    public Object getObject(){
        ClassLoader classLoader = classType.getClassLoader();
        Object object = Proxy.newProxyInstance(classLoader,new Class<?>[]{classType},rpcDynamicPro);
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.classType;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

```

> 注意此处的`getObjectType `方法，在将工厂类注入到容器中的时候，这个方法返回的是什么Class类型那么注册进容器中就是什么Class类型。

然后看一下我们创建的代理类`rpcDynamicPro `

```
@Component
@Slf4j
public class RpcDynamicPro implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       String requestJson = objectToJson(method,args);
        Socket client = new Socket("127.0.0.1", 20006);
        client.setSoTimeout(10000);
        //获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        //获取Socket的输入流，用来接收从服务端发送过来的数据
        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
        //发送数据到服务端
        out.println(requestJson);
        Response response = new Response();
        Gson gson =new Gson();
        try{
            //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
            String responsJson = buf.readLine();
            response = gson.fromJson(responsJson, Response.class);
        }catch(SocketTimeoutException e){
            log.info("Time out, No response");
        }
        if(client != null){
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
            client.close(); //只关闭socket，其关联的输入输出流也会被关闭
        }
        return response.getResult();
    }

    public String objectToJson(Method method,Object [] args){
        Request request = new Request();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String className = method.getDeclaringClass().getName();
        request.setMethodName(methodName);
        request.setParameTypes(parameterTypes);
        request.setParameters(args);
        request.setClassName(getClassName(className));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        Gson gson = gsonBuilder.create();
        return gson.toJson(request);
    }

    private String getClassName(String beanClassName){
        String className = beanClassName.substring(beanClassName.lastIndexOf(".")+1);
        className = className.substring(0,1).toLowerCase() + className.substring(1);
        return className;
    }
}


```
我们的客户端已经写完了，传给服务端的信息我们也已经拼装完毕了。剩下的工作就简单了，开始编写服务端的代码。

## 服务端处理完以后应该返回客户端什么信息？-服务端

服务端的代码相比较客户端来说要简单一些。可以简单分为下面三步

* 拿到接口名以后，通过接口名找到实现类
* 通过反射进行对应方法的执行
* 返回执行完的信息

那么我们就根据这三步进行编写代码

### 拿到接口名以后，通过接口名找到实现类

如何通过接口名拿到对应接口的实现类呢？这就需要我们在服务端启动的时候将其对应信息加载进去

```
@Component
@Log4j
public class InitRpcConfig implements CommandLineRunner {
    @Autowired
    private ApplicationContext applicationContext;

    public static Map<String,Object> rpcServiceMap = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Service.class);
        for (Object bean: beansWithAnnotation.values()){
            Class<?> clazz = bean.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> inter : interfaces){
                rpcServiceMap.put(getClassName(inter.getName()),bean);
                log.info("已经加载的服务:"+inter.getName());
            }
        }
    }

    private String getClassName(String beanClassName){
        String className = beanClassName.substring(beanClassName.lastIndexOf(".")+1);
        className = className.substring(0,1).toLowerCase() + className.substring(1);
        return className;
    }
}

```

此时`rpcServiceMap `存储的就是接口名和其对应的实现类的对应关系。

### 通过反射进行对应方法的执行

此时拿到了对应关系以后就能根据客户端传过来的信息找到相应的实现类中的方法。然后进行执行并返回信息就行

```
public Response invokeMethod(Request request){
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?>[] parameTypes = request.getParameTypes();
        Object o = InitRpcConfig.rpcServiceMap.get(className);
        Response response = new Response();
        try {
            Method method = o.getClass().getDeclaredMethod(methodName, parameTypes);
            Object invokeMethod = method.invoke(o, parameters);
            response.setResult(invokeMethod);
        } catch (NoSuchMethodException e) {
            log.info("没有找到"+methodName);
        } catch (IllegalAccessException e) {
            log.info("执行错误"+parameters);
        } catch (InvocationTargetException e) {
            log.info("执行错误"+parameters);
        }
        return response;
    }
```

现在我们两个服务都启动起来并且在客户端进行调用就发现只是调用接口就能调用过来了。

## 总结

到现在一个简单的RPC就完成了，但是其中还有很多的功能需要完善，例如一个完整RPC框架肯定还需要服务注册与发现，而且双方通信肯定也不能是直接开启一个线程一直在等着，肯定需要是异步的等等的各种功能。后面随着学习的深入，这个框架也会慢慢增加一些东西。不仅是对所学知识的一个应用，更是一个总结。有时候学一个东西学起来觉得很简单，但是真正应用的时候就会发现各种各样的小问题。比如在写这个例子的时候碰到一个问题就是`@Autowired`的时候一直找不到`SendMessage`的类型，最后才发现是工厂类`RpcClinetFactoryBean `中的`getObjectType `中的返回类型写错了，我之前写的是

```
    public Class<?> getObjectType() {
        return this.getClass();;
    }

```

这样的话注册进容器的就是`RpcClinetFactoryBean `类型的而不是`SendMessage `的类型。


### [完整项目地址](https://github.com/modouxiansheng/SpringBoot-Practice)

