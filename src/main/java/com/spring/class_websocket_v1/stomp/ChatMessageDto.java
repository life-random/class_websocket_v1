package com.spring.class_websocket_v1.stomp;

/**
 * 브라우저와 주고받는 메시지 한 건 (JSON)
 * {"sender": "홍길동", "msg": "안녕"}
 *
 * record : 필드를 선언하면 생성자, 접근자, equals, toString 자동으로 만들어 주는 불변 객체
 */

public record ChatMessageDto(String sender, String msg) {}
