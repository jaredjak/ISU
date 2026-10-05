package vehicle;

public class vehicleTest {

	public static void main(String[] args) {
//		//Instead of using Vehicle you can use Car because it extends Vehicle 
//		Car myGreenSubaru = new Car();
//		myGreenSubaru.setName("Greeny");
//		myGreenSubaru.drive(10);
//		myGreenSubaru.setSeats(4);
//		System.out.println(myGreenSubaru);
		
		floorCell myCell = new floorCell();
		// this is true because of the Override
		if (myCell.canPlaceBlock()) {
			System.out.println("Placing Block");
		}
		

	}

}
