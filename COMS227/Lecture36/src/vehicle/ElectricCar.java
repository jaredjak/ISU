package vehicle;

public class ElectricCar extends Car implements Chargeable {

	private Battery battery;
	
	public ElectricCar(int kwh) {
		this.battery = new Battery(kwh);
	}
	
	@Override
	public void charge(int hours) {
		this.battery.charge(hours);
	}

}
