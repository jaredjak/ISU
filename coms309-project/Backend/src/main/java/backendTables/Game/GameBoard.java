package backendTables.Game;

import backendTables.Leaderboard.Leaderboard;
import backendTables.Leaderboard.LeaderboardRepository;
import backendTables.Users.User;
import backendTables.Users.UserRepository;
import backendTables.Bets.BetService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GameBoard {

    public enum GameState { LOBBY, COUNTDOWN, RUNNING, FINISHED }

    private GameState state = GameState.LOBBY;
    private List<GamePlayer> players = new CopyOnWriteArrayList<>();
    private List<Ball> balls = new CopyOnWriteArrayList<>();
    private final Random random = new Random();
    private volatile boolean running = false;
    private volatile boolean hasEnded = false;


    private final int boardWidth = 1000;
    private final int boardHeight = 1000;
    private final long gameDurationMillis = 60000;
    private final long ballSpawnIntervalMillis = 5000;
    private long gameStartTime;
    private long gameEndTime;
    private long lastBallSpawnTime;
    private ScheduledExecutorService gameTimerExecutor = Executors.newSingleThreadScheduledExecutor();

    private final BetService betService;
    private final UserRepository userRepository;
    private final LeaderboardRepository leaderboardRepository;

    private final boolean isClubLobby;

    public GameBoard(BetService betService, UserRepository userRepository, LeaderboardRepository leaderboardRepository, boolean isClubLobby) {
        this.betService = betService;
        this.userRepository = userRepository;
        this.leaderboardRepository = leaderboardRepository;
        this.isClubLobby = isClubLobby;
    }

    public synchronized boolean addPlayer(GamePlayer player) {
        if (state != GameState.LOBBY) return false;
        if (players.size() >= 4)      return false;
        if (players.stream().anyMatch(p -> p.getUsername().equals(player.getUsername())))
            return false; // already in lobby

        player.setPlayerIdentifier("player" + (players.size() + 1));
        players.add(player);
        if (players.size() == 4) startCountdown();
        return true;
    }


    private void startCountdown() {
        state = GameState.COUNTDOWN;
        new Thread(() -> {
            try {
                Thread.sleep(6000);
                broadcastAssignments();
            } catch (InterruptedException ignored) {}
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                startGame();
            } catch (InterruptedException ignored) {}
        }).start();
    }

    private void broadcastAssignments() {
        Map<String, String> assignments = new HashMap<>();
        for (GamePlayer player : players) {
            assignments.put(player.getPlayerIdentifier(), player.getUsername());
        }

        String lobbyId = LobbyManager.getLobbyIdForBoard(this);
        if (lobbyId != null) {
            GameSocket.broadcastPlayerAssignments(lobbyId, assignments);
            SpectatorSocket.broadcastPlayerAssignments(lobbyId, assignments);
        }
    }

    private boolean withinHitbox(double x, double y, String playerIdentifier) {
        double width = 350, height = 400;
        switch (playerIdentifier) {
            case "player1": // bottom-center
                return x >= (boardWidth - width) / 2.0 && x <= (boardWidth + width) / 2.0 &&
                        y >= boardHeight - height && y <= boardHeight;
            case "player2": // right-center
                return x >= boardWidth - height && x <= boardWidth &&
                        y >= (boardHeight - width) / 2.0 && y <= (boardHeight + width) / 2.0;
            case "player3": // top-center
                return x >= (boardWidth - width) / 2.0 && x <= (boardWidth + width) / 2.0 &&
                        y >= 0 && y <= height;
            case "player4": // left-center
                return x >= 0 && x <= height &&
                        y >= (boardHeight - width) / 2.0 && y <= (boardHeight + width) / 2.0;
            default:
                return false;
        }
    }



    public int checkAndRemoveBallsInHitbox(String playerIdentifier) {
        List<Ball> ballsToRemove = new ArrayList<>();

        for (Ball ball : balls) {
            if (withinHitbox(ball.getX(), ball.getY(), playerIdentifier)) {
                ballsToRemove.add(ball);
            }
        }

        int ballsHit = ballsToRemove.size();
        balls.removeAll(ballsToRemove);

        return ballsHit;
    }


    private void startGame() {
        state = GameState.RUNNING;
        hasEnded = false;
        gameStartTime = System.currentTimeMillis();
        lastBallSpawnTime = gameStartTime;
        running = true;
        broadcastAssignments();

        gameTimerExecutor.scheduleAtFixedRate(() -> {
            long elapsed = System.currentTimeMillis() - gameStartTime;
            long remaining = gameDurationMillis - elapsed;

            if (remaining <= 0 && !hasEnded) {
                hasEnded = true;                     // prevent duplicate calls
                broadcastTimeRemaining(0);           // send final time update
                endGame();                           // cleanly transition
            } else if (remaining > 0) {
                broadcastTimeRemaining(remaining);
            }
        }, 0, 1, TimeUnit.SECONDS);

        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                updateGame();  // Now doesn't call endGame
            }
        }).start();
    }


    private void updateGame() {
        if (state != GameState.RUNNING) return;

        for (Ball ball : balls) {
            ball.updatePosition();
            checkBoundaryCollision(ball);
        }

        long now = System.currentTimeMillis();
        if (now - lastBallSpawnTime >= ballSpawnIntervalMillis) {
            spawnBalls();
            lastBallSpawnTime = now;
        }

        List<BallState> ballStates = getBallStates();
        String lobbyId = LobbyManager.getLobbyIdForBoard(this);

        if (lobbyId != null) {
            try {
                String json = new ObjectMapper().writeValueAsString(Map.of("balls", ballStates));
                GameSocket.broadcastBallPositions(lobbyId, json);
                SpectatorSocket.broadcastBallPositions(lobbyId, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkBoundaryCollision(Ball ball) {
        if (ball.getX() <= 0 || ball.getX() >= boardWidth) ball.reverseXVelocity();
        if (ball.getY() <= 0 || ball.getY() >= boardHeight) ball.reverseYVelocity();
    }

    private void spawnBalls() {
        for (int i = 0; i < 50; i++) {
            Ball ball = new Ball(boardWidth / 2.0, boardHeight / 2.0);
            ball.setRandomVelocity(random);
            balls.add(ball);
        }
    }

    private synchronized void endGame() {
        running = false;
        state = GameState.FINISHED;

        if (gameTimerExecutor != null && !gameTimerExecutor.isShutdown()) {
            gameTimerExecutor.shutdownNow();
        }

        try {
            balls.clear();
            gameEndTime = System.currentTimeMillis();

            int highestScore = players.stream()
                    .mapToInt(GamePlayer::getScore)
                    .max()
                    .orElse(0);

            List<GamePlayer> winners = players.stream()
                    .filter(p -> p.getScore() == highestScore)
                    .collect(Collectors.toList());

            List<Map<String, Object>> finalResults = new ArrayList<>();
            for (GamePlayer player : players) {
                User user = userRepository.findById(player.getUserId());
                if (user == null) continue;

                Leaderboard entry = leaderboardRepository.findByUserId(user.getId());
                if (entry == null) {
                    entry = new Leaderboard(user, player.getScore(), 0);
                }

                if (player.getScore() > entry.getScore()) {
                    entry.setScore(player.getScore());
                }

                if (winners.contains(player)) {
                    entry.setWinStreak(entry.getWinStreak() + 1);
                } else {
                    entry.setWinStreak(0);
                }

                leaderboardRepository.save(entry);

                Map<String, Object> playerResult = new HashMap<>();
                playerResult.put("username", player.getUsername());
                playerResult.put("score", player.getScore());
                playerResult.put("isWinner", winners.contains(player));
                finalResults.add(playerResult);
            }

            // Payout bets
            betService.payoutAllBets(winners, LobbyManager.getLobbyIdForBoard(this));

            // Broadcast final results after a small delay
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                    String lobbyId = LobbyManager.getLobbyIdForBoard(this);
                    if (lobbyId != null) {
                        String json = new ObjectMapper().writeValueAsString(Map.of("finalResults", finalResults));
                        GameSocket.broadcastFinalResults(lobbyId, json);
                        SpectatorSocket.broadcastFinalResults(lobbyId, json);
                    }
                } catch (Exception e) {
                    System.err.println("Error broadcasting final results: " + e.getMessage());
                    e.printStackTrace();
                }
            }).start();

            // Public lobbies auto-remove themselves 60 seconds after finishing
            if (!isClubLobby) {
                new Thread(() -> {
                    try {
                        Thread.sleep(60000);
                        LobbyManager.removeLobbyAfterFinish(this);
                    } catch (Exception e) {
                        System.err.println("Error removing lobby after finish: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (Exception e) {
            System.err.println("Critical error in endGame logic: " + e.getMessage());
            e.printStackTrace();
        } finally {
            running = false;
            state = GameState.FINISHED;
        }
    }

    private void broadcastTimeRemaining(long millisRemaining) {
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(
                    Map.of("timeRemainingMillis", millisRemaining)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String lobbyId = LobbyManager.getLobbyIdForBoard(this);
        if (lobbyId != null) {
            GameSocket.broadcastToLobby(lobbyId, json);
            SpectatorSocket.broadcastToSpectators(lobbyId, json);
        }
    }

    public List<BallState> getBallStates() {
        return balls.stream().map(b -> new BallState(b.getX(), b.getY())).collect(Collectors.toList());
    }

    public GameSnapshot generateSnapshot() {
        List<GameSnapshot.PlayerScore> scoreList = players.stream()
                .map(p -> new GameSnapshot.PlayerScore(p.getPlayerIdentifier(), p.getScore()))
                .collect(Collectors.toList());
        return new GameSnapshot(scoreList);
    }

    public synchronized void removePlayerByUsername(String username) {
        players.removeIf(p -> p.getUsername().equals(username));
    }

    public GameState getState() {
        return state;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public boolean isClubLobby() {
        return isClubLobby;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

}
