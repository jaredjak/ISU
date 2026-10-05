package edu.iastate.cs2280.hw2;

import java.io.File;

/**
 * 
 * @author Jared Krug
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("Not allowed...");
			}
		
		sortingAlgorithm = algo;
		
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		// TODO - COMPLETED
		
		File f = new File(inputFileName);
		
//		System.out.println("File path: " + f.getAbsolutePath());
		
		if (!f.exists()) {
			throw new FileNotFoundException("File not found: " + inputFileName);
		}
		
		Scanner scnr = new Scanner(f);
		sortingAlgorithm = algo;
		
		
		
		// First, will check that the file has an even number of integers.
		int count = 0;
		while (scnr.hasNextInt()) {
			scnr.nextInt();
			count++;
		}
		
		scnr.close();
		
		if ( count % 2 != 0) {
			throw new InputMismatchException("Not allowed...");
		}
		
		points = new Point[count / 2];
		
		
		// Second, goes through the file again to setup points
		scnr = new Scanner(f);
		for (int i = 0; i < points.length; i++) {
			int x = scnr.nextInt();
			int y = scnr.nextInt();
			points[i] = new Point(x,y);
		}
		scnr.close();
		
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		// TODO - COMPLETED
		AbstractSorter aSorter; 
		
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
		
		//find what algorithm to use
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else {
			aSorter = new QuickSorter(points);
		}
		
		//sort by x-coordinates and add up scanTime
		aSorter.setComparator(0);
		long start = System.nanoTime();
		aSorter.sort();
		long end = System.nanoTime();
		scanTime += (end - start);
		
		//sort by y-coordinates and add up scanTime
		aSorter.setComparator(1);
		start = System.nanoTime();
		aSorter.sort();
		end = System.nanoTime();
		scanTime += (end - start);
		
		//set up medianCoordinatePoint with the medians found
		int medianX = points[points.length/2].getX();
		int medianY = points[points.length/2].getY();
		medianCoordinatePoint = new Point(medianX, medianY);
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		// TODO - COMPLETED
		return String.format("%-15s %4d % d", sortingAlgorithm.toString(), points.length, scanTime);
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		// TODO - COMPLETED
		return String.format("MCP: (%d, %d)", medianCoordinatePoint.getX(), medianCoordinatePoint.getY());
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		// TODO - COMPLETED?
		String outputFileName = "outputFileName.txt";
		
		try (PrintWriter writer = new PrintWriter(outputFileName)) {
			writer.println(this.toString());
		}
	}	

	

		
}
