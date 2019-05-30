package com.ran.demo.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolExecutorConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${threadpool.corePoolSize}")
    private int corePoolSize;

    @Value("${threadpool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${threadpool.queueCapacity}")
    private int queueCapacity;

    @Value("${threadpool.keepAliveSeconds}")
    private long keepAliveSeconds;

    @Bean(name="threadPoolExecutor")
    public ThreadPoolExecutor threadPoolTaskExecutor(){
        logger.info("create ThreadPoolExecutor, corePoolsize:{}, maxPoolSize:{}, queueCapacity:{}, keepAliveSeconds:{}", corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueCapacity));
        return pool;
    }

}
