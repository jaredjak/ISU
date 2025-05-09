package backendTables.Achievements;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
public class Achievements {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @Getter
    private String username;

    // Tiers of each user's achievements
    private final int[] achievementsStates = new int[16];

    // Counts of how many of each action the user has attained (used to determine how close to next tier)
    private final int[] achievementCounts = new int[16];

    // Used for tier checking on bets, game wins/streaks, and skin counts
    private final int[] nonMoneyTiers = {5, 25, 50, 100, 1000};

    // Used for tier checking on winning/losing wormbuck achievements
    private final int[] moneyTiers = {100, 1000, 50000, 2500000, 1000000};

    private String selected = "Grub";

    @Getter
    private int tierSelected = 1;

    public Achievements(String username) {
        this.username = username;
        increaseCount(15, 1);
    }

    public Achievements() {
        increaseCount(15, 1);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void increaseCount(int index, int quantity) {
        this.achievementCounts[index] += quantity;
        if (index > 8 && this.achievementCounts[index] >= 1) {
            this.achievementsStates[index] = 1;
            return;
        }
        tierCheck(index);
    }

    private void tierCheck(int index) {
        // If the index is 4 or 5 it's a bet quantity achievement
        // Use a separate tier array
        int tier = 0;
        if (index == 4 || index == 5) {
            for (int i = 0; i < this.moneyTiers.length; i++) {
                if (this.achievementCounts[index] >= this.moneyTiers[i] || this.achievementsStates[index] >= i+1) {
                    tier++;
                }
                else {
                    break;
                }
            }

        }
        // Checks all other tiered achievements
        else {
            for (int i = 0; i < this.nonMoneyTiers.length; i++) {
                if (this.achievementCounts[index] >= this.nonMoneyTiers[i] || this.achievementsStates[index] >= i+1) {
                    tier++;
                }
                else {
                    break;
                }
            }
        }
        // After these, return the new tier update
        this.achievementsStates[index] = tier;
    }

    public double[] toNextTier() {
        double[] percentComplete = new double[16];
        for (int i = 0; i < this.achievementCounts.length; i++) {
            if (i > 8) {
                percentComplete[i] = this.achievementsStates[i];
            }
            else if (i == 4 || i == 5) {
                int nextTier = this.moneyTiers[0];
                for (int j = 0; j < this.moneyTiers.length-1; j++) {
                    if (this.achievementCounts[i] >= this.moneyTiers[j]) {
                        nextTier = this.moneyTiers[j+1];
                    }
                    else {
                        break;
                    }
                }
                percentComplete[i] = (double) this.achievementCounts[i] / nextTier;
                if (percentComplete[i] > 1) percentComplete[i] = 1;
            }
            else {
                int nextTier = this.nonMoneyTiers[0];
                for (int j = 0; j < this.nonMoneyTiers.length-1; j++) {
                    if (this.achievementCounts[i] >= this.nonMoneyTiers[j]) {
                        nextTier = this.nonMoneyTiers[j+1];
                    }
                    else {
                        break;
                    }
                }
                percentComplete[i] = (double) this.achievementCounts[i] / nextTier;
                if (percentComplete[i] > 1) percentComplete[i] = 1;
            }
        }
        return percentComplete;
    }

    public String getSelected() {
        return this.selected;
    }


    public boolean setSelected(int index, String[] untieredNames) {
        if (this.achievementsStates[index] == 0) return false;
        this.selected = untieredNames[index];
        this.tierSelected = achievementsStates[index];
        return true;
    }

    public int getTier() {
        return this.tierSelected;
    }

    // Used to reset the streak count. Note this doesn't affect the already attained tier
    public void resetCount(int index) {
        this.achievementCounts[index] = 0;
    }

    public int[] getAchievementStates() {
        return this.achievementsStates;
    }

    public int[] getAchievementCounts() {
        return this.achievementCounts;
    }

    public int[] getNonMoneyTiers() {
        return this.nonMoneyTiers;
    }

    public int[] getMoneyTiers() {
        return this.moneyTiers;
    }

}
