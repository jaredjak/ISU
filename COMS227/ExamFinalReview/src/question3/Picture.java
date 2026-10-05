package question3;

import java.util.ArrayList;

public class Picture {
	private ArrayList<Shape> labels;
	private double area;
	
	public Picture() {
		labels = new ArrayList<>();
		area = 0;
	}

	void addShape(Shape s) {
		if (s.getArea() == 0) {
			throw new IllegalArgumentException("Area cannot be 0");
		}
		labels.add(s);
	}
	
	double findTotalArea() {
		for (int i = 0; i < labels.size(); i++) {
			area = area + labels.get(i).getArea();
		}
		return area;
	}
	void undo() {
		if (labels.size() > 0) {
			labels.remove(labels.size() - 1);
		}
	}
	
	ArrayList<String> getLabels() {
		ArrayList<String> temporaryList = new ArrayList<>();
		for (Shape sh : labels) {
			temporaryList.add(String.valueOf(sh));
		}
		return temporaryList;
	}
}
