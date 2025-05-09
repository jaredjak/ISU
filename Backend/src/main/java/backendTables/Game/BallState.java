package backendTables.Game;

public class BallState {
    private final double x;
    private final double y;

    public BallState(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
