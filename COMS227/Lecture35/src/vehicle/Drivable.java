package vehicle;

public interface Drivable {
	
	/**
	 * Drive for a number of miles.
	 * @param miles
	 */
	public void drive(int miles);
	
	/**
	 * Drive for a number of miles for given hours.
	 * @param miles
	 * @param hours
	 */
	public void drive(int miles, int hours);
}
