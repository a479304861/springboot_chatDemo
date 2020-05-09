package com.demo1.springboottest.config;

import com.demo1.springboottest.websocket.SpringWebSocketHandler;
import com.demo1.springboottest.websocket.SpringWebSocketHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SpringWebSocketConfig  implements WebSocketConfigurer {


    @Bean
    public SpringWebSocketHandler getSpringWebSocketHandler() {
        return new SpringWebSocketHandler();
    }

    @Bean
    public SpringWebSocketHandlerInterceptor getInterceptor() {
        return new SpringWebSocketHandlerInterceptor();

    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getSpringWebSocketHandler(), "/websocket/server")
                .addInterceptors(getInterceptor()).setAllowedOrigins("*");

        registry.addHandler(getSpringWebSocketHandler(), "/sockjs/server").setAllowedOrigins("*")
                .addInterceptors(getInterceptor()).withSockJS();

    }
}
