package backendTables.Leaderboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 
 * @author Carter Hauschildt
 * 
 */ 

public interface LeaderboardRepository extends JpaRepository<backendTables.Leaderboard.Leaderboard, Long> {

    // Finds a leaderboard entry based on the associated user's id.
    Leaderboard findByUserId(int userId);

    // Finds a leaderboard entry based on the associated user's username.
    Leaderboard findByUserUsername(String username);

    // Returns the rank for a given score.
    @Query("SELECT COUNT(l) + 1 FROM Leaderboard l WHERE l.score > :score")
    int getRankForUser(@Param("score") int score);

    // Finds the top 10 users with the highest scores
    List<Leaderboard> findTop10ByOrderByScoreDesc();

    //finds the bottom 10 users with the lowest scores
    List<Leaderboard> findBottom10ByOrderByScoreAsc();

    // Finds the top 10 users with the highest win streak.
    List<Leaderboard> findTop10ByOrderByWinStreakDesc();
}
