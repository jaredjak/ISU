package backendTables.Events;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Quests.QuestProfile;
import backendTables.Quests.QuestRepository;
import backendTables.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import backendTables.Leaderboard.Leaderboard;
import backendTables.Leaderboard.LeaderboardRepository;
import backendTables.Cosmetics.Cosmetics;
import backendTables.Cosmetics.CosmeticsRepository;
import backendTables.UserModeration.UserModeration;
import backendTables.UserModeration.UserModerationRepository;
/**
 *
 * @author Carter Hauschildt
 *
 */

@Component
public class UserCreatedListener {

    @Autowired
    private UserModerationRepository userModerationRepository;
    @Autowired
    private LeaderboardRepository leaderboardRepository;
    @Autowired
    private CosmeticsRepository cosmeticsRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private QuestRepository questRepository;

    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        User user = event.getUser();

        Leaderboard leaderboardEntry = new Leaderboard(user, 0, 0);
        Cosmetics cosmetics = new Cosmetics(user);
        UserModeration moderation = new UserModeration(user);
        Achievements a = new Achievements(user.getUsername());
        QuestProfile q = new QuestProfile(user.getUsername());

        leaderboardRepository.save(leaderboardEntry);
        cosmeticsRepository.save(cosmetics);
        userModerationRepository.save(moderation);
        achievementRepository.save(a);
        questRepository.save(q);
    }
}
