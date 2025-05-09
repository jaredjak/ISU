package backendTables.Game;

import backendTables.Users.User;
import backendTables.Users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@ServerEndpoint(value = "/spectatorSocket/{lobbyId}/{username}", configurator = CustomSpringConfigurator.class)
@Tag(name = "Spectator WebSocket", description = "Handles real-time spectating of games per lobby.")
public class SpectatorSocket {

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

        User user = userRepository.findByUsername(username);
        if (user == null) {
            session.getBasicRemote().sendText("{\"error\": \"User not found\"}");
            session.close();
            return;
        }

        LobbySessionManager.addSpectatorSession(lobbyId, session);
        session.getBasicRemote().sendText("{\"message\": \"Connected as spectator\"}");
    }

    @OnClose
    public void onClose(Session session, @PathParam("lobbyId") String lobbyId) {
        LobbySessionManager.removeSpectatorSession(lobbyId, session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void broadcastBallPositions(String lobbyId, String ballStatesJson) {
        safeSend(LobbySessionManager.getPlayerSessions(lobbyId), ballStatesJson);
    }

    public static void broadcastGameSnapshot(String lobbyId, GameSnapshot snapshot) {
        try {
            String json = objectMapper.writeValueAsString(snapshot);
            broadcastToSpectators(lobbyId, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastPlayerAssignments(String lobbyId, Map<String,String> assignments) {
        try {
            String json = objectMapper.writeValueAsString(Map.of("players", assignments));
            safeSend(LobbySessionManager.getPlayerSessions(lobbyId), json);
        } catch (IOException ignored) {}
    }

    public static void broadcastFinalResults(String lobbyId, String finalResultsJson) {
        safeSend(LobbySessionManager.getPlayerSessions(lobbyId), finalResultsJson);
    }


    public static void broadcastToSpectators(String lobbyId, String message) {
        safeSend(LobbySessionManager.getSpectatorSessions(lobbyId), message);
    }

    // Simple wrapper classes for clean JSON
    private static class BallBroadcast {
        public List<BallState> balls;
        public BallBroadcast(List<BallState> balls) { this.balls = balls; }
    }

    private static class AssignmentBroadcast {
        public Map<String, String> players;
        public AssignmentBroadcast(Map<String, String> players) { this.players = players; }
    }

    private static class FinalResultsBroadcast {
        public List<Map<String, Object>> finalResults;
        public FinalResultsBroadcast(List<Map<String, Object>> finalResults) { this.finalResults = finalResults; }
    }
}
