package backendTables.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Find all messages sent by a specific user, ordered by most recent first
    List<ChatMessage> findBySenderUsernameOrderByTimestampDesc(String senderUsername);

    // Find all messages by sender (no sort)
    List<ChatMessage> findBySenderUsername(String senderUsername);

    // Find all messages sent to a specific club
    List<ChatMessage> findByClubId(int clubId);
}
