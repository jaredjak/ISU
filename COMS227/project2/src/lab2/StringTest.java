package lab2;

public class StringTest {

	public static void main(String[] args) {
		// 2. Class 101
		String message = "Hello, World!";
		int theLength = message.length();
		System.out.println(theLength);
		
		// 3. Java API
		char theChar = message.charAt(3);
		System.out.println(theChar);

		theChar = message.charAt(12);
		System.out.println(theChar);
		
		System.out.println(message.toUpperCase());
		System.out.println(message.substring(0, 5));

	}

}
