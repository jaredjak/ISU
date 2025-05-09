package backendTables.Bets;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Cosmetics.*;
import backendTables.Game.GamePlayer;
import backendTables.Users.User;
import backendTables.Users.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List; 


/**
 *
 * @author
 *
 */

@RestController
@Tag(name="Bet Activity", description="Store Bet Information for Calculation After Games")
public class BetController {

    @Autowired
    BetRepository betRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CosmeticsRepository cosmeticsRepository;
    @Autowired
    AchievementRepository achievementRepository;

    @Operation(
            summary = "Create Bet",
            description = "Create a bet object in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Achievement Profile Retrieved",
                            content = @Content(schema = @Schema(implementation = Bet.class))
                    ),
            }
    )
    @PostMapping("/placeBet")
    public Bet createBet(@Parameter(description="Bet object to be stored. Has the following: Username of user submitting the bet, position of player bet to win (player3), amount bet, server id of game where bet occurred, and winnings multiplier", required=true)
            @RequestBody Bet bet) {
        Bet b = bet;
        b.calcTimeMult();
        b.setBetStreak(userRepository.findByUsername(b.getUsername()).getBetStreak());
        Cosmetics c = cosmeticsRepository.findByUsername(b.getUsername());
        if ((int) b.getAmount() > (int) c.getBalance()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do NOT have enough cash for this.");
        }

        if ((int) b.getAmount() == (int) c.getBalance()) {
            // If they spend all their cash in one go the all in achievement is achieved
            Achievements a = achievementRepository.findByUsername(bet.getUsername());
            a.increaseCount(14, 1);
            achievementRepository.save(a);
        }

        c.adjustBalance(-b.getAmount());
        c.setPrevBet(-b.getAmount());
        cosmeticsRepository.save(c);
        return betRepository.save(b);
    }


    @Operation(
            summary = "Payout Bets",
            description = "For each game, removes the bets from the database and pays out the user making the bet based on the winner."
    )
    @DeleteMapping("/cashoutBets/{winPos}/{lobbyId}")
    public void payoutBets(@Parameter(description = "The player position who won the match (Player1, Player2, etc.)")
                               @PathVariable String winPos, @Parameter(description = "The server ID of the game that finished. Only bets from this id will be paid out.")
                               @PathVariable int lobbyId) {
        // Retrieve all the bets from the repository
        List<Bet> bets = betRepository.findByLobbyId(lobbyId);
        // Loop through each bet in the list
        for (Bet bet : bets) {
            // Delete the bet from the repository
            betRepository.deleteById(bet.getId());
            // Retrieve the user associated with the bet using the username
            User user = userRepository.findByUsername(bet.getUsername());
            if (user == null) {
                // If the user is not found, skip processing for this bet
                continue;
            }
            // Retrieve the cosmetics account for the user
            Cosmetics c = cosmeticsRepository.findByUsername(user.getUsername());
            if (c == null) {
                continue;
            }
            double payoutAmount = bet.returnBet(bet.getPositionBet().equals(winPos));

            // Adjust the user's cosmetics balance with the payout value
            c.adjustBalance(payoutAmount);

            // If the user won the bet, update their bet streak status
            user.setBetStreak(bet.getBetStreak() != 0);
            if (payoutAmount > 0) {
                c.setPrevBet(payoutAmount);
                // Check if the user bet streak is above thresholds for next tier
                Achievements a = achievementRepository.findByUsername(user.getUsername());
                a.increaseCount(6, 1);
                achievementRepository.save(a);
            }

            // Save updated user and cosmetics information back to the repository
            userRepository.save(user);
            cosmeticsRepository.save(c);
        }




    }
}

