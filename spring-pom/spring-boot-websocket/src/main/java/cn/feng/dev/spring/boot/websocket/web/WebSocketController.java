/*
 * File: WebSocketController.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2018-11-14
 */

package cn.feng.dev.spring.boot.websocket.web;

import cn.feng.dev.spring.boot.websocket.entity.RequestMessage;
import cn.feng.dev.spring.boot.websocket.entity.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


/**
 * @author fengtao.xue
 */
@Controller
public class WebSocketController {
    static Logger logger = LoggerFactory.getLogger(Controller.class);

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }
}
