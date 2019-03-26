/*
 * File: WebSocketConfig.java
 * Created By: fengtao.xue@gausscode.com
 * Date: 2019-03-20
 */

package cn.feng.dev.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @author fengtao.xue
 */


@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
