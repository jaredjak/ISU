package backendTables.Game;

import backendTables.Users.User;
import backendTables.Users.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/gameSocket/{lobbyId}/{username}", configurator = CustomSpringConfigurator.class)
@Tag(name = "Game WebSocket", description = "Handles real-time game player communication per lobby.")
public class GameSocket {

    static {
        System.out.println("✅ GameSocket class loaded.");
    }

    public GameSocket() {
        System.out.println("✅ GameSocket constructor called.");
    }

    private static void safeSend(Collection<Session> rawSessions, String message) {
        if (rawSessions == null || rawSessions.isEmpty()) return;

        // Make a shallow copy so concurrent adds/removes don’t blow up the loop
        for (Session s : List.copyOf(rawSessions)) {
            if (s.isOpen()) {
                try { s.getBasicRemote().sendText(message); }
                catch (IOException ignored) {}
            }
        }
    }

    private static final Map<String, Long> playerLastHitTime = new ConcurrentHashMap<>();
    private static final long hitCooldownMillis = 1500; // 1.5 seconds
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    //public static void initialize(UserRepository repo) {
    //    userRepository = repo;
    //}

    @OnOpen
    public void onOpen(Session session, @PathParam("lobbyId") String lobbyId, @PathParam("username") String username) throws IOException {
        GameBoard board = LobbyManager.getLobby(lobbyId);
        if (board == null) {
            session.getBasicRemote().sendText("{\"error\": \"Invalid lobby\"}");
            session.close();
            return;
        }

        Optional<GamePlayer> playerOptional = board.getPlayers().stream()
                .filter(p -> username.equals(p.getUsername()))
                .findFirst();

        if (playerOptional.isEmpty()) {
            session.getBasicRemote().sendText("{\"error\": \"User not found in lobby, please join via REST first\"}");
            session.close();
            return;
        }

        LobbySessionManager.addPlayerSession(lobbyId, session);
        broadcastToLobby(lobbyId, "{\"event\": \"user_joined\", \"username\": \"" + username + "\"}");
    }


    @OnMessage
    public void onMessage(Session session, String message, @PathParam("lobbyId") String lobbyId, @PathParam("username") String username) {
        if (!"hit".equalsIgnoreCase(message.trim())) return;

        String playerKey = lobbyId + ":" + username;
        long now = System.currentTimeMillis();
        long lastHit = playerLastHitTime.getOrDefault(playerKey, 0L);

        if (now - lastHit < hitCooldownMillis) {
            // Too soon, send a cooldown error message
            try {
                String errorMessage = new ObjectMapper().writeValueAsString(Map.of(
                        "error", "Hit on cooldown"
                ));
                session.getBasicRemote().sendText(errorMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // Accept the hit
        playerLastHitTime.put(playerKey, now);

        GameBoard board = LobbyManager.getLobby(lobbyId);
        if (board == null) return;

        String playerIdentifier = board.getPlayers().stream()
                .filter(p -> username.equals(p.getUsername()))
                .map(GamePlayer::getPlayerIdentifier)
                .findFirst()
                .orElse(null);

        if (playerIdentifier == null) return;

        new Thread(() -> {
            int ballsHit = board.checkAndRemoveBallsInHitbox(playerIdentifier);

            board.getPlayers().stream()
                    .filter(p -> playerIdentifier.equals(p.getPlayerIdentifier()))
                    .findFirst()
                    .ifPresent(p -> p.incrementScoreBy(ballsHit));

            broadcastToLobby(lobbyId,
                    "{\"type\":\"hit\",\"player\":\"" + playerIdentifier + "\"}");

            broadcastGameSnapshot(lobbyId, board.generateSnapshot());
        }).start();
    }

    @OnClose
    public void onClose(Session session, @PathParam("lobbyId") String lobbyId, @PathParam("username") String username) {
        LobbySessionManager.removePlayerSession(lobbyId, session);

        GameBoard board = LobbyManager.getLobby(lobbyId);
        if (board != null) {
            board.removePlayerByUsername(username);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void broadcastToLobby(String lobbyId, String message) {
        safeSend(LobbySessionManager.getPlayerSessions(lobbyId), message);
    }

    public static void broadcastPlayerAssignments(String lobbyId, Map<String,String> assignments) {
        try {
            String json = objectMapper.writeValueAsString(Map.of("players", assignments));
            safeSend(LobbySessionManager.getPlayerSessions(lobbyId), json);
        } catch (IOException ignored) {}
    }

    public static void broadcastBallPositions(String lobbyId, String ballStatesJson) {
        safeSend(LobbySessionManager.getPlayerSessions(lobbyId), ballStatesJson);
    }

    private static void broadcastGameSnapshot(String lobbyId, GameSnapshot snapshot) {
        try {
            String json = objectMapper.writeValueAsString(snapshot);
            broadcastToLobby(lobbyId, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastFinalResults(String lobbyId, String finalResultsJson) {
        safeSend(LobbySessionManager.getPlayerSessions(lobbyId), finalResultsJson);
    }

    private void quitPlayer(String lobbyId, String username, Session session) {
        try { session.close(); } catch (IOException ignored) {}

        GameBoard board = LobbyManager.getLobby(lobbyId);
        if (board != null) board.removePlayerByUsername(username);

        LobbySessionManager.removePlayerSession(lobbyId, session);

        broadcastToLobby(lobbyId,
                "{\"type\":\"player_quit\",\"player\":\"" + username + "\"}");
    }


}
