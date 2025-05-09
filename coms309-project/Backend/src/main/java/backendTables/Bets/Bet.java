package backendTables.Bets;

import backendTables.Users.User;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.lang.Math;
import javax.persistence.*;
import backendTables.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Bet { 
    private double amount;
    private String positionBet;
    private double mult = 1;
    private String username;
    private int betStreak;
    private int lobbyId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    public Bet() {

    }

    public Bet(double amount, String positionBet, double mult, String username, int lobbyId) {
        this.amount = amount;
        this.positionBet = positionBet;
        this.mult = mult;
        this.username = username;
        this.lobbyId = lobbyId;
    }

    //Calculate the bet amount based off user win streak (getStreakMult) and
    //the timeMult (based on the current odds of winning at time of the bet)
    //Return 0 if lost
    public double returnBet(Boolean win) {
        if (win) {
            double betWinnings =  this.amount * mult * getStreakMult()*1.5;
            this.betStreak++;
            return betWinnings;
        }
        this.betStreak = 0;
        return 0;
    }

    public int getId() {
        return this.id;
    }
    //Logarithmic function for the win streak multiplier bonus on bets
    public double getStreakMult() {
        return Math.log(betStreak+2)/Math.log(50) + 1;
    }

    public double getAmount() {return this.amount;}

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPositionBet() {
        return this.positionBet;
    }

    public int getBetStreak() {
        return this.betStreak;
    }

    public void setBetStreak(int i) {
        this.betStreak = i;
    }

    public double getMult(){
        return this.mult;
    }

    public void calcTimeMult() {
        this.mult *= this.mult;
        this.mult = this.mult/ 3600 * 1.5 + 1;
    }

    public String getUsername() {
        return this.username;
    }

    public int getLobbyId() {
        return this.lobbyId;
    }
}
