package com.cs309.websocket3.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findByUsername(String username);
    List<Message> findByClubId(int clubId);
}
