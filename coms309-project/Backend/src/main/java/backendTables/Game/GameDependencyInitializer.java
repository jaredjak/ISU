package backendTables.Game;

import backendTables.Bets.BetService;
import backendTables.Game.LobbyManager;
import backendTables.Leaderboard.LeaderboardRepository;
import backendTables.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class GameDependencyInitializer {

    @Autowired
    private BetService betService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    /**
     * Injects all required dependencies into LobbyManager on app startup.
     */
    @PostConstruct
    public void injectDependencies() {
        System.out.println("✅ Injecting Spring-managed dependencies into LobbyManager");
        LobbyManager.initialize(betService, userRepository, leaderboardRepository);
    }
}
