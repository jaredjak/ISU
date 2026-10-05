package hw1;
/**
 * Class balloon represents a hot air balloon. Provided within the class is an assortment of variables
 * and methods that allow for the balloon to both rise and fall given the previously mentioned 
 * variables and methods
 * @author Jared Krug
 */
public class Balloon {
	/**
	 * The HEAT_LOSS constant represents the heat loss factor
	 */
	private final double HEAT_LOSS = 0.1;
	/**
	 * The BLN_VOLUME constant represents the volume of air in the balloon in m^3 
	 * *** m = balloonMass
	 */
	private final double BLN_VOLUME = 61234;
	/**
	 * The GRAV_ACCEL constant represents the acceleration due to gravity in meters per second squared
	 */
	private final double GRAV_ACCEL = 9.81;
	/**
	 * The GAS_CONST constant represents the gas constant of Joule / (kilogram * Kelvins) (I think)
	 */
	private final double GAS_CONST = 287.05;
	/**
	 * The STAN_PRSR constant represents the standard pressure in hectoPascals
	 */
	private final double STAN_PRSR = 1013.25;
	/**
	 * The KELVINS_0C constant represents what Kelvins equal at 0 degrees celsius
	 */
	private final double KELVINS_0C = 273.15;
	
	
	/**
	 * The airTemp variable represents the airs temperature
	 */
	private double airTemp;
	/**
	 * The windDirection variable represents the current wind direction
	 */
	private double windDirection;
	/**
	 * The balloonTemp variable represents the balloons temperature
	 */
	private double balloonTemp;
	/**
	 * The simTime variable represents the current simulation's time 
	 * *** Used for getting both the minutes and seconds
	 */
	private long simTime;
	/**
	 * The altitude variable represents the altitude of the balloon
	 */
	private double altitude;
	/**
	 * The fuelRemaining variable represents the fuel left to keep the balloon up
	 */
	private double fuelRemaining;
	/**
	 * The burnRate variable represents the burn rate that the balloon takes
	 * *** Burns up fuel
	 */
	private double burnRate;
	/**
	 * The balloonMass variable represents the balloon's mass
	 */
	private double balloonMass;
	/**
	 * The velocity variable represents the velocity of the balloon's movement
	 */
	private double velocity;
	/**
	 * The tetherLength variable represents the length of the tether
	 */
	private double tetherLength;
	/**
	 * The originalAirTemp variable is a placeholder for the original air temperature
	 */
	private double originalAirTemp;
	/**
	 * The originalWindDirection variable is a placeholder for the original wind direction
	 */
	private double originalWindDirection;
	
	
	/**
	 * The changeAirTemp variable represents the rate of change in the balloon's air temperature per second 
	 */
	private double changeAirTemp;
	/**
	 * The airDensity variable represents the density of the outside air
	 */
	private double airDensity;
	/**
	 * The balloonDensity variable represents the density of the balloon air
	 */
	private double balloonAirDensity;
	/**
	 * The liftForce variable represents the force of the lift in N
	 */
	private double liftForce;
	/**
	 * The gravityForce variable represents the force of gravity in N
	 */
	private double gravityForce;
	/**
	 * The netForce variable represents the net force in upward direction in N
	 */
	private double netForce;
	/**
	 * The netAcceleration variable represents the net acceleration in upward direction
	 */
	private double netAcceleration;
	
	
	/**
	 * The Balloon constructor creates a new balloon taking in airTemp and windDirection
	 * The constructor sets the original air temperature and wind direction to their own variables for later use
	 * The constructor sets mentioned variables to 0 as place-holders
	 * @param airTemp - The temperature of the air outside
	 * @param windDirection - The direction of the wind
	 */
	public Balloon(double airTemp, double windDirection) {
		this.airTemp = airTemp;
		this.windDirection = windDirection;
		
		originalAirTemp = airTemp;
		originalWindDirection = windDirection;
		
		balloonTemp = airTemp;
		simTime = 0;
		altitude = 0;
		fuelRemaining = 0;
		burnRate = 0;
		balloonMass = 0;
		velocity = 0;
		tetherLength = 0;
	}
	
	/**
	 * The getFuelRemaining method gets the remaining fuel of the balloon
	 * @return fuelRemaining - the remaining fuel for the balloon
	 */
	public double getFuelRemaining() {
		return fuelRemaining;
	}
	
	/**
	 * The setFuelRemaning method sets the fuelReamining variable to the fuel parameter
	 * @param fuel - the fuel used for the balloon
	 */
	public void setFuelRemaning(double fuel) {
		fuelRemaining = fuel;
	}
	
	/**
	 * The getBalloonMass method gets the mass of the balloon
	 * @return balloonMass - mass of the balloon
	 */
	public double getBalloonMass() {
		return balloonMass;
	}
	
