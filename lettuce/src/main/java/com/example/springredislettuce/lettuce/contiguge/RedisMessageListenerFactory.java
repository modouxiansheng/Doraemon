package com.example.springredislettuce.lettuce.contiguge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


@Slf4j
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
                    BeanDefinitionBuilder containerBeanDefinitionBuilder = BeanDefinitionBuilder
                            .genericBeanDefinition(RedisMessageListenerContainer.class);
                    //设置单机监听主节点的配置
                    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(node.getHost(),node.getPort());
                    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
                    lettuceConnectionFactory.afterPropertiesSet();
                    containerBeanDefinitionBuilder.addPropertyValue("connectionFactory", lettuceConnectionFactory);
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
                    container.addMessageListener(messageListener, new PatternTopic("__keyspace@0__:*"));
                    container.start();
                }
            }
        }
    }
}