package backendTables.UserModeration;

import backendTables.Users.User;
import backendTables.Chat.ChatMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    // Find all reports filed by a specific user
    List<UserReport> findByReportingUser(User user);

    List<UserReport> findByReportedUser(User user);

    // Find all reports associated with a specific message
    List<UserReport> findByReportedMessage(ChatMessage message);

    // Find all reports for a given message ID
    List<UserReport> findByReportedMessageId(Long messageId);

    // Check if a user has already reported a specific message
    boolean existsByReportingUserAndReportedMessage(User user, ChatMessage message);

    boolean existsByReportingUserAndReportedUser(User reportingUser, User reportedUser);

}
