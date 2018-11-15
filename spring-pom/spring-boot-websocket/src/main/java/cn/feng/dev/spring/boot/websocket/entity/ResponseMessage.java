/*
 * File: ResponseMessage.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-14
 */

package cn.feng.dev.spring.boot.websocket.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengtao.xue
 */
public class ResponseMessage {
    static Logger logger = LoggerFactory.getLogger(ResponseMessage.class);

    private String responseMessage;

    public ResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
