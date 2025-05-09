package backendTables.Club;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Bets.Bet;
import backendTables.Users.User;
import backendTables.Users.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Club Activity", description="Store Club information and members/counts")
public class ClubController {
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AchievementRepository achievementRepository;

    @Operation(
            summary = "Create Club",
            description = "Create a club with the given name",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Club created",
                            content = @Content(schema = @Schema(implementation = Club.class))
                    ),
            }
    )
    @PostMapping("/club/create/{clubname}")
    public Club createClub(@Parameter(description = "Name to be given to the club", required = true) @PathVariable String clubname,
                           @Parameter(description = "A user object to be put into the club as the founding member", required = true) @RequestBody User u) {
        Club c = new Club(clubname, u);
        clubRepository.save(c);
        u.setClubId(c.getId());

        // This user has joined a club, so make that tier true in the achievement profile
        Achievements a = achievementRepository.findByUsername(u.getUsername());
        a.increaseCount(9,1);
        achievementRepository.save(a);

        if (userRepository.findByUsername(u.getUsername()) == null) {
            userRepository.save(u);
        }
        else {
            u = userRepository.findByUsername(u.getUsername());
            u.setClubId(c.getId());
            userRepository.save(u);
        }
        return c;
    }

    @Operation(
            summary = "Get Club Info",
            description = "Get various info on the club of the given ID: club name, members, member count, etc.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Club found",
                            content = @Content(schema = @Schema(implementation = Club.class))
                    ),
            }
    )
    @GetMapping("/club/get/{id}")
    public Club getClub(@Parameter(description = "Club ID of the club to find.", required = true)@PathVariable("id") int i) {
        return clubRepository.findById(i);
    }

    @Operation(
            summary = "Find Clubs",
            description = "Returns a list of all currently created clubs",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Example of a club. Typically given as a list of clubs.",
                            content = @Content(schema = @Schema(implementation = Club.class))
                    ),
            }
    )
    @GetMapping("/club/get/getAll")
    public List<Club> getClubRepository() {
        return clubRepository.findAll();
    }

    @Operation(
            summary = "Add User",
            description = "Add the given user by username to the club with given id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully added",
                            content = @Content(schema = @Schema(type="boolean", example = "true"))
                    ),
            }
    )
    @PutMapping("/club/addUser/{id}")
    public boolean addUser(@Parameter(description = "ID of the club to add the user to.", required = true) @PathVariable int id,
                           @Parameter(description = "Username of the user to add to the club", required = true) @RequestBody String username) {
        Club c = clubRepository.findById(id);
        User u = userRepository.findByUsername(username);

        // This user has joined a club, so make that tier true in the achievement profile
        Achievements a = achievementRepository.findByUsername(username);
        a.increaseCount(9,1);
        achievementRepository.save(a);

        boolean t = c.addUser(u);
        if (t) {
            u.setClubId(c.getId());
            userRepository.save(u);
        }
        clubRepository.save(c);
        return t;
    }


    @Operation(
            summary = "Remove User",
            description = "Remove the given user by username to the club with given id, if found in club roster",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully removed",
                            content = @Content(schema = @Schema(type="boolean", example = "true"))
                    ),
            }
    )
    @PutMapping("/club/deleteUser/{id}")
    public boolean deleteUser(@Parameter(description = "ID of club to search and remove user from", required = true) @PathVariable int id,
                              @Parameter(description = "Username of given user to remove from the club", required = true) @RequestBody String username) {
        Club c = clubRepository.findById(id);
        User u = userRepository.findByUsername(username);
        boolean t = c.removeUser(u);
        if (t) {
            u.setClubId(0);
            userRepository.save(u);
        }
        //If no members KILL club
        int count = c.getMemberCount();
        if (count == 0) {
            clubRepository.deleteById(c.getId());
        }
        return t;
    }

}
