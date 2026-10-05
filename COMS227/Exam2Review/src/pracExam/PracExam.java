//package pracExam;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Scanner;
//
//public class PracExam {
//	
//	// # 1
//	public static String blankVowels(String str) {
//		String newWord = "";
//		for (int i = 0; i < str.length(); i++) {
//			char c = str.charAt(i);
//			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
//				newWord += "_";
//			} else {
//				newWord += c;
//			}
//		}
//		return newWord;
//	}
//	
//	// # 2
//	public static int[] removeDuplicates(int[] dupeArr) {
//		// First part
//		ArrayList<Integer> noDupesList = new ArrayList<Integer>();
//		
//		// Second part
//		for (int i = 0; i < dupeArr.length; i++) {
//			if (!noDupesList.contains(dupeArr[i])) {
//				noDupesList.add(dupeArr[i]);
//			}
//		}
//		
//		// Third part
//		int[] noDupesArr = new int[noDupesList.size()];
//		
//		// Fourth part
//		for (int i = 0; i < noDupesList.size(); i++) {
//			noDupesArr[i] = noDupesList.get(i);
//		}
//		
//		return noDupesArr;
//	}
//	
//	
//	// # 3
//	public static ArrayList<String> readPalindrome(String fileName) {
//		// First part
//		ArrayList<String> palindromeList = new ArrayList<>();
//		
//		// Second part
//		try {
//			File file = new File(fileName);
//			Scanner scnr = new Scanner(file);
//			
//			while (scnr.hasNext()) {
//				String temp = scnr.next();
//				// pretend isPalindrome exists
//				if (isPalindrome(temp)) {
//					palindromeList.add(temp);
//				}
//			}
//			scnr.close();
//			
//		} catch (FileNotFoundException e) {
//			return palindromeList;
//		}
//		
//		return palindromeList;
//	}
//	
//	public static int[][] rightShifts(int[] arr) {
//		// First part
//		int[][] shiftTable = new int[arr.length][arr.length];
//		
//		// Second part
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr.length; j++) {
//				int newIndex = j - i;
//				if (newIndex > arr.length - 1 || newIndex < 0) {
//					shiftTable[i][j] = 0;
//				} else {
//					shiftTable[i][j] = arr[newIndex];
//				}
//			}
//		}
//		return shiftTable;
//	}
//	
////	extra question
//	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//		int[] sortedArr = new int[nums1.length + nums2.length];
//		
//		for (int i = 0; i < nums1.length; i++) {
//			sortedArr[i] = nums1[i];
//		}
//		
//		for (int j = 0; j < nums2.length; j++) {
//			sortedArr[j + nums1.length] = nums2[j];
//		}
//		
//		Arrays.sort(sortedArr);
//		
//		int len = sortedArr.length;
//		
//		if (len % 2 == 0) {
//			return (sortedArr[len/2] + sortedArr[len/2 -1])/2.0; 
//		} else {
//			return sortedArr[len/2];
//		}
//	}
//	
////	extra question 2
//	public int searchInsert(int[] nums, int target) {
//		for(int i = 0; i < nums.length; i++) {
//			if (nums[i] >= target) {
//				return i;
//			}
//		}
//		return nums.length;
//	}
//	
////	extra question 3
//	public boolean searchMatrix(int[][] matrix, int target) {
//		int col = matrix[0].length;
//		int row = matrix.length;
//		
//		for (int i = 0; i < col; i++) {
//			for (int j = 0; j < row; j++) {
//				if (matrix[j][i] == target) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	
////	extra question 4
//	public static double calcAverage(ArrayList<Integer> nums) {
//		int sum = 0;
//		
//		for (int i = 0; i < nums.size(); i++) {
//			int curNum = nums.get(i);
//			sum += curNum;
//		}
//		return (sum * 1.0) / nums.size();
//	}
//	public static void main2(String[] args) {
//		File file = new File("nums.txt");
//		Scanner scnr = new Scanner(file);
//		ArrayList<Integer> arr = new ArrayList<>();
//		while (scnr.hasNextLine()) {
//			String line = scnr.nextLine();
//			int num = Integer.parseInt(line);
//			arr.add(num);
//		}
//		
//		System.out.println(calcAverage(arr));
//		
//	}
//	
//
//	public static void main(String[] args) {
//		// # 1
////		System.out.println(blankVowels("kayak"));
//		
//		// # 2
////		int[] dupeArr = {5,4,6,4,2};
////		int[] noDupe = removeDuplicates(dupeArr);
////		for (int i = 0; i < noDupe.length; i++) {
////			System.out.print(noDupe[i] + " ");
////		}
//		
//		// # 4
//		
//		
//		// # 5
//		
//		
//		// # 6
////		part a
////		Call Stack:
////		confound(4) + 4 -> 7+4 print 11returns 11
////		confound(3) + 3 -> 4+3 print 7 -> returns 7
////		confound(2) + 2 -> 2+2 -> print 4 -> returns 4
////		print peach
//		
////		output:
////		peach
////		4
////		7
////		11
//		
//		
////		part b
////		Call Stack:
////		(T && check(aba)) || ... 
////		(T && check(ba)) || ... F
////		(F && check(a)) || ... F
////		(T && check()) || ... T
////		(T && T) || ... T
//		
//		
////		in-class #6
////		check("aaba")
////		T && check("aba") || ...
////		T && check("ba") || ...
////		F && ... || F & ...
////		Syso()
////		return back to check("aba")
////		F || F
////		Syso()
////		return back to check("aaba")
////		F || F
////		Syso()
////		return to where method called
//		
////		output:
////		For string ba result is false.
////		For string aba result is false.
////		For string aaba result is false
//		
//		
////		in-class #4
//		//part 1
////		int n = arr.length;
////		int[][] shiftTable = new int[n][n];
////		
//		//part 2
////		for (int i = 0; i < n; i++ {
////			for (int j = i; j < n; j++) {
////				int index = j - i;
////				shiftTable[i][j] = arr[index];
////			}
////		}
//	}
//	
//}
