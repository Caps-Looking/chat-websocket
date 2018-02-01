package com.websocket.tester.websockettester.controllers;

import com.websocket.tester.websockettester.models.dtos.MessageDTO;
import com.websocket.tester.websockettester.models.entities.Message;
import com.websocket.tester.websockettester.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageHandlingController {

    @Autowired
    ChatService chatService;

    @MessageMapping({"/chat/{channelId}"})
    @SendTo("/topic/messages/{channelId}")
    public Message send(@Payload MessageDTO messageDTO) {
        return chatService.saveMessage(messageDTO);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("v1/chat/{id}")
    public void verifyChatExistency(@PathVariable String id) {
        chatService.verifyChatExistency(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("v1/chat/getLatestMessages/{id}")
    public List<Message> getLatestMessages(@PathVariable String id) {
        return chatService.getLatestMessages(id);
    }

}
