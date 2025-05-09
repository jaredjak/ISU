package backendTables.Game;

public class GamePlayer {
    private int userId;
    private int score = 0;
    private String playerIdentifier; // Identifier like "player1", "player2", etc.
    private String username; // New field

    // Constructor
    public GamePlayer(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    // Optional: Overloaded constructor if needed
    public GamePlayer(int userId) {
        this.userId = userId;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public void incrementScoreBy(int amount) {
        this.score += amount;
    }

    public String getPlayerIdentifier() {
        return playerIdentifier;
    }

    public void setPlayerIdentifier(String playerIdentifier) {
        this.playerIdentifier = playerIdentifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
