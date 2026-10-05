package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LineNumberer {
	
	public static void readFile() throws FileNotFoundException{
		File file = new File("../project8/story.txt");
		
		Scanner scanner = new Scanner(file);
		
		int lineCount = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int wordCount;
			
			if (line == null || line.isEmpty()) {
				wordCount = 0;
			}
			else {
				String[] words = line.split("\\s+");
				wordCount = words.length;
			}
			
			lineCount++;
			System.out.println("Line " + lineCount + " has " + wordCount + " words.");
			
		}
		scanner.close();
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		//Checkpoint 1 part 2
		readFile();
		
		
		//CheckPoint 1 part 1
		
//		File file = new File("../project5/src/lab5/SimpleLoops.java");
//		
//		System.out.println(file.exists());
//		System.out.println(file.getName());
//		System.out.println(file.getAbsolutePath());
//		System.out.println(file.length());
//		
//		Scanner scanner = new Scanner(file);
//		int lineCount = 1;
//		
//		while (scanner.hasNextLine()) {
//			String line = scanner.nextLine();
//			System.out.println(lineCount + " ");
//			System.out.println(line);
//			lineCount += 1;
//		}
//		scanner.close();
		
	}

}
