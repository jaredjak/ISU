package backendTables.Cosmetics;
import backendTables.Users.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
@Entity
public class Wormbox {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @OneToOne(mappedBy = "w")
    private Cosmetics cosmetics;
    private String[] userSkins = {"Red", "Orange", "Yellow", "Green",
            "Blue", "Purple", "Gold", "Beige", "Black", "Walter", "Bullworm", "House"};
    private int[] count = new int[12];
    private int len;

    public Wormbox(Cosmetics c) {
        this.cosmetics = c;
        count[7] = 1;
        this.len = 1;
    }

    public Wormbox() {

    }

    public int indexOf(String color) {
        for (int j = 0; j < userSkins.length; j++) {
            if (userSkins[j].equals(color)) {
                return j;
            }
        }
        return -1;
    }

    public String getWorm(String color) {
        int i = indexOf(color);
        if (i == -1) return null;
        return userSkins[i];
    }

    public String[] getAllWorms() {
        return userSkins;
    }

    @Transactional
    public void addWorm(String s) {
        int i = indexOf(s);
        if (i == -1) return;
        len++;
        count[i]++;
    }

    @Transactional
    public void removeWorm(String s) {
        int i = indexOf(s);
        if (i > -1 && len > 1) {
            len--;
            if (count[i] > 0) count[i]--;
        }
    }

    public boolean contains(String s) {
        int i = indexOf(s);
        return (i > -1 && count[i] > 0);
    }

    public int[] getCounts() {
        return count;
    }

    public int getLen() {
        return this.len;
    }

}