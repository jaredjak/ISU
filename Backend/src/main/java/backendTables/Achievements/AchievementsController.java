package backendTables.Achievements;

import backendTables.Users.User;
import backendTables.Users.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="User's Achievement Count", description="Keep track of user's progress toward achievements")
public class AchievementsController {
    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserRepository userRepository;

    private String[] untieredNames = {"Wormling", "Stupid Loser", "Fashion Forward", "Streaker",
            "Las Vegas Local", "Milked", "Accidental Genius", "Hated", "Dedicated",
            "Clubber", "Say My Name!", "Mouse Bites", "Alaskan Bull Worm",
            "GOD", "Oops! All In", "Grub"};

    @Operation(
            summary = "Create Achievement Profile",
            description = "Create's an achievement account tied to the given user. Typically done by backend and unused.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item succesfully created",
                            content = @Content(schema = @Schema(type="boolean", example="true", description="Returns true if successful"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Username not found", content=@Content(schema=@Schema(type="boolean", example="false")))
            }
    )
    @PostMapping("/achievements/{username}")
    public boolean createAchievementProfile(@Parameter(description="Valid username of a user account to create the achievement profile for", required=true)
                                                @PathVariable String username) {
        if (userRepository.findByUsername(username) != null) {
            Achievements a = new Achievements(username);
            achievementRepository.save(a);
            return true;
        }
        return false;
    }

    @Operation(
            summary = "Get Achievements",
            description = "Returns information on the user's achievement progress: currently selected title (and tier of the title), counts towards achievements and tiers for each achievement, and arrays of thresholds to complete achievements",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Achievement Profile Retrieved",
                            content = @Content(schema = @Schema(implementation = Achievements.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Username not found", content=@Content(schema=@Schema(hidden=true)))
            }
    )
    @GetMapping("/achievements/{username}")
    public Achievements getAchievementsProfile(@Parameter(description="Valid username of a user account to search the achievement profiles for", required=true)
                                                   @PathVariable String username) {
        if (achievementRepository.findByUsername(username) != null) {
            return achievementRepository.findByUsername(username);
        }
        return null;
    }

    @Operation(
            summary = "Get Percentage towards next tier",
            description = "For each achievement, calculates what percentage of progress the user has achieved towards the next tier of that achievement.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Username exists",
                            content = @Content(schema = @Schema(type="double[]", example="[0.45, 0.67, 0.0, 1.0, 0.5]"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Username not found", content=@Content(schema=@Schema(hidden=true)))
            }
    )
    @GetMapping("/achievements/getNextTiers/{username}")
    public double[] getNextAchievements(@Parameter(description="Valid username of a user account to search the achievement profiles for",
            required=true)@PathVariable String username) {
        if (achievementRepository.findByUsername(username) != null) {
            Achievements a = achievementRepository.findByUsername(username);
            return a.toNextTier();
        }
        return null;
    }

    @Operation(
            summary = "Get Achievement title selected",
            description = "Returns the user's currently selected achievement title, which is displayed with chat messages.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User exists",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Username not found", content=@Content(schema=@Schema(hidden=true)))
            }
    )
    @GetMapping("/achievements/selected/{username}")
    public String getSelected(@Parameter(description="Valid username of a user account to search the achievement profile for", required=true)@PathVariable String username) {
        if (achievementRepository.findByUsername(username) != null) {
            Achievements a = achievementRepository.findByUsername(username);
            return a.getSelected();
        }

        return null;
    }

    @Operation(
            summary = "Set selected Achievement title",
            description = "Given an index, change the user's Achievement title to that achievement title from the title array. Fails if user doesn't have the achievement.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Achievement Profile Retrieved",
                            content = @Content(schema = @Schema(type="boolean", example="true"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Username", content=@Content(schema=@Schema(hidden=true)))
            }
    )
    @PutMapping("/achievements/selected/{username}/{selectedID}")
    public boolean getSelected(@Parameter(description="Valid username of a user account to create the achievement profile for", required=true)
                                   @PathVariable String username, @Parameter(description="Id of a achievement within the achievement title array to change selected to.", required=true)
                                    @PathVariable int selectedID) {
        if (achievementRepository.findByUsername(username) != null) {
            Achievements a = achievementRepository.findByUsername(username);
            boolean b =  a.setSelected(selectedID, untieredNames);
            achievementRepository.save(a);

            return b;
        }

        return false;
    }

    @Operation(
            summary = "Get Titles",
            description = "Returns a static list of the titles for indexing",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(type="String[]", example="{\"Wormling\", \"Stupid Loser\", \"Fashion Forward\", \"Streaker\",\n" +
                                    "            \"Las Vegas Local\", \"Milked\", \"Accidental Genius\", \"Hated\", \"Dedicated\",\n" +
                                    "            \"Clubber\", \"Say My Name!\", \"Mouse Bites\", \"Alaskan Bull Worm\",\n" +
                                    "            \"GOD\", \"Oops! All In\", \"Grub\"};"))
                    )
            }
    )
    @GetMapping("/achievements/getNames")
    public String[] names() {
        return untieredNames;
    }


    @Operation(
            summary = "Get Descriptions",
            description = "Returns a static list of the descriptions of each Achievement for indexing",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(type="String[]", example="{\n" +
                                    "                \"Win games\",\n" +
                                    "                \"Lose games\",\n" +
                                    "                \"Own Skins\",\n" +
                                    "                \"Gain consecutive win streak\",\n" +
                                    "                \"Win wormbucks from bets\",\n" +
                                    "                \"Lose wormbucks from bets\",\n" +
                                    "                \"Gain consecutive correct bet streak\",\n" +
                                    "                \"Get reported by other players\",\n" +
                                    "                \"Complete Daily Quests\",\n" +
                                    "                \"Join a club\",\n" +
                                    "                \"Buy the Walter skin\",\n" +
                                    "                \"Buy the House skin\",\n" +
                                    "                \"Buy the Bull worm skin\",\n" +
                                    "                \"Become an Admin\",\n" +
                                    "                \"Lose all of your wormbucks in a single bet\",\n" +
                                    "                \"Join Insatiable Insatiable Inchworms!\"\n" +
                                    "        }"))
                    )
            }
    )
    @GetMapping("achievements/getDescription")
    public String[] getDesc() {
        return new String[] {
                "Win games",
                "Lose games",
                "Own Skins",
                "Gain consecutive win streak",
                "Win wormbucks from bets",
                "Lose wormbucks from bets",
                "Gain consecutive correct bet streak",
                "Get reported by other players",
                "Complete Daily Quests",
                "Join a club",
                "Buy the Walter skin",
                "Buy the House skin",
                "Buy the Bull worm skin",
                "Become an Admin",
                "Lose all of your wormbucks in a single bet",
                "Join Insatiable Insatiable Inchworms!"
        };
    }

    // TESTING CONTROLLER
    @Operation(
            summary = "Set Achievement Counts",
            description = "Testing controller, adds to the counts for the achiement at a set index in the user's account. To be done in the backend for actual production."
    )
    @PutMapping("achievements/{index}/{count}/{username}")
    public void increment(@PathVariable int index, @PathVariable int count, @PathVariable String username) {
        Achievements a = achievementRepository.findByUsername(username);
        a.increaseCount(index, count);
        achievementRepository.save(a);
    }


}
