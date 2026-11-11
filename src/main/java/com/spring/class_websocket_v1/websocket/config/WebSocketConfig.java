package com.spring.class_websocket_v1.websocket.config;

import com.spring.class_websocket_v1.websocket.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSocket // 반드시 필수
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler; // DI 처리

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 허용 출처를 지정하지 않으면 같은 출처(같은 주소와 포트에서 열린 페이지)의 연결만 받는다.
        registry.addHandler(chatWebSocketHandler, "/ws-chat");
//                .setAllowedOrigins("*")
//                .setAllowedOrigins("http://localhost:5173")
//                .setAllowedOrigins("https://app.example.com");

        // 허용 출처
    }
}
