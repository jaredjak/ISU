package backendTables.Cosmetics;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;


@Entity
public class Marketplace {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id = 1;

    private final String[] skins = {"Red", "Orange", "Yellow", "Green",
            "Blue", "Purple", "Gold", "Beige", "Black", "Walter", "bullWorm", "House"};
    private double[] prices = {250, 500, 250, 750, 10000, 20000, 100000, 400, 1100, 1000000, 900000, 500000};
    private int[] available = {100, 100,100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
    private final double[] basePrices = {250.0 /1150, 500.0/1150, 250.0/1150, 750.0/1150, 10000.0/1150, 20000.0/1150,
            10000.0/1150, 400.0/1150, 1100.0/1150, 1000000.0/1150, 900000.0/1150, 500000.0/1150};
    public Marketplace() {
        setPrices();
    }

    public double[] getPrices() {
        return prices;
    }

    public void setPrices() {
        for (int i = 0; i < prices.length; i++) {
            prices[i] = Math.round(priceFunc(i, available[i]));
        }
    }

    public double priceFunc(int i, int count) {
        return basePrices[i] * Math.pow(Math.log(101-count) + 2,4);
    }

    public int getId() {
        return id;
    }


    private void setPrice(int i) {
        prices[i] = Math.round(priceFunc(i, available[i]));

    }


    public double buySkin(String color) {
        int id = getIndex(color);
        if (available[id] < 1) return 0;
        double p =  prices[id];
        available[id] -= 1;
        setPrice(id);
        return (Math.round(p));

    }

    public double sellSkin(String color) {
        int id = getIndex(color);
        if (available[id] > 100) return 0;
        available[id] += 1;
        setPrice(id);
        double p =  prices[id];
        return (Math.round(p));
    }

    public int getIndex(String color) {
        for (int i = 0; i < skins.length; i++) {
            if (skins[i].equals(color)) {
                return i;
            }
        }
        return -1;
    }

    public double getPrice(String color) {
        int i = getIndex(color);
        return Math.round(prices[i]);
    }

    public String[] getSkins() {
        return skins;
    }

    public int[] getAvailable() {
        return available;
    }




}
