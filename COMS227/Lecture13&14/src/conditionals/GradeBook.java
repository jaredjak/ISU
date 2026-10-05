package conditionals;

public class GradeBook {
	public String letterGrade (int percent) {
		String letter = "";
		
		if (percent >= 90) {
			letter = "A";
		} else if (percent >= 80) {
			letter = "B";
		} else if (percent >= 70) {
			letter = "C";
		} else if (percent >= 60) {
			letter = "D";
		} else {
			letter = "F";
		}
		
		return letter;
	}
	
	public int getScore(String letter) {
		int score = 0;
		
		if (letter.equals("A")) {
			score = 10;
		} else if (letter.equals("B")) {
			score = 9;
		} else if (letter.equals("C")) {
			score = 8;
		} else if (letter.equals("D")) {
			score = 7;
		}	
		
		return score;
	}

}
