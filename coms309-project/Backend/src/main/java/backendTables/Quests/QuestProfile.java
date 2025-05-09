package backendTables.Quests;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Entity
public class QuestProfile {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    private String username;

    // Tiers of each user's quests
    private boolean[] questStates = new boolean[3];

    // Counts of how many of each action the user has attained (used to determine how close to next tier)
    private int[] questCounts = new int[3];

    private String[] quests = {"Play 5 games", "Win 250 wormbucks from bets", "Score 500 points"};

    private int[] questChecks = {5, 250, 500};


    private LocalDateTime today;

    public QuestProfile(String username) {
        this.username = username;
        this.today = LocalDateTime.now();
    }

    public QuestProfile() {
        this.today = LocalDateTime.now();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int increaseCount(int index, int quantity) {
        questCounts[index] += quantity;

        // This int will be given as a completion reward.
        return tierCheck(index);
    }

    private int tierCheck(int index) {
        if (!questStates[index]) {
            if (questCounts[index] >= questChecks[index]) {
                questStates[index] = true;
                return 3;
            }
        }
        return 0;
    }

    public double[] toNextTier() {
        double[] percents = new double[this.questCounts.length];

        for (int i = 0; i < this.questChecks.length; i++) {
            percents[i] = (double)questCounts[i]/(double)questChecks[i];
            if (percents[i] > 1) percents[i] = 1;
        }

        return percents;
    }

    public void dailyReset() {
        for (int i = 0; i < this.questCounts.length; i++) {
            questCounts[i] = 0;
            questStates[i] = false;
            this.today = LocalDateTime.now();
        }
    }

    public void localDateCheck(LocalDateTime now) {
        if (now.getDayOfYear() > today.getDayOfYear() || now.getYear() > today.getYear()) {
            dailyReset();
        }
    }

    // For testing purposes
    public void revertDay() {
        this.today = LocalDateTime.now().minusDays(2);
    }

    public String getUsername() {
        return this.username;
    }

    public boolean[] getQuestStates() {
        return this.questStates;
    }

    public int[] getQuestCounts() {
        return this.questCounts;
    }

    public int[] getQuestChecks() {
        return this.questChecks;
    }

    public String[] getQuests() {
        return this.quests;
    }

}

