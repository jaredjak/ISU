package newQuestions;

import java.util.ArrayList;

public class DivisibleByTarget {

	public static int[] divisibleByTarget(int[] arr, int target) {
		ArrayList<Integer> result = new ArrayList<>();
		helper(arr, target, 0, result);
		int[] resultArr = new int[result.size()];
		for (int i = 0; i < result.size(); i++) {
			resultArr[i] = result.get(i);
		}
		return resultArr;
		
	}
	
	private static void helper(int[] arr, int target, int index, ArrayList<Integer> result) {
		// base case
		if (index >= arr.length) {
			return;
		}
		
		if (arr[index] % target == 0) {
			result.add(arr[index]);
		}
		
		// recursion
		helper(arr, target, index + 1, result);
		
	}
}
