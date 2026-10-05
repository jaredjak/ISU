package arrays;

public class Arrays {

	public static void printReverse(int[] arr) {
		for (int i = arr.length - 1; i >= 0; i--) {
			System.out.println(arr[i]);
		}
	}
	
	public static int[] reverse(int[] arr) {
		int[] rev = new int[arr.length];
		
		for (int i = 0; i < arr.length; i++) {
			rev[i] = arr[arr.length - 1 - i];
		}
		
		return rev;
	}
	
	public static void main(String[] args) {
		int[] ids = {1,2,3,4,5};
//		ids = new int[5];
		
		String[] names = {"Samuel", "Leonard", "Isaac"};
		
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
		}
		
		System.out.println();
		
		for (int i = 0; i < names.length; i++) {
			System.out.println(names[i]);
		}
		
		System.out.println();
		
		printReverse(ids);
		
		System.out.println();

		ids = reverse(ids);
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
		}
	}
}
