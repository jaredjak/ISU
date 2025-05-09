package backendTables.UserModeration;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Users.User;
import backendTables.Users.UserRepository;
import backendTables.Chat.ChatMessage;
import backendTables.Chat.ChatMessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "User Report Management", description = "Endpoints for filing reports against users or messages, and retrieving submitted reports.")
@RestController
@RequestMapping("/reports")
public class UserReportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Operation(summary = "File a user or message report", description = "Submits a new report against either a user or a chat message. Prevents duplicate reports.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - missing or invalid fields"),
            @ApiResponse(responseCode = "409", description = "Conflict - duplicate report")
    })
    @PostMapping
    public ResponseEntity<?> report(
            @RequestParam String reportingUsername,
            @RequestParam(required = false) String reportedUsername,
            @RequestParam(required = false) Long messageId,
            @RequestParam(required = false) String explanation) {

        User reportingUser = userRepository.findByUsername(reportingUsername);
        if (reportingUser == null) {
            return ResponseEntity.badRequest().body("Reporting user not found");
        }

        if (reportedUsername == null && messageId == null) {
            return ResponseEntity.badRequest().body("Must report either a user or a message.");
        }

        User reportedUser = null;
        ChatMessage reportedMessage = null;

        if (reportedUsername != null) {
            reportedUser = userRepository.findByUsername(reportedUsername);
            if (reportedUser == null) {
                return ResponseEntity.badRequest().body("Reported user not found");
            }

            if (userReportRepository.existsByReportingUserAndReportedUser(reportingUser, reportedUser)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already reported this user.");
            }
        }

        if (messageId != null) {
            reportedMessage = chatMessageRepository.findById(messageId).orElse(null);
            if (reportedMessage == null) {
                return ResponseEntity.badRequest().body("Reported message not found");
            }

            if (userReportRepository.existsByReportingUserAndReportedMessage(reportingUser, reportedMessage)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already reported this message.");
            }
        }

        UserReport report = new UserReport(reportingUser, reportedUser, reportedMessage, explanation);
        userReportRepository.save(report);

        // After each new report, check to see if a new tier achievement is reached.
        Achievements a = achievementRepository.findByUsername(reportedUsername);
        a.increaseCount(7, 1);
        achievementRepository.save(a);

        return ResponseEntity.ok("Report submitted successfully.");
    }

    @Operation(summary = "Get all reports for a message", description = "Retrieves all reports filed against a specific message ID.")
    @ApiResponse(
            responseCode = "200",
            description = "Reports retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReport.class))
    )
    @GetMapping("/message/{messageId}")
    public ResponseEntity<?> getReportsByMessage(@PathVariable Long messageId) {
        List<UserReport> reports = userReportRepository.findByReportedMessageId(messageId);
        return ResponseEntity.ok(reports);
    }

    @Operation(summary = "Get all reports filed against a user", description = "Retrieves all reports filed against a specific user.")
    @ApiResponse(
            responseCode = "200",
            description = "Reports retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReport.class))
    )
    @GetMapping("/user-reports/{username}")
    public ResponseEntity<?> getReportsAgainstUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userReportRepository.findByReportedUser(user));
    }

    @Operation(summary = "Get all reports", description = "Retrieves every report filed in the system.")
    @ApiResponse(
            responseCode = "200",
            description = "All reports retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReport.class))
    )

    @GetMapping("/all")
    public ResponseEntity<?> getAllReports() {
        return ResponseEntity.ok(userReportRepository.findAll());
    }

    @Operation(summary = "Get reports filed by a user", description = "Retrieves all reports submitted by a specific user.")
    @ApiResponse(
            responseCode = "200",
            description = "Reports retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReport.class))
    )

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getReportsByUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        List<UserReport> reports = userReportRepository.findByReportingUser(user);
        return ResponseEntity.ok(reports);
    }
}
