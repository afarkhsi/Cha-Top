package com.chatop_back.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop_back.api.payload.request.MessageRequest;
import com.chatop_back.api.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Message send with success");
        
        return ResponseEntity.ok(response);
    }
}
