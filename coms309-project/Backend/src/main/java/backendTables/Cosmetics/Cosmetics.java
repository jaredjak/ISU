package backendTables.Cosmetics;
import javax.persistence.*;

import backendTables.Users.User;

import java.util.ArrayList;


@Entity
public class Cosmetics {
    public Cosmetics() {

    }
    //TODO Add selected attribute
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String username;

    private String selected;

    private double wormBucks;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "wormbox_id")
    private Wormbox w;
    private double previousBet = 0;

    public Cosmetics (User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.wormBucks = 1000;
        this.selected = "Beige";
        this.w = new Wormbox(this);
    }

    public Wormbox getWormbox() {
        return w;
    }

    public void addWorm(String s) {
        w.addWorm(s);
    }

    public void removeWorm(String s) {
        w.removeWorm(s);
    }

    public String viewSkin(String color) {
        return w.getWorm(color);
    }

    public int[] getCounts() {
        return w.getCounts();
    }

    public void adjustBalance(double amount) {
        if (wormBucks + amount < 0) wormBucks = 0;
        else {
            wormBucks += amount;
        }
    }

    public double getBalance() {
        return wormBucks;
    }

    public int getId() {
        return this.id;
    }

    public double getPrevBet() {
        return this.previousBet;
    }

    public void setPrevBet(double i) {
        this.previousBet = i;
    }

    public boolean select(String worm) {
        if (w.contains(worm)) {
            this.selected = worm;
            return true;
        }
        return false;
    }

    public String getSelected() {
        return this.selected;
    }

    public int getTotalWormCount() {
        return w.getLen();
    }


}

