/*
 * File: WebSocketServer.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2019-03-20
 */

package cn.feng.dev.websocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 * @author fengtao.xue
 */
@ServerEndpoint("/websocket/{authKey}")
@Component
public class WebSocketServer {
    static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<String,WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session WebSocketsession;
    //当前发消息的人员authKey
    private String authKey = "";


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam(value = "authKey") String param, Session WebSocketsession, EndpointConfig config) {
        authKey = param;
        //log.info("authKey:{}",authKey);
        this.WebSocketsession = WebSocketsession;
        webSocketSet.put(param, this);//加入map中
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        sendMessage(this.WebSocketsession, "连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        if (!authKey.equals("")){
            webSocketSet.remove(authKey);//从set中删除
            int cnt = OnlineCount.decrementAndGet();
            log.info("有连接关闭，当前连接数为：{}", cnt);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}",message);
        sendMessage(session, "收到消息，消息内容："+message);
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param message
     */
    public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)",message,session.getId()));
            //session.getBasicRemote().sendText(String.format("%s",message));
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public void broadCastInfo(String message) throws IOException {
        for (String key : webSocketSet.keySet()) {
            Session session = webSocketSet.get(key).WebSocketsession;
            if(session != null && session.isOpen()){
                sendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     * @param message
     * @throws IOException
     */
    public void sendToUser(String authKey, String message) throws IOException {
        WebSocketServer webSocketServer = webSocketSet.get(authKey);
        if ( webSocketServer != null && webSocketServer.WebSocketsession.isOpen()){
            sendMessage(webSocketServer.WebSocketsession, message);
        }
        else{
            log.warn("当前用户不在线：{}",authKey);
        }
    }
}
