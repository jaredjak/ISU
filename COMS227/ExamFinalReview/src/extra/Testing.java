package extra;

//import java.util.ArrayList;

public class Testing {

	public static void main(String[] args) {
//		1 // works
//		Vehicle v1 = new Airship();
//		System.out.println(v1.getStatus());

//		2 // works
//		Submarine v2 = new Submarine();
//		v2.adjustDepth(-10);
//		v2.turn(15);
//		System.out.println(v2.getStatus());

//		3  // compiler
//		Vehicle v3 = new Glider();
//		v3.adjustAltitude(25); // vehicle can't access adjustAltitude
//		v3.turn(5);
//		System.out.println(v3.getStatus());

//		4 // works
//		Aircraft v4 = new Airship();
//		v4.adjustAltitude(100);
//		v4.turn(-20);
//		System.out.println(v4.getStatus());

//		5 // compiler
//		Submarine j = new Submarine();
//		Vehicle k = j;
//		Submarine h = k; // Vehicle can't be set equal to a submarine but a submarine can be set equal to a vehicle
//		h.adjustDepth(-5);
//		h.turn(-25);
//		System.out.println(h.getStatus());

//		6 // works
//		Vehicle x = new Airship();
//		Aircraft y = (Aircraft) x;
//		y.adjustAltitude(200);
//		x.turn(60);
//		System.out.println(x.getStatus());

//		7 // exception
//		Vehicle v = new Airship();
//		Submarine m = (Submarine) v; // ClassCast Exception. Airship cannot be cast to submarine
//		m.adjustDepth(-40);
//		m.turn(40);
//		System.out.println(m.getStatus());

//		8 // works
//		ArrayList<Flyable> fleet = new ArrayList<Flyable>();
//		fleet.add(new Airship());
//		fleet.add(new Glider());
//		for (Flyable fly : fleet) {
//			Aircraft craft = (Aircraft) fly;
//			System.out.println(craft.getStatus());
//		}
		
//		9 // compiler error
//		Vehicle p = new Vehicle(); // Vehicle is abstract, cannot instantiate it
//		System.out.println(p.getStatus());

	}
}
