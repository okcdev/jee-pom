/*
 * File: Main.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-15
 */

package cn.feng.dev.spring.examples.taskExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @author fengtao.xue
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExacutorConfig.class);

        AsyncTask asyncTask = context.getBean(AsyncTask.class);

        for (int i = 1; i <= 20; i++){
            asyncTask.doTask(i);
        }
    }
}
