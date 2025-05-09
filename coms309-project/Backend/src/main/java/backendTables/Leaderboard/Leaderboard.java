package backendTables.Leaderboard;

import javax.persistence.*;
import backendTables.Users.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author Carter Hauschildt
 *
 */

@Entity
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Associate each leaderboard entry with a single user.
    // The @OnDelete annotation ensures that if the user is deleted,
    // the leaderboard entry will also be deleted at the database level.
    @OneToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // Score for the user
    private int score;

    // Win streak for the user
    private int winStreak;

    // Default constructor
    public Leaderboard() { }

    // Constructor to initialize with a user, score, and win streak
    public Leaderboard(User user, int score, int winStreak) {
        this.user = user;
        this.score = score;
        this.winStreak = winStreak;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWinStreak() {
        return winStreak;
    }

    public void setWinStreak(int winStreak) {
        this.winStreak = winStreak;
    }

}