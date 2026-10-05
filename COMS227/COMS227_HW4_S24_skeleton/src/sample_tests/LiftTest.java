package sample_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hw4.AttachedElement;
import hw4.FollowerElement;
import hw4.LiftElement;

public class LiftTest {
	
	private static final double ERROR = 0.001;

	public static void main(String[] args) {
//		// left side at x = 50, width 10, right side at 60
//		LiftElement p = new LiftElement(200, 50, 10, 10);
//		p.setBounds(40, 70);
//		p.setVelocity(0, 6);
//		p.update();
//		System.out.println(p.getYReal() + ", " + (p.getYReal() + 10)); // [56, 66]
//		System.out.println("Velocity " + p.getDeltaY()); // 6.0
//		p.update();
//		System.out.println(p.getYReal() + ", " + (p.getYReal() + 10)); // [60, 70]
//		System.out.println("Velocity " + p.getDeltaY()); // -6.0
//		p.update();
//		System.out.println(p.getYReal() + ", " + (p.getYReal() + 10)); // [54, 64]
//		System.out.println("Velocity " + p.getDeltaY()); // -6.0
//		p.update();
//		System.out.println(p.getYReal() + ", " + (p.getYReal() + 10)); // [48, 58]
//		System.out.println("Velocity " + p.getDeltaY()); // -6.0
//		p.update();
//		System.out.println(p.getYReal() + ", " + (p.getYReal() + 10)); // [42, 52]
//		System.out.println("Velocity " + p.getDeltaY()); // -6.0
//		p.update();
//		System.out.println(p.getYReal() + ", " + (p.getYReal() + 10)); // [40, 50]
//		System.out.println("Velocity " + p.getDeltaY()); // 6.0
		
		double x = 50;
		double y = 50;
		int w = 10;
		int h = 10;
		LiftElement l = new LiftElement(x, y, w, h);
		
		// Set bounds to 30, 70
		double topBound = 30;
		double bottomBound = 70;
		l.setBounds(topBound, bottomBound);
		
		// follower will have a width of 3 and height of 3, and inital offset of 0
		FollowerElement f = new FollowerElement(3, 3, 0);
		
		// Attached element will have a height of 2, width of 2, offset of 4, and hover of 4
		AttachedElement a = new AttachedElement(2, 2, 4, 4);
		
		// Add the follower and attached elements to the lift
		l.addAssociated(a);
		l.addAssociated(f);
		
		// Update with no velocity set; nothing should move but frames should update.
		l.update();
		l.update();
		
		System.out.println(x + " " + l.getXReal());
		System.out.println(y + " " + l.getYReal());
		System.out.println(54 + " " + a.getXReal());
		System.out.println(44 + " " + a.getYReal());
		System.out.println(50 + " " + f.getXReal());
		System.out.println(47 + " " + f.getYReal());
		
		// set lift and follower velocity.
		// Lift velocity is 8, follower velocity is 3.
		l.setVelocity(0, 8);
		f.setVelocity(3, 0);
		
		// move everything.
		l.update();
		
		System.out.println();
		System.out.println(x + " " + l.getXReal());
		System.out.println(58 + " " + l.getYReal());
		System.out.println(54 + " " + a.getXReal());
		System.out.println(52 + " " + a.getYReal());
		System.out.println(53 + " " + f.getXReal());
		System.out.println(55 + " " + f.getYReal());
		
		
	}
//	
//	@Test
//	public void test8() {
//		// Test of a lift element alone.
//		
//		double x = 50;
//		double y = 50;
//		int w = 10;
//		int h = 10;
//		LiftElement l = new LiftElement(x, y, w, h);
//		
//		// Make sure initialization location is correct.
//		assertEquals(x, l.getXReal(), ERROR);
//		assertEquals(y, l.getYReal(), ERROR);
//		
//		// Set bounds to 30, 70
//		double topBound = 30;
//		double bottomBound = 70;
//		l.setBounds(topBound, bottomBound);
//		
//		// Before velocity is set, make sure it does not move 
//		// and frames are incrementing correctly.
//		l.update();
//		l.update();
//		
//		// location should be the same, element has 2 frames
//		assertEquals(x, l.getXReal(), ERROR);
//		assertEquals(y, l.getYReal(), ERROR);
//		assertEquals(2, l.getFrameCount());
//		
//		// Set velocity and start moving
//		double velocity = 8;
//		l.setVelocity(0, velocity);
//		l.update();
//		
//		// y-location should now be 8 frames down (58)
//		assertEquals(58, l.getYReal(), ERROR);
//		
//		// lift tries to move to 66, but that would put the bottom edge past lower bound (70)
//		// origin must remain at 60 to not exceed bottom bound (60 + height(10) = 70).
//		// velocity will also reverse.
//		l.update();
//		assertEquals(60, l.getYReal(), ERROR);
//		assertEquals(-velocity, l.getDeltaY(), ERROR);
//		
//		// now that we are going up, update until we hit the top
//		l.update(); // origin at y = 52
//		l.update(); // y = 44
//		l.update(); // y = 36
//		l.update(); // attempt to go to y = 28, but upper bound is 30
//		
//		// Check all of the attributes of the element, velocity should have reversed again.
//		// 8 frames have passed by this point.
//		assertEquals(30, l.getYReal(), ERROR);
//		assertEquals(x, l.getXReal(), ERROR);
//		assertEquals(velocity, l.getDeltaY(), ERROR);
//		assertEquals(8, l.getFrameCount());
//	}
	
