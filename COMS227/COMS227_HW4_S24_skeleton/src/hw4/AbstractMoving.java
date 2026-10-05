package hw4;

import java.util.ArrayList;
import api.AbstractElement;

public class AbstractMoving extends MovingElement {

	/**
	 * The max the given element can go on the y-axis
	 */
	private double max;
	
	/**
	 * The min the given element can go on the y-axis
	 */
	private double min;
	
	/**
	 * An ArrayList of elements that will be associated with LiftElement
	 */
	private ArrayList<AbstractElement> associated;

	public AbstractMoving(double x, double y, int width, int height) {
		super(x, y, width, height);
		
		max = Double.POSITIVE_INFINITY;
		min = Double.NEGATIVE_INFINITY;
		
		associated = new ArrayList<AbstractElement>();
	}
	
	/**
	 * Adds the associated AttachedElement to the given arrayList
	 * Sets the base of the given element
	 * @param attached - the AttachedElement to add
	 */
	public void addAssociated(AttachedElement attached) {
		associated.add(attached);
		attached.setBase(this);
	}

	/**
	 * Adds the associated FollowerElement to the given arrayList
	 * Sets the base of the given element
	 * @param follower - the FollowerElement to add
	 */
	public void addAssociated(FollowerElement follower) {
		associated.add(follower);
		follower.setBase(this);
	}
	
	/**
	 * Deletes any elements that are considered to be marked
	 */
	public void deleteMarkedAssociated() {
		for (int i = 0; i <= associated.size() - 1; i++) {
			if (associated.get(i).isMarked()) {
				associated.remove(i);
			}
		}
	}
	
	/**
	 * Gets the arrayList used to contain the associated elements
	 * @return associated arrayList
	 */
	public java.util.ArrayList<AbstractElement> getAssociated() {
		return associated;
	}
	
	/**
	 * Gets the max associated with the double max
	 * @return the max an element can go on the y-axis
	 */
	public double getMax() {
		return max;
	}
	
	/**
	 * Gets the min associated with the double min
	 * @return the min an element can go on the y-axis
	 */
	public double getMin() {
		return min;
	}
	
	/**
	 * Sets the bounds of the PlatformElement on the x-axis
	 * @param min - the given min an element can go on the y-axis
	 * @param max - the given max an element can go on the y-axis
	 */
	public void setBounds(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Updates the element (I think this is unnecessary but oh well)
	 */
	@Override
	public void update() {
		super.update();
	}
}