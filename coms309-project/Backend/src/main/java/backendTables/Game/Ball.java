package backendTables.Game;

import java.util.Random;

public class Ball {
    private double x;
    private double y;
    private double vx;
    private double vy;

    public Ball(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setRandomVelocity(Random random) {
        // Speed between 50 and 100
        double speedX = 50 + (25 * random.nextDouble()); // 50 = 100 - 50
        double speedY = 50 + (25 * random.nextDouble());

        // Randomize direction
        this.vx = random.nextBoolean() ? speedX : -speedX;
        this.vy = random.nextBoolean() ? speedY : -speedY;
    }



    public void updatePosition() {
        this.x += vx;
        this.y += vy;
    }

    public void reverseXVelocity() {
        this.vx = -this.vx;
    }

    public void reverseYVelocity() {
        this.vy = -this.vy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