	@Test
	public void test9() {
		// Lift element with an attached element and a follower element. Lots of tests in one.
		
		double x = 50;
		double y = 50;
		int w = 10;
		int h = 10;
		LiftElement l = new LiftElement(x, y, w, h);
		
		// Set bounds to 30, 70
		double topBound = 30;
		double bottomBound = 70;
		l.setBounds(topBound, bottomBound);
		
		// follower will have a width of 3 and height of 3, and inital offset of 0
		FollowerElement f = new FollowerElement(3, 3, 0);
		
		// Attached element will have a height of 2, width of 2, offset of 4, and hover of 4
		AttachedElement a = new AttachedElement(2, 2, 4, 4);
		
		// Add the follower and attached elements to the lift
		l.addAssociated(a);
		l.addAssociated(f);
		
		// Make sure initalization location is correct.
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(y, l.getYReal(), ERROR);
		
		// attached starts at x,y [54, 44] (offset of 4, hover of 4, height of 2)
		assertEquals(54, a.getXReal(), ERROR);
		assertEquals(44, a.getYReal(), ERROR);
		
		// Follower starts at x,y [50, 47] (offset of 0 and height of 3)
		assertEquals(50, f.getXReal(), ERROR);
		assertEquals(47, f.getYReal(), ERROR);

		// Update with no velocity set; nothing should move but frames should update.
		l.update();
		l.update();
		
		// check locations again. Nothing should have moved.
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(y, l.getYReal(), ERROR);
		assertEquals(54, a.getXReal(), ERROR);
		assertEquals(44, a.getYReal(), ERROR);
		assertEquals(50, f.getXReal(), ERROR);
		assertEquals(47, f.getYReal(), ERROR);
		
		// check frame counts
		assertEquals(2, l.getFrameCount());
		assertEquals(2, a.getFrameCount());
		assertEquals(2, f.getFrameCount());
		
		// set lift and follower velocity.
		// Lift velocity is 8, follower velocity is 3.
		l.setVelocity(0, 8);
		f.setVelocity(3, 0);
		
		// move everything.
		l.update();
		
		// check new positions.
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(58, l.getYReal(), ERROR);
		assertEquals(54, a.getXReal(), ERROR);
		assertEquals(52, a.getYReal(), ERROR);
		assertEquals(53, f.getXReal(), ERROR);
		assertEquals(55, f.getYReal(), ERROR);
		
		// move again. This time the lift will hit the bottom boundary
		// so the origin of the lift will be y = 60
		l.update();
		
		// checking positions
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(60, l.getYReal(), ERROR);
		assertEquals(54, a.getXReal(), ERROR);
		assertEquals(54, a.getYReal(), ERROR);
		assertEquals(56, f.getXReal(), ERROR);
		assertEquals(57, f.getYReal(), ERROR);
		
		// the velocity of the lift should have reversed.
		assertEquals(-8, l.getDeltaY(), ERROR);
		
		// keep updating, we are now moving up. Also the follower will now
		// hit the right edge of the lift, so it will stop at the boundary
		// and reverse.
		l.update();
		
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(52, l.getYReal(), ERROR);
		assertEquals(54, a.getXReal(), ERROR);
		assertEquals(46, a.getYReal(), ERROR);
		assertEquals(57, f.getXReal(), ERROR);
		assertEquals(49, f.getYReal(), ERROR);
		
		// follower velocity should have reversed.
		assertEquals(-3, f.getDeltaX(), ERROR);
		
		// keep updating until we get to the top.
		l.update(); 	// lift_y = 44, follower x = 54
		l.update(); 	// lift_y = 36, follower x = 51
		l.update();		// lift tries to go to y=28, but boundary at y=30
		// follower also tries to go to x = 48, but boundary was at x = 50
		
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(30, l.getYReal(), ERROR);
		assertEquals(54, a.getXReal(), ERROR);
		assertEquals(24, a.getYReal(), ERROR);
		assertEquals(50, f.getXReal(), ERROR);
		assertEquals(27, f.getYReal(), ERROR);
		
		// both lift and follower velocities should have reversed as well
		assertEquals(8, l.getDeltaY(), ERROR);
		assertEquals(3, f.getDeltaX(), ERROR);
		
		// 8 frames have passed, so make sure that all elements have the correct frame count.
		assertEquals(8, l.getFrameCount());
		assertEquals(8, a.getFrameCount());
		assertEquals(8, f.getFrameCount());
	}

}