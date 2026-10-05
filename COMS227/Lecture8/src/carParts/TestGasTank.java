//package carParts;
//
//// Figure out how to import JUnit 5
//
//import static org.junit.assertEquals;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.Before;
//
//public class TestGasTank {
//	private GasTank gasTank;
//	
//	@Before
//	public void setup() {
//		gasTank = new GasTank(10);
//	}
//	
//	@Test
//	public void testNewGasTank() {
//		assertEquals("Create tank with capacity 10. " + "getCapacity() should return 10", 10, gasTank.getCapacity());
//		// run it as a JUnit test
//	}
//	
//	@Test
//	public void testAdd() {
//		gasTank.add(5);
//		assertEquals("Add 5 gallons to gas tank. " + "getLevel() should return 5", 5, gasTank.getLevel());
//		// run it as a JUnit test
//	}
//	
//	public void testAddOverCapacity() {
//		gasTank.add(12);
//		assertEquals("Add 12 gallons to gas tank with capacity 10. " + "getLevel() should return 10", 10, gasTank.getLevel());
//		// run it as a JUnit test
//	}
//
//}
