// backendTables.Game.GameSnapshot.java
package backendTables.Game;

import java.util.List;

public class GameSnapshot {
    private List<PlayerScore> scores;

    public GameSnapshot(List<PlayerScore> scores) {
        this.scores = scores;
    }

    public List<PlayerScore> getScores() {
        return scores;
    }

    public static class PlayerScore {
        private String playerIdentifier;
        private int score;

        public PlayerScore(String playerIdentifier, int score) {
            this.playerIdentifier = playerIdentifier;
            this.score = score;
        }

        public String getPlayerIdentifier() {
            return playerIdentifier;
        }

        public int getScore() {
            return score;
        }
    }
}

