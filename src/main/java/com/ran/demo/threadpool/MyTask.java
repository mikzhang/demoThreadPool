package com.ran.demo.threadpool;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class MyTask implements Runnable{
    private String threadName;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        logger.info("thread:{} is running...", threadName);
        try {
            Thread.sleep(1000l);//让任务执行慢些, 以便突出测试效果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
