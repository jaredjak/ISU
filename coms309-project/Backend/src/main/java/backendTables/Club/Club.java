package backendTables.Club;

import javax.persistence.*;

import backendTables.Users.User;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Table(name = "CLub")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;
    @Column
    private ArrayList<String> users = new ArrayList<String>();
    private int memberCount = 0;

    public Club() {

    }

    public Club(String name, User firstmember) {
        this.users.add(firstmember.getUsername());
        this.name = name;
        this.memberCount = 1;
    }

    public int getId() {
        return id;
    }

    public String getClubName() {
        return name;
    }

    public void setClubName(String name) {
        this.name = name;
    }

    public boolean addUser(User u) {
        if (u.getClubId() != 0) {
            return false;
        }
        users.add(u.getUsername());
        memberCount++;
        return true;
    }

    public boolean removeUser(User u) {
        if (u.getClubId() == this.getId()) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equals(u.getUsername())) {
                    users.remove(i);
                    memberCount--;
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getUsers() {
        return this.users;
    }

    public int getMemberCount() {return this.memberCount;}
}
