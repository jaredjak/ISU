package test;

public class Tester {
	private int a;
	private static int b;

	public Tester(int a) {
		this.a = a;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public static int getB() {
		return b;
	}

	public static void setB(int b) {
		Tester.b = b;
	}

}
