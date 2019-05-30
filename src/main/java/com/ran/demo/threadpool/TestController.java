package com.ran.demo.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor exec;

    @Autowired
    @Qualifier("threadPoolExecutor")
    private ThreadPoolExecutor exec2;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        //监控放在最前面, 若放置在 exec.submit 后, 会错过开始的很多任务执行情况的监控
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> logExec());
        CompletableFuture cf2 = CompletableFuture.supplyAsync(() -> logExec2());

        for (int i=0; i<5000; i++){
            MyTask t = new MyTask();
            t.setThreadName(i+"");
            exec.submit(t);
//            exec2.submit(t);
        }
        return "OK";
    }

    /**
     * 随机向线程池添加任务
     *
     * @param threads
     * @return
     */
    @RequestMapping(value = "/test/add")
    @ResponseBody
    public String testAdd(
        @RequestParam(value = "threads", required = false) int threads
    ){
        for (int i=0; i<threads; i++){
            MyTask t = new MyTask();
            t.setThreadName("added"+i);
            exec.submit(t);
//            exec2.submit(t);
        }
        return "OK";
    }

    public boolean logExec() {
        for (;;) {
            logger.info("corePoolSize:{}, maxPoolSize:{}, poolSize:{}, activeCount:{}", exec.getCorePoolSize(), exec.getMaxPoolSize(), exec.getPoolSize(), exec.getActiveCount());
            try {
                Thread.sleep(200l);//暂停200ms, 避免太频繁的记录相同指标的日志
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean logExec2() {
        for (;;) {
            int corePoolSize = exec2.getCorePoolSize();
            int maximumPoolSize = exec2.getMaximumPoolSize();
            int poolSize = exec2.getPoolSize();
            int largestPoolSize = exec2.getLargestPoolSize();
            long taskCount = exec2.getTaskCount();
            long completedTaskCount = exec2.getCompletedTaskCount();
            int activeCount = exec2.getActiveCount();
            int queueSize = exec2.getQueue().size();
            logger.info("corePoolSize:{}, maximumPoolSize:{}, poolSize:{}, largestPoolSize:{}, taskCount:{}, completedTaskCount:{}, activeCount:{}, queueSize:{}",
                corePoolSize, maximumPoolSize, poolSize, largestPoolSize, taskCount,completedTaskCount, activeCount, queueSize);
            try {
                Thread.sleep(200l);//暂停200ms, 避免太频繁的记录相同指标的日志
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
