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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	// Not needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		// TODO - COMPLETED
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		// TODO - COMPLETED
		if (points.length > 1) {
			mergeSortRec(points);
		}
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int n = pts.length;
		
		if (n <= 1) {
//			return pts;
			return;
		}
		
		int middle = n/2;
		Point[] left = new Point[middle];
		Point[] right = new Point[n - middle];
		
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
	private void merge(Point[] left, Point[] right, Point[] pts) {
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
		
		while (i < Llength) {
			pts[k] = left[i];
			i++;
			k++;
		}
		while (j < Rlength) {
			pts[k] = right[j];
			j++;
			k++;
		}
	}
}