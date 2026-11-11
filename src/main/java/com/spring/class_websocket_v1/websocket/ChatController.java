package com.spring.class_websocket_v1.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    // 채팅 목록 화면 (폴링)
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("chats", chatService.findAll());
        return "index";
    }

}
