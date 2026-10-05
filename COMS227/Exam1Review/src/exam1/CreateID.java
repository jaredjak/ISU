package exam1;

import java.util.Random;

public class CreateID {
	public static String createID(String firstName, String lastName) {
		String firstInitial = firstName.substring(0,1);
		String firstUnder = firstInitial.toLowerCase();
		
		String lastUnder = lastName.toLowerCase();
		
		Random rand = new Random();
		int randomNum = rand.nextInt(50) + 1;
		return firstUnder + lastUnder + randomNum;
	}
	
	public static void main(String[] args) {
		String fName = "Bob";
		String lName = "Marley";
		String newPass = createID(fName, lName);
		System.out.println(newPass);
	}

}
