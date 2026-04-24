package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Messages;
import com.example.demo.Model.DTO.chatuserDTO;
import com.example.demo.Repository.MessageRepo;
import com.example.demo.Service.MessageService;
@RestController
@RequestMapping("/api/chat")
public class MessageController {
    private final MessageService messageService;
    private final MessageRepo messageRepo;
    public MessageController(MessageService messageService, MessageRepo messageRepo) {
        this.messageService = messageService;
        this.messageRepo = messageRepo;
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Messages message) {
        message.setTimestamp(LocalDateTime.now());
        message.setMsgContent(message.getMsgContent().trim());
        Messages savedMsg = messageService.sendMessage(message); // 🔥 IMPORTANT LINE

    return ResponseEntity.ok(savedMsg);
    } 
    
    @GetMapping("/getchat")
    public List<Messages> getChat(@RequestParam Long userId, @RequestParam Long adminId) {
        return messageService.getChat(userId, adminId);
    }
  @GetMapping("/chat-users")
public List<chatuserDTO> getChatUsers(@RequestParam Long adminId) {
    return messageService.getChatUsers(adminId);
}
}
