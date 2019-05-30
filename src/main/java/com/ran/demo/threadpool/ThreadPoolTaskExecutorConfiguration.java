package com.ran.demo.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 使用 Spring 对 ThreadPoolExecutor 包装后的线程池类 ThreadPoolTaskExecutor
 *
 */
@Configuration
public class ThreadPoolTaskExecutorConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${threadpool.corePoolSize}")
    private int corePoolSize;

    @Value("${threadpool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${threadpool.queueCapacity}")
    private int queueCapacity;

    @Value("${threadpool.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Bean(name="threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        logger.info("create ThreadPoolTaskExecutor, corePoolsize:{}, maxPoolSize:{}, queueCapacity:{}, keepAliveSeconds:{}", corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(keepAliveSeconds);
        pool.setCorePoolSize(corePoolSize);//核心线程池数
        pool.setMaxPoolSize(maxPoolSize); // 最大线程
        pool.setQueueCapacity(queueCapacity);//队列容量
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy()); //队列满，线程被拒绝执行策略
        return pool;
    }

}
