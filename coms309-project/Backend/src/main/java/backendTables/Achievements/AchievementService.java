package backendTables.Achievements;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Cosmetics.Cosmetics;
import backendTables.Cosmetics.CosmeticsRepository;
import backendTables.Game.GamePlayer;
import backendTables.Users.User;
import backendTables.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    public int getTier(String username) {
        return achievementRepository.findByUsername(username).getTier();
    }

    public String getSelected(String username) {
        return achievementRepository.findByUsername(username).getSelected();
    }

}
