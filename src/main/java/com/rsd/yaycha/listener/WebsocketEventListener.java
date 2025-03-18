package com.rsd.yaycha.listener;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.rsd.yaycha.dto.ChatMessage;
import com.rsd.yaycha.enums.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebsocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
            .content("Hey Hey Hey Hey Thu Hwat Thur P")
            .type(MessageType.LEAVE)
            .sender(username)
            .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }else {
            log.info("User disconnected");
            var chatMessage = ChatMessage.builder()
            .content("Hey Hey Hey Hey Thu Hwat Thur P")
            .type(MessageType.LEAVE)
            .sender(username)
            .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}
