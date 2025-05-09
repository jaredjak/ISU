package backendTables.Game;

import backendTables.Club.Club;
import backendTables.Club.ClubRepository;
import backendTables.UserModeration.UserModeration;
import backendTables.UserModeration.UserModerationRepository;
import backendTables.Users.User;
import backendTables.Users.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game")
@Tag(name = "Game Lobby Management", description = "Endpoints for joining public or club lobbies and querying game state or players.")
public class GameController {

    @Autowired private UserRepository userRepository;
    @Autowired private ClubRepository clubRepository;
    @Autowired private UserModerationRepository moderationRepository;

    @Operation(summary = "Join a public game lobby", description = "Allows a player to join an open public lobby using their username. Automatically assigns a player slot if space is available.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player joined successfully or lobby full"),
            @ApiResponse(responseCode = "404", description = "User not found or banned/suspended")
    })
    @PostMapping("/joinByUsername/{username}")
    public Map<String, String> joinGameByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return Map.of("error", "user not found");
        }

        UserModeration moderation = moderationRepository.findByUserUsername(username);
        if (moderation != null) {
            if (moderation.isBanned()) {
                return Map.of("error", "user is banned");
            }
            if (moderation.isSuspended()) {
                return Map.of("error", "user is suspended until " + moderation.getSuspendedUntil().toString());
            }
        }

        GameBoard board = LobbyManager.getOrCreatePublicLobby();
        GamePlayer player = new GamePlayer(user.getId(), username);
        boolean joined = board.addPlayer(player);
        String lobbyId = LobbyManager.getLobbyIdForBoard(board);

        return joined
                ? Map.of("message", "joined game", "lobbyId", lobbyId)
                : Map.of("error", "cannot join game");
    }

    @Operation(summary = "Join a club lobby", description = "Allows a player to join their club's dedicated game lobby if they are a valid member.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player joined club lobby successfully"),
            @ApiResponse(responseCode = "404", description = "User or club not found, or player not a member/banned/suspended")
    })
    @PostMapping("/club/joinGame/{clubId}/{username}")
    public Map<String, String> joinClubGame(@PathVariable int clubId, @PathVariable String username) {
        User user = userRepository.findByUsername(username);
        Club club = clubRepository.findById(clubId);

        if (user == null || club == null || !club.getUsers().contains(username)) {
            return Map.of("error", "not authorized");
        }

        UserModeration moderation = moderationRepository.findByUserUsername(username);
        if (moderation != null) {
            if (moderation.isBanned()) {
                return Map.of("error", "user is banned");
            }
            if (moderation.isSuspended()) {
                return Map.of("error", "user is suspended until " + moderation.getSuspendedUntil().toString());
            }
        }

        GameBoard board = LobbyManager.getOrCreateClubLobby(clubId);
        GamePlayer player = new GamePlayer(user.getId(), username);
        boolean joined = board.addPlayer(player);
        String lobbyId = LobbyManager.getLobbyIdForBoard(board);

        return joined
                ? Map.of("message", "joined club lobby", "lobbyId", lobbyId)
                : Map.of("error", "lobby full or not joinable");
    }

    @Operation(summary = "Get the current game state", description = "Returns the current state of a lobby (e.g., LOBBY, COUNTDOWN, RUNNING, FINISHED).")
    @ApiResponse(responseCode = "200", description = "Game state retrieved successfully")
    @GetMapping("/state/{lobbyId}")
    public GameBoard.GameState getGameState(@PathVariable String lobbyId) {
        GameBoard board = LobbyManager.getLobby(lobbyId);
        return board != null ? board.getState() : null;
    }

    @Operation(summary = "Get players in a lobby", description = "Returns a list of all players currently in the given lobby.")
    @ApiResponse(responseCode = "200", description = "Player list retrieved successfully")
    @GetMapping("/players/{lobbyId}")
    public List<GamePlayer> getPlayers(@PathVariable String lobbyId) {
        GameBoard board = LobbyManager.getLobby(lobbyId);
        return board != null ? board.getPlayers() : List.of();
    }

    @Operation(summary = "Remove a player from a lobby", description = "Removes a player from the specified lobby and notifies all clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player removed successfully"),
            @ApiResponse(responseCode = "404", description = "Lobby not found")
    })
    @DeleteMapping("/remove/{lobbyId}/{username}")
    public Map<String,String> kickPlayer(@PathVariable String lobbyId,
                                         @PathVariable String username) {
        GameBoard board = LobbyManager.getLobby(lobbyId);
        if (board == null) return Map.of("error","lobby not found");
        board.removePlayerByUsername(username);
        GameSocket.broadcastToLobby(lobbyId,
                "{\"event\":\"player_removed\",\"username\":\""+username+"\"}");
        SpectatorSocket.broadcastToSpectators(lobbyId,
                "{\"event\":\"player_removed\",\"username\":\""+username+"\"}");
        return Map.of("message","removed");
    }

    @Operation(summary = "Get the most recently started running game", description = "Returns the lobby ID of the most recently started game in progress.")
    @GetMapping("/spectate/recent")
    public Map<String, String> spectateMostRecentGame() {
        String lobbyId = LobbyManager.getMostRecentRunningLobbyId();
        return lobbyId != null
                ? Map.of("lobbyId", lobbyId)
                : Map.of("error", "no running games");
    }

    @Operation(summary = "Get a random running game", description = "Returns the lobby ID of a randomly selected active game.")
    @GetMapping("/spectate/random")
    public Map<String, String> spectateRandomGame() {
        String lobbyId = LobbyManager.getRandomRunningLobbyId();
        return lobbyId != null
                ? Map.of("lobbyId", lobbyId)
                : Map.of("error", "no running games");
    }

}
