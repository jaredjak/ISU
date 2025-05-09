package backendTables.Chat;

import backendTables.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ChatMessageService {

    @Autowired
    ChatMessageRepository repo;
    @Autowired UserRepository userRepository;

    // Save a new message
    public void saveMessage(String sender, String message, int id) {
        ChatMessage msg = new ChatMessage(sender, message, LocalDateTime.now(), id);
        repo.save(msg);
    }

    // Get messages by user, sorted newest first
    public List<ChatMessage> getMessagesByUser(String username) {
        return repo.findBySenderUsernameOrderByTimestampDesc(username);
    }

    // Get all messages sent by a user (unsorted)
    public List<ChatMessage> getRawMessagesByUser(String username) {
        return repo.findBySenderUsername(username);
    }
    public List<ChatMessage> getRawMessagesByUser(String username, int id) {
        List<ChatMessage> messages = repo.findBySenderUsername(username);
        for (int i = messages.size()-1; i >= 0; i--) {
            if (messages.get(i).getClubId() != id) {
                messages.remove(i);
            }
        }
        return messages;
    }

    // Method to broadcast the message
    public void broadcast(String message, int id, Map<Session, String> sessionUsernameMap) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                if (userRepository.findByUsername(username).getClubId() == id) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Get messages for a specific club
    public List<ChatMessage> getMessagesByClubId(int clubId) {
        return repo.findByClubId(clubId);
    }
}
