package vehicle;

import java.util.ArrayList;

public class TestVehicle {

	public static void driveDistance(Drivable drivableThing, int miles) {
		drivableThing.drive(miles);
	}
	
	public static void main(String[] args) {
//		Vehicle myVehicle = new Vehicle(); //no work
		
		Car myCar = new Car();
		Truck myTruck = new Truck();
		Vehicle myVehicle;
		
		myVehicle = myCar;
		myVehicle.drive(10);
		
		myVehicle = myTruck;
		myVehicle.drive(10);
		
		driveDistance(myCar, 20);
		driveDistance(myTruck, 20);
		
		Plane myPlane = new Plane();
		driveDistance(myPlane, 10);
		
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(myCar);
		vehicles.add(myTruck);
		for (Vehicle v : vehicles) {
			v.drive(100);
		}
		
		ArrayList<Drivable> vehicles2 = new ArrayList<>();
		vehicles2.add(myCar);
		vehicles2.add(myTruck);
		vehicles2.add(myPlane);
		for (Drivable v : vehicles2) {
			v.drive(100);
		}
		
	}

}
