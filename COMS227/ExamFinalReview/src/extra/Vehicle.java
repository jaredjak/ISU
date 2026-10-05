package extra;


public abstract class Vehicle implements Steerable {
	private double direction = 0;

	public void turn(double dirChange) {
		direction += dirChange;
	}
	public String getStatus() {
		return "Direction: " + direction;
	}
}
