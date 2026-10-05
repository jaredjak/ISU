package extra;

public class Submarine extends Watercraft implements Submersible {
//	private double direction = 0;
	private double depth = 0;

//	public void turn(double dirChange) {
//		direction += dirChange;
//	}

	public void adjustDepth(double depthChange) {
		depth += depthChange;
	}

	public String getStatus() {
		return super.getStatus() + " Depth: " + depth;
	}
}