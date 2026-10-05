package edu.iastate.cs2280.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Jared Krug
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
	// Not needed ...
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		// TODO - COMPLETED
		super(pts);
		algorithm = "quicksort";
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override 
	public void sort()
	{
		// TODO - COMPLETED
		quickSortRec(0, points.length - 1);
	}
	


	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		// TODO - COMPLETED
		if (first >= last) {
			return;
		}
		
		int p = partition(first, last);
		
		//Recursively sort
		quickSortRec(first, p-1);
		quickSortRec(p+1, last);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		// TODO - COMPLETED
		Point pivot = points[last];
		int i = first - 1;
		
		for (int j = first; j < last; j++) {
			if (points[j].compareTo(pivot) <= 0) {
				i++;
				
				Point temp = points[i];
				points[i] = points[j];
				points[j] = temp;
			}
		}
		
		Point temp = points[i+1];
		points[i+1] = points[last];
		points[last] = temp;
		
		return i+1; 
	}	
	
	// Other private methods if needed ...
	// Not needed ...
}
