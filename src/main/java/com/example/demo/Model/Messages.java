package com.example.demo.Model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "chat_message")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long receiverId;
    private String msgContent;
     private String senderName; 

    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public Messages(Long senderId, Long receiverId, String msgContent, LocalDateTime timestamp, String senderName) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msgContent = msgContent;
        this.timestamp = timestamp;
        this.senderName = senderName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
public String getSenderName() {
        return senderName;  
}
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }       
    public Messages() {
    }

}
