package backendTables.Quests;

import backendTables.Club.Club;
import backendTables.Cosmetics.CosmeticsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Tag(name="Quest Updates", description="Store Quest information and progress/counts")
public class QuestController {

    @Autowired
    private QuestRepository questRepository;

    @Operation(
            summary = "Get Quest Profile",
            description = "Gets information on Quest counts, states (complete/incomplete), and completion thresholds",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account retrieved",
                            content = @Content(schema = @Schema(implementation = QuestProfile.class))
                    ),
            }
    )
    @GetMapping("/quests/{username}")
    public QuestProfile getProfile(@Parameter(description = "Username of the quest profile to find.", required = true) @PathVariable String username) {
        QuestProfile q = questRepository.findByUsername(username);
        return q;
    }

    @Operation(
            summary = "Get quest progress",
            description = "Returns a double array representing the progress made towards completing each quest",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Profile retrieved",
                            content = @Content(schema = @Schema(type="double[]", example = "[0.74, 0.0, 1.0]"))
                    ),
            }
    )
    @GetMapping("/quests/toNextTier/{username}")
    public double[] getPercents(@Parameter(description = "Get the information from quest profile of this username",required = true)@PathVariable String username) {
        return questRepository.findByUsername(username).toNextTier();
    }

    @Operation(
            summary = "Daily reset",
            description = "Checks if it is the next day. If so, reset progress in all quests."
    )
    @PutMapping("/quests/resetTime/{username}")
    public void reset(@Parameter(description = "Username of the account to reset information of.", required = true) @PathVariable String username) {
        QuestProfile q = questRepository.findByUsername(username);
        q.localDateCheck(LocalDateTime.now());
        questRepository.save(q);
    }

    @PutMapping("/quests/{index}/{amount}/{username}")
    public void rest(@PathVariable int index, @PathVariable int amount, @PathVariable String username) {
        QuestProfile q = questRepository.findByUsername(username);
        q.increaseCount(index, amount);
        questRepository.save(q);
    }
}
