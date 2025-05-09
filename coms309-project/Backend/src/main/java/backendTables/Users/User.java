package backendTables.Users;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 * @author
 *
 */

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String emailId;
    private String password;
    private int ssn;
    private int clubId = 0;

    private int betStreak = 0;
     // =============================== Constructors ================================== //


    public User(String name, String emailId, int ssn, String password) {
        // int id needs to have a proper process of selection

        this.username = name;
        this.emailId = emailId;
        this.ssn = ssn;
        this.password = password;
    }

    public User() {

    }


    // =============================== Getters and Setters for each field ================================== //

    public int getSSN() {
        return this.ssn;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.emailId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setSSN(int newSSN) {
        this.ssn = newSSN;
    }

    public void setUsername(String newName) {
        this.username = newName;
    }

    public void setEmail(String email) {
        this.emailId = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setClubId(int i) {
        this.clubId = i;
    }

    public int getClubId() {
        return this.clubId;
    }

    public void setBetStreak(boolean win) {
        if (win) {
            betStreak++;
        }
        else {
            betStreak = 0;
        }
    }

    public int getBetStreak() {
        return betStreak;
    }

}
