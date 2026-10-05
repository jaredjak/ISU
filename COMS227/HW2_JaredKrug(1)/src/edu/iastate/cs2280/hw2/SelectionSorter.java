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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	// not needed ...
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		// TODO - COMPLETED
		super(pts);
		algorithm = "selection sort";
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		// TODO - COMPLETED
		for (int i = 0; i < points.length - 1; i++) {
			int minIndex = i;
			for (int j = i; j < points.length; j++) {
				if (points[j].compareTo(points[minIndex]) < 0) {
					minIndex = j;
				}
			}
			Point temp = points[i];
			points[i] = points[minIndex];
			points[minIndex] = temp;
		}
	}	
}
