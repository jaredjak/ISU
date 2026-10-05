package lab8;

public class IntListTest {

	public static void main(String[] args) {
		
		IntList list = new IntList();
		list.add(5);
		list.add(4);
		list.add(3);
		
//		System.out.println(list);
//		System.out.println("Size: " + list.size());
//		System.out.println("Min: " + list.getMinimum());
//		System.out.println("Max: " + list.getMaximum());
		
		
		// Checkpoint 1
		IntListSorted sortedList = new IntListSorted();
		
		sortedList.add(5);
		sortedList.add(4);
		sortedList.add(3);
		sortedList.add(2);
	
		System.out.println(sortedList);
		System.out.println("Size: " + sortedList.size());
		System.out.println("Min: " + sortedList.getMinimum());
		System.out.println("Max: " + sortedList.getMaximum());
		System.out.println("Median: " + sortedList.getMedian());
	}
}
