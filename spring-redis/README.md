# 解决Redis集群条件下键空间通知服务器接收不到消息的问题

## 键空间通知介绍

> 键空间通知使得客户端可以通过订阅频道或模式， 来接收那些以某种方式改动了 Redis 数据集的事件。

可以通过对redis的`redis.conf`文件中配置`notify-keyspace-events`参数可以指定服务器发送哪种类型的通知。下面对于一些参数的描述。默认情况下此功能是关闭的。

|字符|通知|
|----|------|
|`K`|键空间通知，所有通知以 `__keyspace@<db>__` 为前缀|
|`E	`|键事件通知，所有通知以 `__keyevent@<db>__` 为前缀|
|`g	`|`DEL` 、 `EXPIRE` 、 `RENAME` 等类型无关的通用命令的通知|
|`$	`|字符串命令的通知|
|`l`|列表命令的通知|
|`s	`|集合命令的通知|
|`h`|哈希命令的通知|
|`z`|有序集合命令的通知|
|`x`|过期事件：每当有过期键被删除时发送|
|`e`|驱逐(evict)事件：每当有键因为 maxmemory 政策而被删除时发送|
|`A`|参数 `g$lshzxe` 的别名|

> 所以当你配置文件中配置为`AKE`时就表示发送所有类型的通知。

## 在程序中接入

使用`SpringData`可以轻松的实现对于redis键空间通知的接收操作。只需要作如下配置即可

所使用的jar包

```
'org.springframework.boot:spring-boot-starter-data-redis'

```

### 配置监听器

```
@Configuration
@ConditionalOnExpression("!'${spring.redis.host:}'.isEmpty()")
public static class RedisStandAloneAutoConfiguration {
    @Bean
    public RedisMessageListenerContainer customizeRedisListenerContainer(
            RedisConnectionFactory redisConnectionFactory,MessageListener messageListener) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListener,new PatternTopic("__keyspace@0__:*"));
        return redisMessageListenerContainer;
    }
}

```

其中`PatternTopic `构造器里面填写的是你所要监听哪一个通道。

例如在redis中执行`set blog buxuewushu`。我配置文件中配置的`AKE`所以所有消息都会发送，他就会发送两条信息。

```
PUBLISH __keyspace@0__:blog set
PUBLISH __keyevent@0__:set blog

```

所以我在上面配置的监听规则`__keyspace@0__:*`就是监听0号库发送的所有space信息都会接收到。

### 配置处理器

上面我们配置了监听Redis的哪条通道，现在我们需要配置接收到了信息以后如何处理的事情。所以此时我们需要在程序中写处理器

```

@Slf4j
@Component
public class KeyExpiredEventMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("监听失效的redisKey：{},值是:{}", new String(message.getChannel()), new String(message.getBody()));
    }
}

```

只需要实现`MessageListener `即可。我们只是将监听到的键和发送的信息打印出来。

### 效果展示

此时我们启动本地的redis，然后执行`set blog buxuewushu`命令，可以在程序中看到。下面的输出。即我们已经监听到了redis发送的消息了。

```
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog,值是:set

```

此时如果我们将规则变成`__key*__:*`那么会收到什么呢？还是执行`set blog buxuewushu`命令

```
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyevent@0__:set,值是:blog

```
我们看到执行一个set命令可以收到两个消息，一个是`space`消息一个是`event`消息。

### 集群条件下

