package backendTables.Chat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages") // optional: rename for clarity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderUsername;

    private String message;

    private LocalDateTime timestamp;

    private int serverId;

    private int clubId;

    public ChatMessage() {}

    public ChatMessage(String senderUsername, String message, LocalDateTime timestamp) {
        this.senderUsername = senderUsername;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ChatMessage(String senderUsername, String message, LocalDateTime timestamp, int clubId) {
        this.senderUsername = senderUsername;
        this.message = message;
        this.timestamp = timestamp;
        this.clubId = clubId;
    }

    public Long getId() {
        return id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }
}
