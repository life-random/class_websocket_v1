package com.spring.class_websocket_v1.stomp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * STOMP 설정
 * 순수 웹소켓 코드에서 직접 만들었던 웹소켓 세션과 여러 관릴를 여기 메시지 브로커가 대신한다
 *
 * 주소 3자기
 * ws://localhost:8080/ws-stomp : 브라우저가 WebSocket 연결을 여는 주소가 됨
 *
 * /pub/... : 브라우저 -> 서버 (@MassageMapping 을 활용해 주소를 받는다)
 * /sub/... : 서버 -> 브라우저 보낼 때 씀 (브로커가 이 주소를 구독한 모두에게 전송한다)
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker   // 메시지 브로커 설정 ( STOMP 사용시 활용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 1. 연결 주소
     * 브라누저는 ws://localhost:8080/ws-stomp로 WebSocket 연결을 연다
     * 3편 처럼 허용 출처를 적지 않으면 같은 화면에 같은 출처에서 연결만 받는다
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp");
    }

    /**
     * 2. 주소 규칙 설정하는 부분
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /pub으로 시작하는 메시지가 왔을 때 @MessageMapping 가 붙은 컨트롤러로 메서드로 보낸다
        registry.setApplicationDestinationPrefixes("/pub");

        // /sub 으로 시작하는 주소는 스프링 내장(메모리 _ 브로커가 구독자에 알아서 배달한다
        registry.enableSimpleBroker("/sub");
    }
}
