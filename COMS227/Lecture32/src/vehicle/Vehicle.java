package vehicle;

public class Vehicle extends Object {
	private int odometer;
	private String name;
	private GasTank gasTank;
	
	public Vehicle() {
		this.gasTank = new GasTank();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (name.equals(((Vehicle)obj).getName()));
	}
	
	public void setName(String givenName) {
		name = givenName;
	}
	
	public String getName() {
		return name;
	}
	
	public void drive(int miles) {
		System.out.println("executing Vehicle drive method");
		// can't roll back the odometer
		if (miles >= 0) {
			odometer += miles;
		}
	}
	
	public int getOdometer() {
		return odometer;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + ", odometer: " + odometer;
	}

}
