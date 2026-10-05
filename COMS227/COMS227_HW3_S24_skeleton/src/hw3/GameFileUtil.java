package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import api.Wall;
import api.Cell;
import api.Exit;
import api.BodySegment;

/**
 * Utility class with static methods for loading game files.
 */
public class GameFileUtil {
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, LizardGame game) {
		try {
			File file = new File(filePath);
			Scanner scnr = new Scanner(file);
			
			
			// This bit here gets the grid dimensions and makes a new game
			String dimensionsArr = scnr.nextLine();
			String[] newDimensionsArr = dimensionsArr.split("x");
			int columns = Integer.parseInt(newDimensionsArr[0]);
			int rows = Integer.parseInt(newDimensionsArr[1]);
			game.resetGrid(columns, rows);
			
			
			// This bit will find and place the walls and exits
			int row = 0;
			String tempString = "";
			while (scnr.hasNextLine()) {
				String line = scnr.nextLine();
				
				// Breaks so that I can iterate through the last line(s) separately
				if (line.contains("L")) {
					tempString = line;
					break;
				}

				for (int col = 0; col < line.length(); col++) {
					char ch = line.charAt(col);
					
					// first if not really necessary but added it anyway
					if (ch == '.') {
						break;
					}
					else if (ch == 'W') {
						game.addWall(new Wall(game.getCell(col, row)));
					}
					else if (ch == 'E') {
						game.addExit(new Exit(game.getCell(col, row)));
					}
				}
				row++;
			}
			
			
			// This bit here will place the lizard(s) into the new game with the help of a helper method
			helpPlace(tempString, game);
			
			while (scnr.hasNextLine()) {
				String line = scnr.nextLine();
				helpPlace(line, game);
			}
			
			scnr.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: File not found.");
		}
	}
	
	/**
	 * HELPER METHOD
	 * helpPlace uses a given string from a line in the given text file as well as the lizard game
	 * to place any given amount of lizards present in the file
	 * @param lineString String representing the current line of the file being used to place a lizard
	 * @param game the current game being used
	 */
	private static void helpPlace(String lineString, LizardGame game) {
		ArrayList<BodySegment> segments = new ArrayList<>();
		Lizard liz = new Lizard();
		
		String[] str = lineString.split(" ");
		
		for (String s : str) {
			if (!s.contains("L")) {
//				System.out.println(s);
				String[] newStr = s.split(",");
				Cell lizardCell = game.getCell(Integer.parseInt(newStr[0]), Integer.parseInt(newStr[1]));
//				System.out.println(lizardCell);
				BodySegment newSegment = new BodySegment(liz, lizardCell);
				segments.add(newSegment);
			}
		}
		liz.setSegments(segments);
		game.addLizard(liz);
	}
}
