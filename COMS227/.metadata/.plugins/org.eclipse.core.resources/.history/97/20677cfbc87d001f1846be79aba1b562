package exam1Review;

public class selectionSort {
	
	/** Selection Sort (How it works):
	 * 		Iterates through array starting at index 0, assigning the minimum index to that index.
	 * 		Then, iterates through the rest of the remaining array
	 * 		In each iteration, if the current int is less than the int at minIndex, then minIndex is assigned to the new int
	 * 		When that is done, then a 3 line structure is used to change both the ints at minIndex and the current element at i
	 * 
	 */
	

	public static void selectionSort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			int temp = arr[i];
			arr[i] = arr[minIndex];
			arr[minIndex] = temp;
			
			
			// Test how it prints out
			for (int k = 0; k < arr.length; k++) {
				System.out.print(arr[k] + " ");
			}
			System.out.println();
		}
	}


	public static void main(String[] args) {
		int[] arr1 = {1,4,5,2,3};
		selectionSort(arr1);
		System.out.println();
		int[] arr2 = {4,3,5,1,2};
		selectionSort(arr2);
	}
}