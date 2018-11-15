/*
 * File: TaskExacutorConfig.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-15
 */

package cn.feng.dev.spring.examples.taskExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * @author fengtao.xue
 */
@Configuration
@ComponentScan
@EnableAsync//开启异步任务支持
public class TaskExacutorConfig implements AsyncConfigurer {
    static Logger logger = LoggerFactory.getLogger(TaskExacutorConfig.class);

    //返回一个线程池
    public Executor getAsyncExecutor() {
        //创建并配置线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.initialize();
        return taskExecutor;
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
