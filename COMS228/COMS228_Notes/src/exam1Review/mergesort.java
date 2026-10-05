package exam1Review;

public class mergesort {
	
	public static void main(String[] args) {
		sort();
		
	}

	public static void sort()
	{
		// TODO - COMPLETED
		Integer[] points = {1,2,3,4,5,6,7,8,9,10};
		if (points.length > 1) {
			mergeSortRec(points);
		}
		
		for (int i = 0; i < points.length; i++) {
			System.out.print(points[i] + ", ");
		}
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private static void mergeSortRec(Integer[] pts)
	{
		int n = pts.length;
		
		if (n <= 1) {
//			return pts;
			return;
		}
		
		int middle = n/2;
		Integer[] left = new Integer[middle];
		Integer[] right = new Integer[n - middle];
		
		//Making copies
		for (int i = 0; i < middle; i++) {
			left[i] = pts[i];
		}
		for (int i = middle; i < pts.length; i++) {
			right[i - middle] = pts[i];
		}
		
		//Recursively call mergeSort
		mergeSortRec(left);
		mergeSortRec(right);
		
		//Merge the sorted halves
		merge(left, right, pts);
	}

	
	// Other private methods if needed ...
	/**
	 * Helper method used to merge two arrays into one singular array
	 * @param left - given array representing the desired left side
	 * @param right - given array representing the desired right side
	 * @param pts - the array receiving the elements
	 */
	private static void merge(Integer[] left, Integer[] right, Integer[] pts) {
		int Llength = left.length;
		int Rlength = right.length;
		int i = 0, j = 0, k = 0;
		
		while (i < Llength && j < Rlength) {
			if (left[i].compareTo(right[j]) <= 0) {
				pts[k] = left[i];
				i++;
				k++;
			} else {
				pts[k] = right[j];
				j++;
				k++;
			}
		}
		
		if (i >= Llength) {
			while (j < Rlength) {
				pts[k] = right[j];
				j++;
				k++;
			}
		} else {
			while (i < Llength) {
				pts[k] = left[i];
				i++;
				i++;
			}
		}
		
//		while (i < Llength) {
//				pts[k] = left[i];
//				i++;
//				k++;
//			}
//		while (j < Rlength) {
//				pts[k] = right[j];
//				j++;
//				k++;
//		}
	}
}
