/*
 * File: WebMvcConfig.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-14
 */

package cn.feng.dev.spring.boot.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author fengtao.xue
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    static Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ws").setViewName("/ws");
    }
}
