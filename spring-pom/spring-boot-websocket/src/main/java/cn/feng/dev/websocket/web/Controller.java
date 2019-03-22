/*
 * File: Controller.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2019-03-20
 */

package cn.feng.dev.websocket.web;

import cn.feng.dev.websocket.entity.Greeting;
import cn.feng.dev.websocket.entity.HelloMessage;
import cn.feng.dev.websocket.service.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @author fengtao.xue
 */
@RestController
public class Controller {
    static Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    WebSocketServer webSocketServer;

    /*@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }*/


    /**
     * 群发消息内容
     *
     * @param message
     * @return
     */
    @RequestMapping(value = "/api/ws/sendAll", method = RequestMethod.GET)
    public String sendAllMessage(@RequestParam(required = true) String message) {
        try {
            webSocketServer.broadCastInfo(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    /**
     * 指定会话ID发消息
     *
     * @param message 消息内容
     * @param authKey      连接会话ID
     * @return
     */
    @RequestMapping(value = "/api/ws/sendOne", method = RequestMethod.GET)
    public String sendOneMessage(@RequestParam(required = true) String message,
                                 @RequestParam(required = true) String authKey,
                                 HttpSession session) {
        try {
            String sessionId = session.getId();
            webSocketServer.sendToUser(authKey, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
