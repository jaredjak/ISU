package extra;

public abstract class Aircraft extends Vehicle implements Flyable {
	private double altitude = 0;

	public void adjustAltitude(double altChange) {
		altitude += altChange;
	}
	
	public String getStatus() {
		return super.getStatus() + " Altitude: " + altitude;
	}
}