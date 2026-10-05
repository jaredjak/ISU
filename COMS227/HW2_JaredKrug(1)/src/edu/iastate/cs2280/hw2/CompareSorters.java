package edu.iastate.cs2280.hw2;

/**
 *  
 * @author Jared Krug
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		// TODO - COMPLETED
		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       PointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// 	
		
		Scanner scnr = new Scanner(System.in);
		Random rand = new Random();
		int trialCount = 0;
		
		
		System.out.println("Performance of four Sorting Algorithms is Point Scanning");
		System.out.println();
		System.out.println("keys:  1 (random integers)  2 (file input)  3 (exit)");
		
		
		
		while (true) {
			System.out.print("Trial " + ++trialCount + ": ");
			int userChoice = scnr.nextInt();
			
			
			Point[] points = null;
			PointScanner[] scanners = new PointScanner[4]; 
			
			
			if (userChoice == 1) {
				System.out.print("Enter number of random points: ");
				int randPts = scnr.nextInt();
				points = generateRandomPoints(randPts, rand);
				
				//Reassigning
				scanners[0] = new PointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(points, Algorithm.MergeSort);
				scanners[3] = new PointScanner(points, Algorithm.QuickSort);
			}
			
			else if (userChoice == 2){
				System.out.println("Points from a file");
				System.out.print("File name: ");
				String file = scnr.next();
				
				//Reassigning
				scanners[0] = new PointScanner(file, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(file, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(file, Algorithm.MergeSort);
				scanners[3] = new PointScanner(file, Algorithm.QuickSort);
			}
			
			else if (userChoice == 3) {
				System.out.println("Goodbye...");
				break;
			}
			
			else {
				System.out.println("Not allowed...");
				break;
			}
			
			System.out.println();
			System.out.println("algorithm      size      time (ns)");
			System.out.println("----------------------------------");
			
			// for each loop to scan and print the stats
			for (PointScanner pointscanner : scanners) {
				pointscanner.scan();
				System.out.println(pointscanner.stats());
			}
			
			System.out.println("----------------------------------");
			System.out.println();
			
		}
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() 
		//        method in the PointScanner class.  
		//
		//     c) After all four scans are done for the input, print out the statistics table from
		//		  section 2.
		//
		// A sample scenario is given in Section 2 of the project description. 
		
		scnr.close();
		
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		// TODO - COMPLETED
		if (numPts < 1) {
			throw new IllegalArgumentException("Must be more than 0 points...");
		}
		
		Point[] randPoints = new Point[numPts];
		
		for (int i = 0; i < numPts; i++) {
			int x = rand.nextInt(101) - 50;
			int y = rand.nextInt(101) - 50;
			randPoints[i] = new Point(x,y);
		}
		
		return randPoints;
	}
	
}