我们刚才的测试都是在单机Redis下测试的，当将Redis转为集群模式时，会发现接收不到了消息了。此时我们启动本机的redis的集群。关于如何在本机利用docker一键部署集群可以参考我的一篇文章[Mac上最简单明了的利用Docker搭建Redis集群](http://modouxiansheng.top/2019/04/22/%E4%B8%8D%E5%AD%A6%E6%97%A0%E6%95%B0-Mac%E4%B8%8A%E6%9C%80%E7%AE%80%E5%8D%95%E6%98%8E%E4%BA%86%E7%9A%84%E5%88%A9%E7%94%A8Docker%E6%90%AD%E5%BB%BARedis%E9%9B%86%E7%BE%A4/)。启动完redis集群以后我们还是启动程序进行测试。

redis集群配置如下，监听规则改为如下

```
spring:
  redis:
    cluster:
      nodes:
      - 127.0.0.1:7000
      - 127.0.0.1:7001
      - 127.0.0.1:7002
      - 127.0.0.1:7003
      - 127.0.0.1:7004
      - 127.0.0.1:7005
```

我们redis中如下的命令

```
127.0.0.1:7002> set blog buxuwshu
-> Redirected to slot [7653] located at 127.0.0.1:7001
OK
127.0.0.1:7001> set blog1 buxuwshu
-> Redirected to slot [2090] located at 127.0.0.1:7000
OK
127.0.0.1:7000> set blog2 buxuwshu
-> Redirected to slot [14409] located at 127.0.0.1:7002
OK
127.0.0.1:7002> set blog3 buxuwshu
-> Redirected to slot [10344] located at 127.0.0.1:7001
OK
127.0.0.1:7001> set blog4 buxuwshu
OK
127.0.0.1:7001> set blog5 buxuwshu
-> Redirected to slot [2222] located at 127.0.0.1:7000
OK

```

在程序中打印如下

```
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog3,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog4,值是:set
```
我们看到只打印了`blog `、`blog1`、`blog4`的键，而我们通过上面观察，打印的键都是分布在7001端口上的。因此我们预测程序只是监听了7001端口发送的消息。而通过N次测试，程序不是每次都在监听7001端口，而是随机的。但是每次只会监听一个端口。


## 问题所在

接下来让我们通过找寻源码，看看到底是哪出的问题。

`JedisSlotBasedConnectionHandler`的`getConnection`方法中

```
public Jedis getConnection() {
    // In antirez's redis-rb-cluster implementation,
    // getRandomConnection always return valid connection (able to
    // ping-pong)
    // or exception if all connections are invalid

    List<JedisPool> pools = cache.getShuffledNodesPool();

    for (JedisPool pool : pools) {
      Jedis jedis = null;
      try {
        jedis = pool.getResource();

        if (jedis == null) {
          continue;
        }

        String result = jedis.ping();

        if (result.equalsIgnoreCase("pong")) return jedis;

        jedis.close();
      } catch (JedisException ex) {
        if (jedis != null) {
          jedis.close();
        }
      }
    }
    throw new JedisNoReachableClusterNodeException("No reachable node in cluster");
  }

```

可以看到注释中写着会获得一个随机的有效连接。也可以通过代码看到，获得连接池的信息以后遍历，直到有一个信息能够`ping-pong`通就直接返回此连接进行监听。而Redis的消息发送是在本地发送的。因此默认只能监听到集群中一台机器发送的消息。

> 本地发送解释：例如有三个主机01，02，03。此时如果有个set键`buxuewushu`落到了主机01上，那么此消息就会通过01这台主机发送，因此如果此时服务监听的02机器，那么这个消息就会监听不到。

![本地发送](https://ws2.sinaimg.cn/large/006tNc79ly1g2icj2zhiuj317c0k446b.jpg)

## 解决办法

既然我们知道了在集群条件下，每次监听只会随机取一个端口进行监听。那么我们就自己写监听机制，监听集群条件下的所有主机的端口就行了。

我们可以看到在`SpringData`中提供了`RedisMessageListenerContainer`类来与Redis服务器进行通信。
此类中有个`start`方法，可以看到是建立了与Redis的异步通信操作。所以我们的改造点就放在这就行。思路如下。

* 程序启动时，获得集群的配置信息
* 根据集群配置的`Master`数配置相同的`RedisMessageListenerContainer `进行监听

主要代码如下

```
 public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        RedisClusterConnection redisClusterConnection = redisConnectionFactory.getClusterConnection();
        if (redisClusterConnection != null) {
            Iterable<RedisClusterNode> nodes = redisClusterConnection.clusterGetNodes();
            for (RedisClusterNode node : nodes) {
                if (node.isMaster()) {
                    String containerBeanName = "messageContainer" + node.hashCode();
                    if (beanFactory.containsBean(containerBeanName)) {
                        return;
                    }
                    JedisConnectionFactory factory = new JedisConnectionFactory(
                            new JedisShardInfo(node.getHost(), node.getPort()));
                    BeanDefinitionBuilder containerBeanDefinitionBuilder = BeanDefinitionBuilder
                            .genericBeanDefinition(RedisMessageListenerContainer.class);
                    containerBeanDefinitionBuilder.addPropertyValue("connectionFactory", factory);
                    containerBeanDefinitionBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
                    containerBeanDefinitionBuilder.setLazyInit(false);
                    beanFactory.registerBeanDefinition(containerBeanName,
                            containerBeanDefinitionBuilder.getRawBeanDefinition());

                    RedisMessageListenerContainer container = beanFactory
                            .getBean(containerBeanName, RedisMessageListenerContainer.class);
                    String listenerBeanName = "messageListener" + node.hashCode();
                    if (beanFactory.containsBean(listenerBeanName)) {
                        return;
                    }
                    container.addMessageListener(messageListener, new PatternTopic("__key*__:*"));
                    container.start();
                }
            }
        }
    }

```

此时我们再启动程序，还是在Redis中如下的输入

```
127.0.0.1:7002> set blog0 buxuewushu
-> Redirected to slot [6155] located at 127.0.0.1:7001
OK
127.0.0.1:7001> set blog1 buxuewushu
-> Redirected to slot [2090] located at 127.0.0.1:7000
OK
127.0.0.1:7000> set blog2 buxuewushu
-> Redirected to slot [14409] located at 127.0.0.1:7002
OK
127.0.0.1:7002> set blog3 buxuewushu
-> Redirected to slot [10344] located at 127.0.0.1:7001
OK
127.0.0.1:7001> set blog4 buxuewushu
OK
127.0.0.1:7001> set blog5 buxuewushu
-> Redirected to slot [2222] located at 127.0.0.1:7000
OK

```

这时我们可以看到在程序中我们接收到了所有端口的信息了。

```
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog0,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog1,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog2,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog3,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog4,值是:set
c.e.s.r.KeyExpiredEventMessageListener   : 监听到的信息：__keyspace@0__:blog5,值是:set

```

此时相当于我们建立了三个连接来监听三个redis服务器发送的消息。

![](https://ws3.sinaimg.cn/large/006tNc79ly1g2idg9hef3j317u0k2ai2.jpg)

> 小贴士：模式能匹配通配符，例如`__keyspace@0__:blog*`表示只接收blog开头的key值的信息，其他key值信息不接收


### [完整代码](https://github.com/modouxiansheng/SpringBoot-Practice/tree/master/spring-redis)