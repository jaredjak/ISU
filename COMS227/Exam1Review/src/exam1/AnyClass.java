package exam1;

public class AnyClass {
	public static int findCopyCost(int numCopies) {
		double numC = (double)numCopies;
		if (numCopies <= 10) {
			numC *= 0.15;
		} else if (numCopies <= 100) {
			numC = 10 * 0.15 + (numC-10) * 0.12;
		} else {
			numC = 10*0.15 + 100*0.12 + (numC-110) * 0.08;
		}
		numC *= 100;
		int numC2 = (int)numC;
		return numC2;
	}
	
	public static void main(String[] args) {
		int cost = findCopyCost(150);
		System.out.println(cost);
	}
}
