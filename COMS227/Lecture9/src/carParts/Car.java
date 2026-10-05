package carParts;

public class Car {
	private static final double MILES_TO_KM = 1.60934;
	private GasTank gasTank;
	private double mpg;
	private double odometer;

	
	/**
	 * This constructor creates a new car object with a gas tank
	 * @param mpg - The fuel efficiency of the car 
	 * @param capacity - This is the amount of gas the gas tank can hold
	 */
	public Car(double mpg, int capacity) {
		gasTank = new GasTank(capacity);
		this.mpg = mpg;
	}
	
	/**
	 * This method returns the reading on the car's odometer
	 * @return The odometer value
	 */
	public double getOdometer() {
		return odometer;
	}
	
	/**
	 * This method returns the amount of the gas in the car's gas tank as a value between 0 and 1
	 * @return The level of the gas tank
	 */
	public double getGasGauge() {
		return gasTank.getLevel() / gasTank.getCapacity();
	}

	/**
	 * This method sets the car's gas tank level to its maximum level
	 */
	public void buyGas() {
		gasTank.add(gasTank.getCapacity() - gasTank.getLevel());
	}
	
	/**
	 * This method converts a number of miles into the equivalent amount of kilometers
	 * @param miles - The number of miles to be converted
	 * @return The number of kilometers for the number of miles
	 */
	public static double convertMilesToKm(double miles) {
		return miles * MILES_TO_KM;
	}
}
