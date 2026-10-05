package genericCode;

public class GenericCode {

	/**
	 * Every 4 years is a leap year
	 * Except every 100 years is not a leap year
	 * Except every 400 years is a leap year
	 * @param year - year we are testing
	 * @return whether the year is a leap year or not 
	 */
	public static boolean isLeapYear(int year) {
		return ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)));
	}
	
	public static void main(String[] args) {
		int testYear = 2024;
		boolean expected = true;
		boolean actual = isLeapYear(testYear);
		System.out.println("Leap year test. Expected: " + expected + " Actual: " + actual);

	}

}
