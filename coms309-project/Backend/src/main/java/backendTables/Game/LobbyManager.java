package backendTables.Game;

import backendTables.Bets.BetService;
import backendTables.Leaderboard.LeaderboardRepository;
import backendTables.Users.UserRepository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LobbyManager {

    private static final Map<String, GameBoard> lobbies = new ConcurrentHashMap<>();

    private static BetService betService;
    private static UserRepository userRepository;
    private static LeaderboardRepository leaderboardRepository;

    // Inject dependencies (set once on application startup)
    public static void initialize(BetService betSvc, UserRepository userRepo, LeaderboardRepository leaderRepo) {
        betService = betSvc;
        userRepository = userRepo;
        leaderboardRepository = leaderRepo;
    }

    public static synchronized GameBoard getOrCreatePublicLobby() {
        for (Map.Entry<String, GameBoard> entry : lobbies.entrySet()) {
            GameBoard board = entry.getValue();
            if (!board.isClubLobby() && board.getState() == GameBoard.GameState.LOBBY && board.getPlayers().size() < 4) {
                return board;
            }
        }
        String lobbyId = UUID.randomUUID().toString();
        GameBoard newBoard = new GameBoard(betService, userRepository, leaderboardRepository, false);
        lobbies.put(lobbyId, newBoard);
        return newBoard;
    }

    public static synchronized GameBoard createClubLobby(int clubId) {
        String lobbyId = "club-" + clubId;
        if (lobbies.containsKey(lobbyId)) {
            return lobbies.get(lobbyId);
        }
        GameBoard board = new GameBoard(betService, userRepository, leaderboardRepository, true);
        lobbies.put(lobbyId, board);
        return board;
    }

    public static synchronized GameBoard getOrCreateClubLobby(int clubId) {
        String lobbyId = "club-" + clubId;
        return lobbies.computeIfAbsent(lobbyId,
                id -> new GameBoard(betService, userRepository, leaderboardRepository, true));
    }

    public static GameBoard getLobby(String lobbyId) {
        return lobbies.get(lobbyId);
    }

    public static synchronized void removeLobbyAfterFinish(GameBoard finishedBoard) {
        for (Map.Entry<String, GameBoard> entry : lobbies.entrySet()) {
            if (entry.getValue() == finishedBoard && !finishedBoard.isClubLobby()) {
                lobbies.remove(entry.getKey());
                break;
            }
        }
    }

    public static synchronized String getLobbyIdForBoard(GameBoard board) {
        for (Map.Entry<String, GameBoard> entry : lobbies.entrySet()) {
            if (entry.getValue() == board) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Map<String, GameBoard> getAllLobbies() {
        return lobbies;
    }

    // Return the lobbyId of the most recently started RUNNING game
    public static synchronized String getMostRecentRunningLobbyId() {
        return lobbies.entrySet().stream()
                .filter(e -> e.getValue().getState() == GameBoard.GameState.RUNNING)
                .filter(e -> !e.getValue().isClubLobby()) // exclude club games
                .sorted((a, b) -> Long.compare(
                        b.getValue().getGameStartTime(),
                        a.getValue().getGameStartTime()
                ))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    // Return a random lobbyId from currently running games
    public static synchronized String getRandomRunningLobbyId() {
        List<Map.Entry<String, GameBoard>> runningGames = lobbies.entrySet().stream()
                .filter(e -> e.getValue().getState() == GameBoard.GameState.RUNNING)
                .filter(e -> !e.getValue().isClubLobby()) // exclude club games
                .collect(Collectors.toList());

        if (runningGames.isEmpty()) return null;

        return runningGames.get(new Random().nextInt(runningGames.size())).getKey();
    }

}
