package backendTables.Game;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LobbySessionManager {

    private static final Map<String, List<Session>> playerSessionsPerLobby = new ConcurrentHashMap<>();
    private static final Map<String, List<Session>> spectatorSessionsPerLobby = new ConcurrentHashMap<>();

    // === Player Session Management ===
    public static void addPlayerSession(String lobbyId, Session session) {
        playerSessionsPerLobby.computeIfAbsent(lobbyId, k -> Collections.synchronizedList(new ArrayList<>())).add(session);
    }

    public static void removePlayerSession(String lobbyId, Session session) {
        List<Session> sessions = playerSessionsPerLobby.get(lobbyId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    public static List<Session> getPlayerSessions(String lobbyId) {
        return playerSessionsPerLobby.getOrDefault(lobbyId, Collections.emptyList());
    }

    // === Spectator Session Management ===
    public static void addSpectatorSession(String lobbyId, Session session) {
        spectatorSessionsPerLobby.computeIfAbsent(lobbyId, k -> Collections.synchronizedList(new ArrayList<>())).add(session);
    }

    public static void removeSpectatorSession(String lobbyId, Session session) {
        List<Session> sessions = spectatorSessionsPerLobby.get(lobbyId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    public static List<Session> getSpectatorSessions(String lobbyId) {
        return spectatorSessionsPerLobby.getOrDefault(lobbyId, Collections.emptyList());
    }
}
