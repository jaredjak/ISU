package com.example.androidexample;

/* Basic helper class for storing the information needed for the Admin Chat RecyclerView */
public class ChatMessage {
    private String message;
    private String senderUsername;
    private String timestamp;
    private Integer id;

    public ChatMessage(String message, String senderUsername, String timestamp, Integer id) {
        this.message = message;
        this.senderUsername = senderUsername;
        this.timestamp = timestamp;
        this.id = id;
    }

    // Getters
    public String getMessage() { return message; }
    public String getSenderUsername() { return senderUsername; }
    public String getTimestamp() { return timestamp; }
    public Integer getId() { return id; }
}
