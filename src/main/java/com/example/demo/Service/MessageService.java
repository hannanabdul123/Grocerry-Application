package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Model.Messages;
import com.example.demo.Model.User;
import com.example.demo.Model.DTO.chatuserDTO;
import com.example.demo.Repository.MessageRepo;
import com.example.demo.Repository.UserRepository;

@Service
public class MessageService {

    private final MessageRepo messageRepo;
    private final UserRepository userRepository;

    public MessageService(MessageRepo messageRepo, UserRepository userRepository) {
        this.messageRepo = messageRepo;
        this.userRepository = userRepository;
    }

    // Send Message
    public Messages sendMessage(Messages message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepo.save(message);
    }

    // Get Chat between user & admin
    public List<Messages> getChat(Long userId, Long adminId) {
        List<Messages> chat = messageRepo.getChat(userId, adminId);
        for (Messages m : chat) {
            if (m.getSenderId().equals(adminId)) {
                m.setSenderName("Admin");
            } else {
                User sender = userRepository.findById(m.getSenderId()).orElse(null);
                m.setSenderName(sender != null ? sender.getName() : "Unknown User");
            }
        }
        return chat;
    }

    // Get Users who messaged admin
    public List<chatuserDTO> getChatUsers(Long adminId) {
        List<Long> userIds = messageRepo.findUsersWhoMessagedAdmin(adminId);

        return userIds.stream().map(id -> {
            User user = userRepository.findById(id).orElse(null);
            return new chatuserDTO(id, user.getName());
        }).collect(Collectors.toList());

    }
}
