/*
 * File: SpringContextUtil.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-21
 */

package cn.feng.dev.shiro.utls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * @author fengtao.xue
 */
@Service
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware {
    static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法。设置上下文环境
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     *
     * @param requiredType
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }
}
