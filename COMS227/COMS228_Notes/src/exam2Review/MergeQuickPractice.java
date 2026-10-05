package exam2Review;

public class MergeQuickPractice {
	public static void main(String[] args) {
		int[] arr = {6,3,5,2,1,320,4,8,7,78,80,9};
		quickSort(arr);
		for (int i = 0; i < arr.length; i++) {
			if (i+1 == arr.length) {
				System.out.print(arr[i]);
			} else
				System.out.print(arr[i] + ", ");
		}
		
		System.out.println();
		
		int[] arr2 = {7,98,6,3,5,653,9,1,8,2,4,97};
		arr2 = mergeSort(arr2);
		System.out.println(arr2.length);
		for (int i = 0; i < arr2.length; i++) {
			if (i+1 == arr2.length) {
				System.out.print(arr2[i]);
			} else
				System.out.print(arr2[i] + ", ");
		}
	}
	
	
	public static void quickSort(int[] arr) {
		quickSortRec(arr, 0, arr.length-1);
	}
	
	public static void quickSortRec(int[] arr, int first, int last) {
		//base case
		if (first >= last) return;
		
		int p = partition(arr,first,last);
		
		quickSortRec(arr, first, p-1);
		quickSortRec(arr, p+1, last);
		
	}
	
	//arr[last]
//	public static int partition(int[] arr, int first, int last) {
//		int pivot = arr[last];
//		int i = first-1;
//		
//		for (int j = first; j < last; j++) {
//			if (arr[j] <= pivot) {
//				i++;
//				int temp = arr[i];
//				arr[i] = arr[j];
//				arr[j] = temp;
//			}
//		}
//		
//		int temp = arr[i+1];
//		arr[i+1] = arr[last];
//		arr[last] = temp;
//		
//		return i+1;
//	}
	
	//arr[first]
	public static int partition(int[] arr, int first, int last) {
		int pivot = arr[first];
		int i = first+1;
		
		for (int j = first+1; j <= last; j++) {
			if (arr[j] <= pivot) {
				
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
			}
		}
		
		int temp = arr[first];
		arr[first] = arr[i-1];
		arr[i-1] = temp;
		
		return i-1;
	}
	
	
//------------------------------------------------------------------------------------------------------
	
	
	
	public static int[] mergeSort(int[] arr) {
		int n = arr.length;
		
		//base case
		if (n <= 1) return arr;
		
		int middle = n/2;
		int[] left = new int[middle];
		int[] right = new int[n-middle];
		
		//make copies
		for (int i = 0; i < middle; i++) {
			left[i] = arr[i];
		}
		for (int i = middle; i < arr.length; i++) {
			right[i-middle] = arr[i];
		}
		
		left = mergeSort(left);
		right = mergeSort(right);
		
		arr = merge(left, right);
		
		return arr;
	}
	
	public static int[] merge(int[] left, int[] right) {
		int i = 0; int j = 0; int k = 0;
		int[] newArr = new int[left.length+right.length];
		
		while (i < left.length && j < right.length) {
			if (left[i] < right[j]) {
				newArr[k++] = left[i++];
			}
			else {
				newArr[k++] = right[j++];
			}
		}
		while (i < left.length) {
			newArr[k++] = left[i++];
		}
		while (j < right.length) {
			newArr[k++] = right[j++];
		}
		
		return newArr;
	}
}
