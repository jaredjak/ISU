package test;

public class Test {
	int a = 2;
	int b = a + 1;

	public static void main(String[] args) {
		int answer = add(1, 2) + 3;
//		System.out.println(answer);
		
		int absVal = Math.abs(-3);
		
		int num1 = Integer.parseInt("100");
		int num2 = Integer.parseInt("100", 2);
		
		System.out.println(num1);
		System.out.println(num2);

	}
	
//	public static int add(int a, int b)	{
//		return a + b;
//		
//	}

	private static int add(int i, int j) {
		// TODO Auto-generated method stub
		return i + j;
	}

}