	/**
	 * The setBalloonMass method sets the mass of the balloon to the fuel parameter as it depends on the amount of fuel
	 * @param fuel - the fuel used for the balloon
	 */
	public void setBalloonMass(double fuel) {
		balloonMass = fuel;
	}
	
	/**
	 * The getOutsideAirTemp method gets the outside air temperature
	 * @return airTemp - outside air temperature
	 */
	public double getOutsideAirTemp() {
		return airTemp;
	}
	
	/**
	 * The setOutsideAirTemp method sets the outside air temperature using the temp parameter
	 * @param temp - temperature of the air
	 */
	public void setOutsideAirTemp(double temp) {
		airTemp = temp;
	}
	
	/**
	 * The getFuelBurnRate method gets the fuel's burn rate
	 * @return burnRate - the rate the fuel burns
	 */
	public double getFuelBurnRate() {
		return burnRate;
	}
	
	/**
	 * The setFuelBurnRate method sets the rate the fuel burns
	 * @param rate - the rate of burning
	 */
	public void setFuelBurnRate(double rate) {
		burnRate = rate;
	}
	
	/**
	 * The getBalloonTemp method gets the balloon's temperature
	 * @return balloonTemp - the balloon's temperature
	 */
	public double getBalloonTemp() {
		return balloonTemp;
	}
	
	/**
	 * The setBalloonTemp method sets the temperature of the balloon's temperature
	 * @param temp - temperature of the balloon's air
	 */
	public void setBalloonTemp(double temp) {
		balloonTemp = temp;
	}
	
	/**
	 * The getVelocity method gets the velocity of the balloon
	 * @return velocity - the balloon's velocity
	 */
	public double getVelocity() {
		return velocity;
	}
	
	/**
	 * The getAltitude method gets the altitude of the balloon
	 * @return altitude - the balloon's altitude
	 */
	public double getAltitude() {
		return altitude;
	}
	
	/**
	 * The getTetherLength method gets the length of the tether
	 * @return tetherLength - length of the tether
	 */
	public double getTetherLength() {
		return tetherLength;
	}
	
	/**
	 * The getTetherRemaining method gets the remaining tether
	 * @return tetherLength - altitude = the tether leftover
	 */
	public double getTetherRemaining() {
		return tetherLength - altitude;
	}
	
	/**
	 * The setTetherLength method sets the length of the tether
	 * @param length - length of the tether
	 */
	public void setTetherLength(double length) {
		tetherLength = length;
	}
	
	/**
	 * The getWindDirection method gets the wind direction (0 inclusive to 360 exclusive)
	 * @return windDirection - direction of the wind
	 */
	public double getWindDirection() {
		return windDirection;
	}
	
	/**
	 * The changeWindDirection method changes the wind direction using an implemented formula (0 inclusive to 360 exclusive)
	 * @param deg - degree of the wind (360 degrees)
	 */
	public void changeWindDirection(double deg) {
		windDirection = ((windDirection % 360) + (deg + 360)) % 360;
	}
	
	/**
	 * The getMinutes method takes simTime divided by 60 to get the minutes the simulation has been running
	 * @return (simTime / 60) - minutes the simulation has been running
	 */
	public long getMinutes() {
		return (simTime / 60);
	}
	
	/**
	 * The getSeconds method takes simTime modulus 60 to get the seconds the simulation has been running
	 * @return (simTime % 60) - seconds (0-59) the simulation has been running
	 */
	public long getSeconds() {
		return (simTime % 60);
	}
	
	/**
	 * The update method updates the entire simulation using a vast majority of formulas
	 * using previously mentioned variables and methods
	 */
	public void update() {
		simTime += 1;
		burnRate = Math.min(fuelRemaining, burnRate);
		fuelRemaining -= burnRate;		
		changeAirTemp = burnRate + (airTemp - balloonTemp) * HEAT_LOSS;
		balloonTemp = balloonTemp + changeAirTemp;
		airDensity = STAN_PRSR / (GAS_CONST * (airTemp + KELVINS_0C));
		balloonAirDensity = STAN_PRSR / (GAS_CONST * (balloonTemp + KELVINS_0C));
		liftForce = BLN_VOLUME * (airDensity - balloonAirDensity) * GRAV_ACCEL;
		gravityForce = balloonMass * GRAV_ACCEL;
		netForce = liftForce - gravityForce;
		netAcceleration = netForce / balloonMass;
		velocity += netAcceleration;
		altitude += velocity;
		
		altitude = Math.max(altitude, 0);
		altitude = Math.min(altitude, tetherLength);
	}
	
	/**
	 * The reset method resets all necessary items to their original values from the constructor
	 */
	public void reset() {
		airTemp = originalAirTemp;
		windDirection = originalWindDirection;
		balloonTemp = airTemp;
		simTime = 0;
		altitude = 0;
		fuelRemaining = 0;
		burnRate = 0;
		balloonMass = 0;
		velocity = 0;
		tetherLength = 0;
	}

}