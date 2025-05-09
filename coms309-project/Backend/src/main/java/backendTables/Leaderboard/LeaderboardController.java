package backendTables.Leaderboard;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 *
 * @author Carter Hauschildt
 *
 */

@Tag(name = "Leaderboard Management", description = "Endpoints for viewing, updating, creating, and deleting leaderboard entries for users.")
@RestController
public class LeaderboardController {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private AchievementRepository achievementRepository;


    @Operation(summary = "Get Top 10 Players by Score", description = "Retrieves the top 10 users sorted by score in descending order.")
    @ApiResponse(
            responseCode = "200",
            description = "Top 10 players retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Leaderboard.class))
    )
    // Retrieve the top 10 users by score (descending)
    @GetMapping("/leaderboard/leaders")
    public List<Leaderboard> getTop10Leaderboard() {
        return leaderboardRepository.findTop10ByOrderByScoreDesc();
    }


    @Operation(summary = "Get Bottom 10 Players by Score", description = "Retrieves the bottom 10 users sorted by score in ascending order.")
    @ApiResponse(
            responseCode = "200",
            description = "Bottom 10 players retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Leaderboard.class))
    )
    @GetMapping("/leaderboard/wall-of-shame")
    public List<Leaderboard> getBottom10Leaderboard() {
        return leaderboardRepository.findBottom10ByOrderByScoreAsc();
    }

    @Operation(summary = "Get User's Leaderboard Rank", description = "Retrieves a specific user's rank based on their score.")
    @ApiResponse(
            responseCode = "200",
            description = "User rank retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "integer"))
    )
    @GetMapping("/leaderboard/rank/{username}")
    public ResponseEntity<?> getUserRank(@PathVariable String username) {
        Leaderboard entry = leaderboardRepository.findByUserUsername(username);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        int score = entry.getScore();
        int rank = leaderboardRepository.getRankForUser(score);
        return ResponseEntity.ok(rank);
    }

    @Operation(summary = "Update a User's Leaderboard Entry", description = "Updates the score and win streak of an existing leaderboard entry using the username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leaderboard entry updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/leaderboard/{username}")
    public ResponseEntity<?> updateLeaderboardEntry(@PathVariable String username, @RequestBody Leaderboard updatedEntry) {
        Leaderboard entry = leaderboardRepository.findByUserUsername(username);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        entry.setScore(updatedEntry.getScore());
        entry.setWinStreak(updatedEntry.getWinStreak());

        leaderboardRepository.save(entry);
        return ResponseEntity.ok(entry);
    }

    @Operation(summary = "Create New Leaderboard Entry", description = "Creates a new leaderboard entry for a user if one doesn't already exist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leaderboard entry created successfully"),
            @ApiResponse(responseCode = "409", description = "Leaderboard entry for user already exists")
    })
    @PostMapping("/leaderboard")
    public ResponseEntity<?> createLeaderboardEntry(@RequestBody Leaderboard newEntry) {
        Leaderboard existingEntry = leaderboardRepository.findByUserId(newEntry.getUser().getId());
        if (existingEntry != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Leaderboard entry for this user already exists.");
        }
        Leaderboard createdEntry = leaderboardRepository.save(newEntry);
        return ResponseEntity.ok(createdEntry);
    }

    @Operation(summary = "Delete Leaderboard Entry by Username", description = "Deletes a leaderboard entry associated with the given username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leaderboard entry deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/leaderboard/{username}")
    public ResponseEntity<?> deleteLeaderboardEntry(@PathVariable String username) {
        Leaderboard entry = leaderboardRepository.findByUserUsername(username);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        leaderboardRepository.delete(entry);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Top 10 Players by Win Streak", description = "Retrieves the top 10 users sorted by win streak in descending order.")
    @ApiResponse(
            responseCode = "200",
            description = "Top 10 players by win streak retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Leaderboard.class))
    )

    @GetMapping("/leaderboard/hall-of-fame")
    public List<Leaderboard> getLeaderboardWinStreak() {
        return leaderboardRepository.findTop10ByOrderByWinStreakDesc();
    }
}
