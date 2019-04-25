package com.example.springredis.redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import redis.clients.jedis.JedisShardInfo;

public class RedisMessageListenerFactory implements BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {

    private DefaultListableBeanFactory beanFactory;

    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private MessageListener messageListener;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

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
                    container.addMessageListener(messageListener, new PatternTopic("__keyspace@0__:hu*"));
                    container.start();
                }
            }
        }
    }
}