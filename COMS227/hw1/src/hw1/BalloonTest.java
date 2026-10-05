package hw1;

public class BalloonTest {

	public static void main(String[] args) {
		Balloon b = new Balloon(20, 90);
		System.out.println("Test 1:");
		System.out.println("Fuel remaining is " + b.getFuelRemaining() + ". expected: 0");
		System.out.println("Adding 1000 to fuel.");
		b.setFuelRemaning(1000);
		System.out.println("Fuel remaining is " + b.getFuelRemaining() + " expected 1000");
		
		
		System.out.println("Test 2:");
		System.out.println("Air temp is " + b.getOutsideAirTemp() + " expected 20");
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 20");
		System.out.println("Fuel burn rate is " + b.getFuelBurnRate() + " expected 0");
		System.out.println("Tether length is " + b.getTetherLength() + " expected 0");
		System.out.println("Balloon mass is " + b.getBalloonMass() + " expected 0");
		
		b.setOutsideAirTemp(18);
		b.setBalloonTemp(25);
		b.setFuelBurnRate(5);
		b.setTetherLength(100);
		b.setBalloonMass(110);
		
		System.out.println("Air temp is " + b.getOutsideAirTemp() + " expected 18");
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 25");
		System.out.println("Fuel burn rate is " + b.getFuelBurnRate() + " expected 5");
		System.out.println("Tether length is " + b.getTetherLength() + " expected 100");
		System.out.println("Balloon mass is " + b.getBalloonMass() + " expected 110");
		
		
		System.out.println("Test 3:");
		System.out.println("Minutes is " + b.getMinutes() + " expected 0");
		System.out.println("Seconds is " + b.getSeconds() + " expected 0");
		b.update();
		b.update();
		b.update();
		System.out.println("Minutes is " + b.getMinutes() + " expected 0");
		System.out.println("Seconds is " + b.getSeconds() + " expected 3");
		
		
		System.out.println("Test 4:");
		System.out.println("Wind direction is " + b.getWindDirection() + " expected 90");
		b.changeWindDirection(180);
		System.out.println("Wind direction is " + b.getWindDirection() + " expected 270");
		b.changeWindDirection(90);
		System.out.println("Wind direction is " + b.getWindDirection() + " expected 0");
		b.changeWindDirection(-90);
		System.out.println("Wind direction is " + b.getWindDirection() + " expected 270");
		
		
		System.out.println("Test 5:");
		b.reset();
		System.out.println("Air temp is " + b.getOutsideAirTemp() + " expected 20");
		System.out.println("Wind direction is " + b.getWindDirection() + " expected 90");
		System.out.println("Fuel remaining is " + b.getFuelRemaining() + " expected: 0");
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 20");
		System.out.println("Fuel burn rate is " + b.getFuelBurnRate() + " expected 0");
		System.out.println("Tether length is " + b.getTetherLength() + " expected 0");
		System.out.println("Balloon mass is " + b.getBalloonMass() + " expected 0");
		
		
		
		System.out.println("Test 6:");
		b.setBalloonMass(100);
		b.setBalloonTemp(70);
		b.setFuelBurnRate(5);
		b.setFuelRemaning(10);
		b.setTetherLength(100);
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 70");
		System.out.println("Balloon velocity is " + b.getVelocity() + " expected 0");
		System.out.println("Altitude is " + b.getAltitude() + " expected 0");
		b.update();
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 70");
		System.out.println("Balloon velocity is " + b.getVelocity() + " expected 0.72");
		System.out.println("Altitude is " + b.getAltitude() + " expected 0.72");
		b.update();
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 70");
		System.out.println("Balloon velocity is " + b.getVelocity() + " expected 1.45");
		System.out.println("Altitude is " + b.getAltitude() + " expected 2.18");
		// Fuel is now gone. poof
		b.update();
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 65");
		System.out.println("Balloon velocity is " + b.getVelocity() + " expected 1.27");
		System.out.println("Altitude is " + b.getAltitude() + " expected 3.46");
		b.update();
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 60.5");
		System.out.println("Balloon velocity is " + b.getVelocity() + " expected 0.24");
		System.out.println("Altitude is " + b.getAltitude() + " expected 3.70");
		b.update();
		System.out.println("Balloon temp is " + b.getBalloonTemp() + " expected 56.45");
		System.out.println("Balloon velocity is " + b.getVelocity() + " expected -1.56");
		System.out.println("Altitude is " + b.getAltitude() + " expected 2.14");
		
		
		b.reset();
		b.setFuelRemaning(2);
		b.setTetherLength(100);
		b.setFuelBurnRate(5);
		b.setBalloonTemp(70);
		b.setOutsideAirTemp(20);
		b.setBalloonMass(100);
		b.update();
		System.out.println("Altitude is: " + b.getAltitude());
	}

}
