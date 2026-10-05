package conditionals3;

public class Expressions {
	
	/*
	 * Understand the ideas of idempotence
	 */

	public static boolean updateA() {
		System.out.println("Update A ran...");
		return true;
	}
	public static boolean updateB() {
		System.out.println("Update B ran...");
		return true;
	}
	
	public static void main(String[] args) {
//		int a = 3;
//		int b = 2;
//		int c = 1;
//		
//		if (a > b) {
//			if (b > c) {
//				System.out.println("Both true");
//			}
//		}
//		
//		if (a > b && b > c) {
//			System.out.println("Both true");
//		}
//		
//		if (a > b || b > c) {
//			System.out.println("Or Test - true");
//		}
//		
//		if (a > b) {
//			System.out.println("a > b");
//		}
//		else if (b > c){
//			System.out.println(" b > c");
//		}
		
		boolean isA = updateA();
		boolean isB = updateB();
		
		if (isA && isB) {
			System.out.println("Both updates successful");
		}

	}

}
