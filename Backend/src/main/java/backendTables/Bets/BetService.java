package backendTables.Bets;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Cosmetics.Cosmetics;
import backendTables.Cosmetics.CosmeticsRepository;
import backendTables.Game.GamePlayer;
import backendTables.Quests.QuestProfile;
import backendTables.Quests.QuestRepository;
import backendTables.Users.User;
import backendTables.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CosmeticsRepository cosmeticsRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private QuestRepository questRepository;


    public void payoutAllBets (List <GamePlayer> winners, String lobbyId) {
        // Retrieve all the bets from the repository
        List<Bet> bets = betRepository.findAll();

        // Loop through each bet in the list
        for (Bet bet : bets) {
            if (!(bet.getLobbyId()+"").equals(lobbyId)) continue;

            betRepository.deleteById(bet.getId());

            // Retrieve the user associated with the bet using the username
            User user = userRepository.findByUsername(bet.getUsername());
            if (user == null) {
                // If the user is not found, skip processing for this bet
                continue;
            }

            // Retrieve the cosmetics account for the user
            Cosmetics c = cosmeticsRepository.findByUsername(user.getUsername());
            QuestProfile q = questRepository.findByUsername(user.getUsername());
            q.localDateCheck(LocalDateTime.now());

            if (c == null) {
                continue;
            }
            Achievements a = achievementRepository.findByUsername(user.getUsername());
            double payoutAmount = 0;
            for (GamePlayer g: winners) {
                if (g.getPlayerIdentifier().equals(bet.getPositionBet())) {
                    payoutAmount = bet.returnBet(true);
                    // Increase streak count in achievement
                    a.increaseCount(6, 1);
                    // Increase cash earned in achievement
                    a.increaseCount(4, (int) payoutAmount);
                    achievementRepository.save(a);
                }
            }

            // If you lost increment lose money achievement
            if (payoutAmount == 0) {
                a.increaseCount(5, (int) bet.getAmount());
            }

            // Adjust the user's cosmetics balance with the payout value
            c.adjustBalance(payoutAmount);
            int i = q.increaseCount(1, (int) payoutAmount);
            c.adjustBalance(i);

            // If the user won the bet, update their bet streak status
            user.setBetStreak(bet.getBetStreak() != 0);

            // Save updated user and cosmetics information back to the repository
            userRepository.save(user);
            cosmeticsRepository.save(c);
            questRepository.save(q);
        }
    }
}
