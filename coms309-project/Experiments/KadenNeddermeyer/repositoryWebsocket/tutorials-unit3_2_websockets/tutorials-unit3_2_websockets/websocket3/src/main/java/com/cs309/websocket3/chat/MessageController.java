package com.cs309.websocket3.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    // to be used by administrator view
    @GetMapping("/getMessages/{username}")
    List<Message> getUserMessages(@PathVariable String username) {
        return messageRepository.findByUsername(username);
    }


    @GetMapping("/getClubMessages/{id}")
    List<Message> getClubMessages(@PathVariable int id) {
        return messageRepository.findByClubId(id);
    }


}
