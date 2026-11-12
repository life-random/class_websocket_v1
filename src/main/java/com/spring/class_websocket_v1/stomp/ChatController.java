package com.spring.class_websocket_v1.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    // 서버 어디서든 특정 주소 (/sub/...)를 구독한 브라우저에게 메시지를 보낼 수 있게 해주는 도구
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    // 첫 화면 : 자동으로 1번 방으로 보낸다.
    @GetMapping("/")
    public String home() {
        return "redirect:/rooms/1";
    }

    @GetMapping("/rooms/{roomId}")
    public String room(@PathVariable("roomId") Integer roomId, Model model) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("chats", chatService.findByRoomId(roomId));
        return "index";
    }

    /**
     * 채팅 메시지 수신 (STOMP)
     * 이 메서드는 URL로 불리지 않는다
     * 브라우저는 ws://localhost:8080/ws-stomp 로 연결을 한 번 열어 두고,
     * 그 연결 안으로 destination:/pub/chat/{1}이 적힌 SEND 프렘을 보낸다
     * 스프링 /pub을 떼고 남은 chat/1 을 보고 메서드를 찾는다.
     */
    @MessageMapping("/chat/{roomId}")
    public void sendMessage(@DestinationVariable("roomId") Integer roomId, ChatMessageDto request) {
        log.info("수신 : {} 번 방, {}", roomId, request);
        // 빈 메시지는 거른다 (브라우저 검사는 우회할 수 있으므로 서버에서도 확인한다)
        if (request.msg() == null || request.msg().isBlank()){
            return;
        }

        // 이름이 비어 있으면 "익명"으로 둔다.
        String sender = request.sender() == null || request.sender().isBlank() ? "익명" : request.sender().trim();

        Chat chat = chatService.save(roomId, sender, request.msg().trim());

        // 중요 (메세지 브로커에게 전달)
        // 3편에서 만들었던 직접 작성ㅎ나 broadcast() 반복문이 이 한줄로 끝났다
        simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId,
                new ChatMessageDto(chat.getSender(), chat.getMsg()));
    }
}
