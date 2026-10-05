package intList;

public class IntList {
	private int[] data;
	private int size;
	
	public IntList(int initCapacity) {
		data = new int[initCapacity];
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public int get(int pos) {
		if (pos >= size) {
			return -1;
		} else {
			return data[pos];
		}
	}
	
	public void set(int pos, int value) {
		data[pos] = value;
	}
	
	public void add(int value) {
		checkCapacity();
		data[size] = value;
		size++;
	}
	
	public void add(int pos, int value) {
		checkCapacity(); 
		
		for (int i = size; i > pos; i--) {
			data[i] = data[i-1];
		}
		data[pos] = value;
		size++;
	}
	
	private void checkCapacity() {
		if (size == data.length) {
			int[] newData = new int[data.length * 2];
			//int i outside of the loop because we want to preserve i for later use.
			int i;
			for (i = 0; i < data.length; i++) {
				newData[i] = data[i];
			}
			data = newData;
		}
	}
	
	public void remove(int pos) {
		for (int i = pos; i < size - 1; i++) {
			data[i] = data[i+1];
		}
		size--;
	}
	
	public String toString() {
		String result = "[ ";
		
		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				result += data[i];
			} else {
				result += data[i] + ", ";
			}
		}
		
		result += " ]";
		
		return result;
	}
	
	public static void main(String[] args) {
		IntList list = new IntList(5);
		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		
		list.remove(2);
		
		System.out.println(list.toString());
		

	}

}
