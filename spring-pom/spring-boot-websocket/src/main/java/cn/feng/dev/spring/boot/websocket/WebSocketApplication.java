/*
 * File: WebSocketApplication.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-14
 */

package cn.feng.dev.spring.boot.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author fengtao.xue
 */
@SpringBootApplication
public class WebSocketApplication {
    static Logger logger = LoggerFactory.getLogger(WebSocketApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }
}
