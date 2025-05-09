package backendTables.UserModeration;

import backendTables.Users.User;
import backendTables.Chat.ChatMessage;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"reporting_user_id", "reported_message_id"})
)
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who submitted the report
    @ManyToOne
    @JoinColumn(name = "reporting_user_id", nullable = false)
    private User reportingUser;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    // The message that was reported
    @ManyToOne
    @JoinColumn(name = "reported_message_id", nullable = true)
    private ChatMessage reportedMessage;

    // Optional explanation from the reporter
    private String explanation;

    // Timestamp for when the report was submitted
    private LocalDateTime timestamp;

    // === Constructors ===

    public UserReport() {}

    public UserReport(User reportingUser, User reportedUser, ChatMessage reportedMessage, String explanation) {
        this.reportingUser = reportingUser;
        this.reportedUser = reportedUser;
        this.reportedMessage = reportedMessage;
        this.explanation = explanation;
        this.timestamp = LocalDateTime.now();
    }

    // === Getters and Setters ===

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReportingUser() {
        return reportingUser;
    }

    public void setReportingUser(User reportingUser) {
        this.reportingUser = reportingUser;
    }

    public ChatMessage getReportedMessage() {
        return reportedMessage;
    }

    public void setReportedMessage(ChatMessage reportedMessage) {
        this.reportedMessage = reportedMessage;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
