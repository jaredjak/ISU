package vehicle;

public class Vehicle implements Drivable, Steerable {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void drive(int miles, int hours) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drive(int miles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turn(int degrees) {
		// TODO Auto-generated method stub
		
	}
}
