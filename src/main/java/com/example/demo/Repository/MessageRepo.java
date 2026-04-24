package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Messages;

@Repository
public interface MessageRepo extends JpaRepository<Messages, Long> {

    // 👉 Chat between user & admin (both direction)
    @Query("SELECT c FROM Messages c WHERE " +
       "(c.senderId = :userId AND c.receiverId = :adminId) OR " +
       "(c.senderId = :adminId AND c.receiverId = :userId) " +
       "ORDER BY c.timestamp ASC")
List<Messages> getChat(@Param("userId") Long userId,
                   @Param("adminId") Long adminId);

    //👉 Admin ke liye user list (jin users ne message kiya)
    @Query("SELECT DISTINCT m.senderId FROM Messages m WHERE m.receiverId = :adminId AND m.senderId != :adminId")
    List<Long> findUsersWhoMessagedAdmin(@Param("adminId") Long adminId);
}


