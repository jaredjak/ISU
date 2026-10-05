//package newQuestions;
//
//public class SortedIncreasing {
//
//	public static boolean isSortedIncreasing(String[] names, int n) {
//		// base case
//		if (n <= 1) {
//			return true;
//		}
//		
//		return compareTo((names[n-1], names[n-2]) >= 0) &&
//				isSortedIncreasing(names, n - 1);
//	}
//	
//	public static int compareTo(String str1, String str2) {
//		int minLength = Math.min(str1.length(), str2.length());
//		
//		for (int i = 0; i < minLength; i++) {
//			if (str1.charAt(i) != str2.charAt(i)) {
//				return str1.charAt(i) - str2.charAt(i);
//			}
//		}
//	}
//}
