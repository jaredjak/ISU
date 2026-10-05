package vehicle;

public class Semi extends Vehicle {
	private int hours;
	
	
	@Override
	public void drive (int miles) {
		if (miles >= 0) {
			this.hours += miles/55.0;
		}
	}

}
