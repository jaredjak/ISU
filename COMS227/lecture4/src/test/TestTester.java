package test;

public class TestTester {

	public static void main(String[] args) {
		Tester t1 = new Tester(1);
		Tester t2 = new Tester(2);
		
		System.out.println(t1.getA() + " " + t2.getA());
		System.out.println(Tester.getB() + " " + Tester.getB());
		
		t1.setA(6);
		t2.setA(27);
		
		Tester.setB(34);
		Tester.setB(12);
		
		System.out.println(t1.getA() + " " + t2.getA());
		System.out.println(Tester.getB() + " " + Tester.getB());
	}

}
