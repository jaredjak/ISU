package backendTables.UserModeration;

import javax.persistence.*;
import backendTables.Users.User;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class UserModeration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private boolean isAdmin;
    private boolean isBanned;
    private LocalDateTime suspendedUntil;

    @Enumerated(EnumType.STRING)
    private ModerationStatus reportStatus = ModerationStatus.PENDING_REVIEW;

    public UserModeration(User user, boolean isAdmin, boolean isBanned, LocalDateTime suspendedUntil) {
        this.user = user;
        this.isAdmin = isAdmin;
        this.isBanned = isBanned;
        this.suspendedUntil = suspendedUntil;
        this.reportStatus = ModerationStatus.PENDING_REVIEW;
    }

    public UserModeration(User user) {
        this(user, false, false, null); // calls the 4-arg constructor with defaults
    }

    public UserModeration() {

    }

    // Utility method
    public boolean isSuspended() {
        return suspendedUntil != null && LocalDateTime.now().isBefore(suspendedUntil);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public LocalDateTime getSuspendedUntil() {
        return suspendedUntil;
    }

    public void setSuspendedUntil(LocalDateTime suspendedUntil) {
        this.suspendedUntil = suspendedUntil;
    }

    public ModerationStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ModerationStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

}
