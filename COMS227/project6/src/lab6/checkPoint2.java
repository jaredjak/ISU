package lab6;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import plotter.Plotter;
import plotter.Polyline;

public class checkPoint2 {
	
	private static Polyline parseOneLine(String line) {
		String[] lineParts = line.split("\\s+"); // \\s+ splits by whitespace
		int width;
		int beginIndex;
		String color;
		
		if (isNumeric(lineParts[0])) {
			width = Integer.parseInt(lineParts[0]);
			color = lineParts[1];
			beginIndex = 2;
		} else {
			width = 1;
			color = lineParts[0];
			beginIndex = 1;
		}
		
		Polyline polyline = new Polyline(color, width);
		
		for (int i = beginIndex; i < lineParts.length; i += 2) {
			int xVal = Integer.parseInt(lineParts[i]);
			int yVal = Integer.parseInt(lineParts[i + 1]);
			polyline.addPoint(new Point(xVal, yVal));
		}
		
		return polyline;
	}
	
	/**
	 * Helper for parseSingleLine method
	 * @param str
	 * @return boolean true or false
	 */
	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	private static ArrayList<Polyline> readFile(String fileName) throws FileNotFoundException {
		ArrayList<Polyline> poly = new ArrayList<Polyline>();
		File file = new File(fileName);
		Scanner scnr = new Scanner(file);
		
		while (scnr.hasNextLine()) {
			String line = scnr.nextLine().trim(); // trim deletes all leading and trailing whitespace
			if (!line.startsWith("#") && !line.isEmpty()) { // does not consider the random comment and the blank spaces
				poly.add(parseOneLine(line));
			}
		}
		return poly;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Polyline> polyList = readFile("../project8/hello.txt");
		Plotter plotter = new Plotter();
		
		for (Polyline temp : polyList) {
			plotter.plot(temp);
		}
	}

}
