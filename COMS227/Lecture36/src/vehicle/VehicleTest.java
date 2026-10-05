package vehicle;

import java.util.ArrayList;

public class VehicleTest {

	public static void main(String[] args) {
		ArrayList<Chargeable> chargeableThings = new ArrayList<>();
		
		chargeableThings.add(new ElectricCar(10000));
		chargeableThings.add(new ElectricTruck(10000));
		chargeableThings.add(new Battery(10000));
		chargeableThings.add(new Phone(10));
		
		for (Chargeable chargeableThing : chargeableThings) {
			chargeableThing.charge(10);
		}

	}

}
