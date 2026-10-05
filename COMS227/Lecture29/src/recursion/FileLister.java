package recursion;

import java.io.File;
import java.util.ArrayList;

public class FileLister {

	public static int arraySum(int[] arr, int start, int end) {
		// Base Case
		if (start > end) {
			return 0;
		} else if (start == end) {
			return arr[start];
		}
		
		// Recursion
		int mid = (start + end) / 2;
		int first = arraySum(arr, start, mid);
		int second = arraySum(arr, mid + 1, end);
		return first + second;
		
	}
	
	public static ArrayList<String> listPermutations(String text) {
		ArrayList<String> list = new ArrayList<String>();
		
		// Base Case
		if (text.length() == 1) {
			list.add(text);
			return list;
		}
		
		// Recursion
		for (int i = 0; i < text.length(); i++) {
			// the character in question
			char c = text.charAt(i);
			// the String without the character in question
			String remainder = text.substring(0, i) + text.substring(i + 1);
			
			ArrayList<String> sublist = listPermutations(remainder);
			for (String s : sublist) {
				list.add(c + s);
			}
		}
		
		return list;
	}
	
	
	public static void listAllFilesIndented(File file, String padding) {
		// Base Case
		if (!file.isDirectory()) {
			System.out.println(padding + file.getName());
			return;
		}
		
		// Recursion
		File[] files = file.listFiles();
		System.out.println(padding + file.getName());
		for (File subfile : files) {
			listAllFilesIndented(subfile, padding + "- ");
		}
	}
	
	public static void listAllFiles(File file) {
		// Base Case
		if (!file.isDirectory()) {
			System.out.println(file.getName());
			return;
		}
		
		// Recursion
		File[] files = file.listFiles();
		System.out.println(file.getName());
		for (File subfile : files) {
			listAllFiles(subfile);
		}
	}
	
	public static void listOneLevel(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subfile : files) {
				System.out.println(subfile.getName());
			}
		}
	}
	
	public static void main(String[] args) {
//		listOneLevel(new File("./Root"));
//		listAllFiles(new File("./Root"));
//		listAllFilesIndented(new File("./Root"), "- ");
//		System.out.println(listPermutations("ABC"));
		int[] data = {1,2,3,4,5}; // sum is 15
		System.out.println(arraySum(data, 0, data.length - 1));

	}

}
