/*
 * File: RequestMessage.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-14
 */

package cn.feng.dev.spring.boot.websocket.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class RequestMessage {
    static Logger logger = LoggerFactory.getLogger(RequestMessage.class);

    private String name;

    public String getName() {
        return name;
    }
}
