/*
 * File: AsyncTask.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-15
 */

package cn.feng.dev.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;


/**
 * @author fengtao.xue
 */
@Component
public class AsyncTask {
    static Logger logger = LoggerFactory.getLogger(AsyncTask.class);

    @Async
    public Future<Boolean> doTask(int i){
        try {
            Thread.sleep(5000);
            logger.debug("异步任务：" + i+"；Thread.currentThread().getName():"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(true);
    }

    @Async
    public void executeAsyncTask(int i) {
        try {
            Thread.sleep(5000);
            System.out.println("异步任务：" + i+"；Thread.currentThread().getName():"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
