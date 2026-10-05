package exam2Review;

public class mergeQuickSort {

	public static void main(String[] args) {
		int[] points = {45,3,21,756,43,2,87};
		points = mergeSort(points);
		for (int i = 0; i < points.length; i++) {
			if (i+1 == points.length) {
				System.out.print(points[i]);
			} else {
				System.out.print(points[i] + ", ");
			}
		}
		
		
		System.out.println();
		
		int[] arr = {9,6,4,3,1};
		quickSort(arr);
		for (int i = 0; i < arr.length; i++) {
			if (i+1 == arr.length) {
				System.out.print(arr[i]);
			} else {
				System.out.print(arr[i] + ", ");
			}
		}
	}

	
	public static void quickSort(int[] arr) {
		//calls recursive version
		
		quickSortRec(arr, 0, arr.length-1); // first and last indices
	}

	private static void quickSortRec (int[] arr, int first, int last) {
		//Ending condition - size 1 or less
		if (first >= last) return;
		
		// return the index of one correctly sorted element;
		// that is p
		// the sorted element was called the pivot
		int p = partition(arr, first, last);
		
		quickSortRec(arr, first, p - 1);
		quickSortRec(arr, p + 1, last);
	}
		 
	 public static int partition (int[] arr,int first,int last) {
	 	//select pivot index somehow: here
	 	// it is the last index
	 	int pivot = arr[last];
	 	// this is the small number counter
	 	int i = first - 1;
	 	
	 	for (int j = first; j < last; j++) {
	 		if (arr[j] <= pivot) {
	 			i++;
	 			int temp = arr[i];
	 			arr[i] = arr[j];
	 			arr[j] = temp;
	 		}
	 	}
	 	
	 	// glue the pivot to the right side of the small numbers subarray
	 	int temp = arr[i+1];
	 	arr[i+1] = arr[last];
	 	arr[last] = temp;
	 	// return index of the correctly sorted number
	 	return i + 1;
	 }
	
	
	 
//-------------------------------------------------------------------------------------------------------------
	 
	 
	 
	 public static int[] mergeSort(int[] arr) {
		 int n = arr.length;
		 
		 // base case:
		 if (n <= 1) return arr;
		 
		 int middle = n/2;
		 int[] left = new int[middle];
		 int[] right = new int[n - middle];
		 
		 //make copies
		 for (int i = 0; i < middle; i++) {
			 left[i] = arr[i];
		 }
		 for (int i = middle; i < arr.length; i++) {
			 right[i - middle] = arr[i];
		 }
		 
		 left = mergeSort(left);
		 right = mergeSort(right);
		 
		 arr = merge(left, right);
		 
		 return arr;
	 }
	 
	 public static int[] merge(int[] A, int[] B) {
		 int i = 0; int j = 0; int k = 0;
		 int[] arr = new int[A.length + B.length];
		 
		 while (i < A.length && j < B.length) {
			 if (A[i] <= B[j]) {
				 arr[k] = A[i];
				 k++;
				 i++;
			 }
			 else {
				 arr[k] = B[j];
				 k++;
				 j++;
			 }
		 }
		 
		 while (i < A.length) {
			 arr[k] = A[i];
			 k++;
			 i++;
		 }
		 
		 while (j < B.length) {
			 arr[k] = B[j];
			 k++;
			 j++;
		 }
		 
		 return arr;
	 }

	 
	 
//	public static void mergeSort(int[] arr) {
//		mergeSortRec(arr);
//	}
//	 
//	private static void mergeSortRec(int[] arr)
//	{
//		int n = arr.length;
//		
//		if (n <= 1) {
//			return;
//		}
//		
//		int middle = n/2;
//		int[] left = new int[middle];
//		int[] right = new int[n - middle];
//		
//		//Making copies
//		for (int i = 0; i < middle; i++) {
//			left[i] = arr[i];
//		}
//		for (int i = middle; i < arr.length; i++) {
//			right[i - middle] = arr[i];
//		}
//		
//		//Recursively call mergeSort
//		mergeSortRec(left);
//		mergeSortRec(right);
//		
//		//Merge the sorted halves
//		merge(left, right, arr);
//	}
//
//	private static void merge(int[] left, int[] right, int[] arr) {
//		int Llength = left.length;
//		int Rlength = right.length;
//		int i = 0, j = 0, k = 0;
//		
//		while (i < Llength && j < Rlength) {
//			if (left[i] <= right[j]) {
//				arr[k] = left[i];
//				i++;
//				k++;
//			} else {
//				arr[k] = right[j];
//				j++;
//				k++;
//			}
//		}
//		
//		if (i >= Llength) {
//			while (j < Rlength) {
//				arr[k] = right[j];
//				j++;
//				k++;
//			}
//		} else {
//			while (i < Llength) {
//				arr[k] = left[i];
//				i++;
//				i++;
//			}
//		}
//	}
}
