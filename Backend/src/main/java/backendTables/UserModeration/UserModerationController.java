package backendTables.UserModeration;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Chat.*;
import backendTables.Users.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Tag(name = "User Moderation", description = "Endpoints for banning, suspending, promoting users and checking user moderation status.")
@RestController
@RequestMapping("/admin")
public class UserModerationController {

    @Autowired
    private UserModerationRepository moderationRepo;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private AchievementRepository achievementRepository;

    @Operation(summary = "Ban a user", description = "Permanently bans the specified user and broadcasts a ban message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User banned successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/ban/{username}")
    public ResponseEntity<?> banUser(@PathVariable String username) {
        UserModeration mod = moderationRepo.findByUserUsername(username);
        int ssn = userRepository.findByUsername(username).getSSN();
        if (mod == null) return ResponseEntity.notFound().build();

        ChatMessage msg = new ChatMessage("God", "User " + username + " has been banned. Here is their SSN: " + ssn, LocalDateTime.now());
        msg.setClubId(0);
        chatMessageService.broadcast("God: " + msg.getMessage(), 0, ChatSocket.getSessionUsernameMap());
        chatMessageRepository.save(msg);

        mod.setBanned(true);
        moderationRepo.save(mod);
        return ResponseEntity.ok("User banned.");
    }


    @Operation(summary = "Suspend a user", description = "Temporarily suspends a user from playing for a given number of minutes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User suspended successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/suspend/{username}/{minutes}")
    public ResponseEntity<?> suspendUser(@PathVariable String username, @PathVariable long minutes) {
        UserModeration mod = moderationRepo.findByUserUsername(username);
        if (mod == null) return ResponseEntity.notFound().build();

        mod.setSuspendedUntil(LocalDateTime.now().plus(Duration.ofMinutes(minutes)));
        moderationRepo.save(mod);
        return ResponseEntity.ok("User suspended for " + minutes + " minutes.");
    }

    @Operation(summary = "Promote a user to admin", description = "Grants administrative privileges to a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User promoted to admin successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/promote/{username}")
    public ResponseEntity<?> promoteToAdmin(@PathVariable String username) {
        UserModeration mod = moderationRepo.findByUserUsername(username);
        if (mod == null) return ResponseEntity.notFound().build();

        mod.setAdmin(true);

        // Since this user is now an admin, give them the achievement for this
        Achievements a = achievementRepository.findByUsername(username);
        a.increaseCount(13, 1);
        achievementRepository.save(a);

        moderationRepo.save(mod);
        return ResponseEntity.ok("User promoted to admin.");
    }

    @Operation(summary = "Get a user's moderation status", description = "Retrieves moderation details for a specific user (e.g., banned, suspended, admin status).")
    @ApiResponse(
            responseCode = "200",
            description = "User moderation status retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserModeration.class))
    )
    @GetMapping("/status/{username}")
    public ResponseEntity<?> getStatus(@PathVariable String username) {
        UserModeration mod = moderationRepo.findByUserUsername(username);
        if (mod == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(mod);
    }


    // Set a user's report status manually
    @PutMapping("/setReportStatus/{username}/{status}")
    public ResponseEntity<?> setReportStatus(
            @PathVariable String username,
            @PathVariable ModerationStatus status) {

        UserModeration moderation = moderationRepo.findByUserUsername(username);
        if (moderation == null) {
            return ResponseEntity.notFound().build();
        }

        moderation.setReportStatus(status);
        moderationRepo.save(moderation);

        return ResponseEntity.ok("Report status set to " + status);
    }

    // Get all users with a specific report status
    @GetMapping("/getByReportStatus/{status}")
    public ResponseEntity<?> getByReportStatus(@PathVariable ModerationStatus status) {
        List<UserModeration> mods = moderationRepo.findByReportStatus(status);
        return ResponseEntity.ok(mods);
    }

    @GetMapping("/reports/status/{status}")
    public ResponseEntity<?> getReportsByStatus(@PathVariable ModerationStatus status) {
        List<UserModeration> reports = moderationRepo.findByReportStatus(status);
        return ResponseEntity.ok(reports);
    }

}
