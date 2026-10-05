package exam1;

public class MyAnswers {
	//1.
	//a) int fullCartons = eggCount / 12;
	//b) int leftOver = eggCount % 12;
	//c) char lastChar = str.charAt(str.length() - 1);
	//d) boolean areTheSameString = s1.equals(s2);
	//e) boolean hasAtLeastFour = s.length() >= 4;
	//f) double totalValue = (d * 100 + c) / 100.0;
	//g) int(d);
	
	//2.
	//Line 1: 20
	//Line 2: 0
	//Line 3: 15
	//Line 4: 25
	
	//3.
	//a)
//	public class MeterTest {
//		public static void main(String[] args) {
//			ParkingMeter pm = new ParkingMeter(15, 60);
//			pm.insertCoin(3);
//			pm.passTime(20);
//			System.out.println("Actual time remaining: " + pm.getTimeRemaining());
//			System.out.println("Expected time remaining: 40");
//			System.out.println("Actual total amount: " + pm.getTotal());
//			System.out.println("Expected total amount: .75");
//			pm.insertCoin(4);
//			pm.passTime(90);
//			System.out.println("Actual time remaining: " + pm.getTimeRemaining());
//			System.out.println("Expected time remaining: 0");
//			System.out.println("Actual total amount: " + pm.getTotal());
//			System.out.println("Expected total amount: 1.75");
//		}
//	}
//	//b)
//	public class ParkingMeter {
//		
//		private int minutesPerQuarter;
//		private int maximumTime;
//		private int quarterCount;
//		private int timeRemaining;
//		
//		public ParkingMeter(int minutesPerQuarter, int maximumTime) {
//			this.minutesPerQuarter = minutesPerQuarter;
//			this.maximumTime = maximumTime;
//			quarterCount = 0;
//			timeRemaining = 0;
//		}
//		public void insertCoin(int howMany) {
//			quarterCount += howMany;
//			timeRemaining = Math.min(timeRemaining + minutesPerQuarter*howMany, maximumTime );
//		}
//		public int getTimeRemaining() {
//			return timeRemaining;
//		}
//		public void passTime(int minutes) {
//			timeRemaining = Math.max(timeRemaining - minutes, 0);
//		}
//		public double getTotal() {
//			return quarterCount * 0.25;
//		}
//	}
	
	//4.
//	import java.util.Scanner;
//	public class TimeConverter {
//		public static void main(String[] args) {
//			// TODO: your code here
//			Scanner scnr = new Scanner(System.in);
//			System.out.print("How many meters? ");
//			double meters = scnr.nextDouble();
//			System.out.print("Convert to cm or km? ");
//			String s = scnr.next();
//			if (s.equals("cm")) {
//				double cm = meters * 100;
//				System.out.println("The measurement is " + cm + " " + s);
//			} else if (s.equals("km")) {
//				double km = meters / 1000;
//				System.out.println("The measurement is " + km + " " + s);
//			} else {
//				System.out.println("The requested conversion is not possible.");
//			}
//		}
//	}
	
	//5. 
//	import java.util.Random;
//	public class SomeClass {
//		public static String guessBirthday() {
//			//TODO: your code here
//			Random rand = new Random();
//			int day = rand.nextInt(30) + 1;
//			int year = rand.nextInt(100) + 1900;
//			return "September " + day + ", " + year;
//		}
//	}
	
	//6. 
	//a) same
	//b) different
	//c) same
	//d) same
	//e) different
	//f) different
	//g) same
	//h) same
	
	//continued
//	public boolean foo(int x, int y) {
//		return (x > 0 && (y/x == 2 || y/x == 3));
//	}

}
