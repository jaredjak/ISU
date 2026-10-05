package sample_tests;

import hw4.AttachedElement;
import hw4.FlyingElement;
import hw4.FollowerElement;
import hw4.LiftElement;
import hw4.PlatformElement;
import hw4.MovingElement;
import hw4.SimpleElement;
import hw4.VanishingElement;

import org.junit.Test;
import static org.junit.Assert.*;

public class ElementTests {
	
	private static final double ERROR = 0.001;
	
	@Test
	public void test1() {
		// BasicTest provided by instructors
		SimpleElement e = new SimpleElement(2.3, 4.5, 10, 20);
		assertEquals(e.getXReal(), 2.3, ERROR); // expected 2.3
		assertEquals(e.getXInt(), 2, ERROR); // expected 2
		assertEquals(e.getYReal(), 4.5, ERROR); // expected 4.5
		assertEquals(e.getYInt(), 5, ERROR); // expected 5
		assertEquals(e.getWidth(), 10, ERROR); // expected 10
		assertEquals(e.getHeight(), 20, ERROR);// expected 20
		assertEquals(e.isMarked(), false); // expected false

		// setting position
		e.setPosition(42, 137);
		assertEquals(e.getXReal(), 42, ERROR); // expected 42
		assertEquals(e.getYReal(), 137, ERROR); // expected 137

		// update should increment the frame count
		e.update();
		e.update();
		assertEquals(e.getFrameCount(), 2); // expected 2

		// mark
		e.markForDeletion();
		assertEquals(e.isMarked(), true); // expected true

		e = new SimpleElement(10, 0, 10, 10);
		SimpleElement e2 = new SimpleElement(1, 5, 10, 10);
		assertEquals(e.collides(e2), true); // expected true
		e2.setPosition(0, 5);
		assertEquals(e.collides(e2), false); // expected false
	}
	
	@Test
	public void test2() {
		// Flying test provided by instructors
		FlyingElement p = new FlyingElement(0, 0, 0, 0);
		p.setGrounded(false);
		p.setVelocity(2, 3);
		p.update();
		assertEquals(p.getYReal(), 3, ERROR); // expected 3
		assertEquals(p.getDeltaY(), 3, ERROR);// expected 3
		p.update();
		assertEquals(p.getYReal(), 6, ERROR); // expected 6
		assertEquals(p.getDeltaY(), 3, ERROR);// expected 3
		p.setGravity(5);
		p.update();
		assertEquals(p.getYReal(), 9, ERROR); // 6 + 3 = 9
		assertEquals(p.getDeltaY(), 8, ERROR); // 3 + 5 = 8
		assertEquals(p.isGrounded(), false);
		p.setGrounded(true);
		p.update();
		assertEquals(p.getYReal(), 17, ERROR); // 9 + 8 = 17
		assertEquals(p.getDeltaY(), 8, ERROR); // 8 (grounded)
		assertEquals(p.isGrounded(), true);
		p.update();
		assertEquals(p.getYReal(), 25, ERROR); // 17 + 8 = 25
		assertEquals(p.getDeltaY(), 8, ERROR); // 8 (grounded)
	}
	
	@Test
	public void test3() {
		// PlatformTest provided by instructors
		PlatformElement p = new PlatformElement(50, 200, 10, 10);
		p.setBounds(40, 70);
		p.setVelocity(6, 0);
		p.update();
		assertEquals(p.getXReal(), 56, ERROR); // [56, 66]
		assertEquals(p.getXReal() + 10, 66, ERROR);
		assertEquals(p.getDeltaX(), 6, ERROR); // 6.0
		p.update();
		assertEquals(p.getXReal(), 60, ERROR); // [60, 70]
		assertEquals(p.getXReal() + 10, 70, ERROR);
		assertEquals(p.getDeltaX(), -6, ERROR); // -6.0
		p.update();
		assertEquals(p.getXReal(), 54, ERROR); // [54, 64]
		assertEquals(p.getXReal() + 10, 64, ERROR);
		assertEquals(p.getDeltaX(), -6, ERROR);
		p.update();
		assertEquals(p.getXReal(), 48, ERROR); // [48, 58]
		assertEquals(p.getXReal() + 10, 58, ERROR);
		assertEquals(p.getDeltaX(), -6, ERROR);
		p.update();
		assertEquals(p.getXReal(), 42, ERROR); // [42, 52]
		assertEquals(p.getXReal() + 10, 52, ERROR);
		assertEquals(p.getDeltaX(), -6, ERROR);
		p.update();
		assertEquals(p.getXReal(), 40, ERROR); // [40, 50]
		assertEquals(p.getXReal() + 10, 50, ERROR);
		assertEquals(p.getDeltaX(), 6, ERROR);
	}
	
	@Test
	public void test4() {
		// PlatformWithAttachedTest provided by instructors.
		
		// left side at x = 50, width 10, right side at 60
		PlatformElement p = new PlatformElement(50, 200, 10, 10);
		p.setBounds(40, 70);
		p.setVelocity(6, 0);

		// size 5 x 5, offset 2 units from left of platform, 15 above
		AttachedElement c = new AttachedElement(5, 5, 2, 15);

		// this should automatically make p the base of c
		p.addAssociated(c);

		// x position should be the base position + 2 = 52
		// y position should be base y - attached element height - hover = 180
		assertEquals(c.getXReal(), 52, ERROR); // expected 52
		assertEquals(c.getYReal(), 180, ERROR); // expected 180
		p.update();
		assertEquals(c.getXReal(), 58, ERROR); // expected 58
		assertEquals(c.getYReal(), 180, ERROR); // expected 180
		p.update();
		assertEquals(c.getXReal(), 62, ERROR); // expected 62
		assertEquals(c.getYReal(), 180, ERROR); // expected 180

		// calling update on p should update associated elements too
		assertEquals(c.getFrameCount(), 2); // expected 2
	}
	
	@Test
	public void test5() {
		// PlatformWithFollowerTest provided by instructors.
		
		// first try an example where the base doesn't move
		// left side at x = 50, width 10, right side at 60
		PlatformElement b = new PlatformElement(50, 200, 10, 10);
		b.setBounds(40, 70);

		// size 5 x 5, initial offset 2 units from left of platform
		FollowerElement e = new FollowerElement(5, 5, 2);
		e.setVelocity(2, 0);

		// this should automatically make b the base of e
		// and the left and right sides of b the boundaries of e
		b.addAssociated(e);
		assertEquals(e.getMin(), 50, ERROR); // 50
		assertEquals(e.getMax(), 60, ERROR); // 60

		// x position should be the base position + 2 = 52
		// y position should be base y - follower height = 195
		assertEquals(e.getXReal(), 52, ERROR); // expected 52
		assertEquals(e.getYReal(), 195, ERROR); // expected 195

		// platform doesn't move here, but follower does
		b.update();
		assertEquals(e.getXReal(), 54, ERROR); // expected 54, 59
		assertEquals(e.getXReal() + 5, 59, ERROR);
		assertEquals(e.getDeltaX(), 2, ERROR); // expected 2.0
		b.update();
		assertEquals(e.getXReal(), 55, ERROR); // expected 55, 60
		assertEquals(e.getXReal() + 5, 60, ERROR);
		assertEquals(e.getDeltaX(), -2, ERROR); // expected -2.0

		// next, what if platform is moving, but follower velocity is 0?
		// left side at x = 50, width 10, right side at 60
		b = new PlatformElement(50, 200, 10, 10);
		b.setBounds(40, 70);
		b.setVelocity(3, 0);

		// size 5 x 5, initial offset 2 units from left of platform
		e = new FollowerElement(5, 5, 2);
		b.addAssociated(e);
		assertEquals(e.getXReal(), 52, ERROR); // expected 52, 57
		assertEquals(e.getXReal() + 5, 57, ERROR);
		assertEquals(e.getDeltaX(), 0, ERROR); // expected 0

		// when b moves, the boundaries of e should shift
		b.update();
		assertEquals(e.getMin(), 53, ERROR); 
		assertEquals(e.getMax(), 63, ERROR); // 53, 63

		// but e is still 2 units from left side
		assertEquals(e.getXReal(), 55, ERROR); // expected 55, 60
		assertEquals(e.getXReal() + 5, 60, ERROR);
		assertEquals(e.getDeltaX(), 0, ERROR); // expected 0

		// next, what if platform is moving, and follower velocity is nonzero?
		// left side at x = 50, width 10, right side at 60
		b = new PlatformElement(50, 200, 10, 10);
		b.setBounds(40, 70);
		b.setVelocity(3, 0);

		// size 5 x 5, initial offset 2 units from left of platform
		e = new FollowerElement(5, 5, 2);
		e.setVelocity(2, 0);
		b.addAssociated(e);
		assertEquals(e.getXReal(), 52, ERROR); // expected 52, 57
		assertEquals(e.getXReal() + 5, 57, ERROR);

		b.update();
		// e is now 4 units from left bound, since its velocity is 2
		assertEquals(e.getMin(), 53, ERROR);
		assertEquals(e.getMax(), 63, ERROR);
		assertEquals(e.getXReal(), 57, ERROR); // expected 57, 62
		assertEquals(e.getXReal() + 5, 62, ERROR);

		b.update();
		// b has moved to [56, 66], e attempts to move another 2 units
		// relative to b, to [62, 67], but hits the right boundary at 66
		// and reverses direction
		assertEquals(e.getMin(), 56, ERROR);
		assertEquals(e.getMax(), 66, ERROR);
		assertEquals(e.getXReal(), 61, ERROR);
		assertEquals(e.getXReal() + 5, 66, ERROR);
		assertEquals(e.getDeltaX(), -2, ERROR);
	}
	
	@Test
	public void test6() {
		// test implementation of the vanishing element
		double x = 9.6;
		double y = 11.2;
		int w = 10;
		int h = 5;
		int initalLife = 4;
		VanishingElement v = new VanishingElement(x, y, w, h, initalLife);
		
		// Test dimensions of elements
		assertEquals(x, v.getXReal(), ERROR);
		assertEquals(y, v.getYReal(), ERROR);
		assertEquals(10, v.getXInt());
		assertEquals(11, v.getYInt());
		assertEquals(w, v.getWidth());
		assertEquals(h, v.getHeight());
		
		// check frameCount and isMarked
		assertEquals(0, v.getFrameCount());
		assertEquals(false, v.isMarked());
		
		v.update();
		v.update();
		
		// After two updates, frame count should be 2 and not marked
		assertEquals(2, v.getFrameCount());
		assertEquals(false, v.isMarked());
		
		v.update();
		v.update();

		// After two more updates, frame count should be 4 
		// and it should be marked for deletion.
		assertEquals(4, v.getFrameCount());
		assertEquals(true, v.isMarked());
	}
	
	@Test
	public void test7() {
		// Implementation of the moving element.
		double x = 5;
		double y = 5;
		int w = 10;
		int h = 5;
		MovingElement m = new MovingElement(x, y, w, h);
		
		// Make sure initalization location is correct.
		assertEquals(x, m.getXReal(), ERROR);
		assertEquals(y, m.getYReal(), ERROR);
		
		// Before velocity is set, update and make sure nothing moved.
		m.update();
		
		assertEquals(x, m.getXReal(), ERROR);
		assertEquals(y, m.getYReal(), ERROR);
		
		// Framecount should now also be 1
		assertEquals(1, m.getFrameCount());
		
		// set velocity and update twice
		double dX = 2;
		double dY = 1;
		m.setVelocity(dX, dY);
		m.update();
		m.update();
		
		// Element should now be at [9, 7], with 3 frames.
		assertEquals(9, m.getXReal(), ERROR);
		assertEquals(7, m.getYReal(), ERROR);
		assertEquals(3, m.getFrameCount());
		
		// Set velocity back to zero and make sure it doesn't move again.
		m.setVelocity(0, 0);
		m.update();
		
		// Should still be at [9, 7], but now with 4 frames
		assertEquals(9, m.getXReal(), ERROR);
		assertEquals(7, m.getYReal(), ERROR);
		assertEquals(4, m.getFrameCount());
	}
	
	@Test
	public void test8() {
		// Test of a lift elemement alone.
		
		double x = 50;
		double y = 50;
		int w = 10;
		int h = 10;
		LiftElement l = new LiftElement(x, y, w, h);
		
		// Make sure initalization location is correct.
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(y, l.getYReal(), ERROR);
		
		// Set bounds to 30, 70
		double topBound = 30;
		double bottomBound = 70;
		l.setBounds(topBound, bottomBound);
		
		// Before velocity is set, make sure it does not move 
		// and frames are incrementing correctly.
		l.update();
		l.update();
		
		// location should be the same, element has 2 frames
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(y, l.getYReal(), ERROR);
		assertEquals(2, l.getFrameCount());
		
		// Set velocity and start moving
		double velocity = 8;
		l.setVelocity(0, velocity);
		l.update();
		
		// y-location should now be 8 frames down (58)
		assertEquals(58, l.getYReal(), ERROR);
		
		// lift tries to move to 66, but that would put the bottom edge past lower bound (70)
		// origin must remain at 60 to not exceed bottom bound (60 + height(10) = 70).
		// velocity will also reverse.
		l.update();
		assertEquals(60, l.getYReal(), ERROR);
		assertEquals(-velocity, l.getDeltaY(), ERROR);
		
		// now that we are going up, update until we hit the top
		l.update(); // origin at y = 52
		l.update(); // y = 44
		l.update(); // y = 36
		l.update(); // attempt to go to y = 28, but upper bound is 30
		
		// Check all of the attributes of the element, velocity should have reversed again.
		// 8 frames have passed by this point.
		assertEquals(30, l.getYReal(), ERROR);
		assertEquals(x, l.getXReal(), ERROR);
		assertEquals(velocity, l.getDeltaY(), ERROR);
		assertEquals(8, l.getFrameCount());
	}
	
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
	
	@Test
	public void test10() {
		// Test the getAssociated and deleteAllAssociated methods for the lift element.
		// Same initalization as test9.
		
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
		
		// Lets check the list of associated elements. 
		// The length of the list should be 2.
		assertEquals(2, l.getAssociated().size());
		
		// Try calling update() to try to see if anything breaks.
		l.update();
		l.update();
		
		// List should still be length 2
		assertEquals(2, l.getAssociated().size());
		
		// Set velocity and update and see if anything breaks
		l.setVelocity(0, 8);
		f.setVelocity(3, 0);

		l.update();
		
		// List should still be length 2
		assertEquals(2, l.getAssociated().size());
		
		// Lets try deleting the associated elements.
		a.markForDeletion();
		f.markForDeletion();
		l.deleteMarkedAssociated();
		
		// The length of the associated elements list should be zero.
		assertEquals(0, l.getAssociated().size());
	}
	
	@Test
	public void test11() {
		// Test the getAssociated and deleteAllAssociated methods for the platform element.
		// Same initalization as test9.
		
		double x = 50;
		double y = 50;
		int w = 10;
		int h = 10;
		PlatformElement p = new PlatformElement(x, y, w, h);
		
		// Set bounds to 30, 70
		double topBound = 30;
		double bottomBound = 70;
		p.setBounds(topBound, bottomBound);
		
		// follower will have a width of 3 and height of 3, and inital offset of 0
		FollowerElement f = new FollowerElement(3, 3, 0);
		
		// Attached element will have a height of 2, width of 2, offset of 4, and hover of 4
		AttachedElement a = new AttachedElement(2, 2, 4, 4);
		
		// Add the follower and attached elements to the lift
		p.addAssociated(a);
		p.addAssociated(f);
		
		// Lets check the list of associated elements. 
		// The length of the list should be 2.
		assertEquals(2, p.getAssociated().size());
		
		// Try calling update() to try to see if anything breaks.
		p.update();
		p.update();
		
		// List should still be length 2
		assertEquals(2, p.getAssociated().size());
		
		// Set velocity and update and see if anything breaks
		p.setVelocity(8, 0);
		f.setVelocity(3, 0);

		p.update();
		
		// List should still be length 2
		assertEquals(2, p.getAssociated().size());
		
		// Lets try deleting the associated elements.
		a.markForDeletion();
		f.markForDeletion();
		p.deleteMarkedAssociated();
		
		// The length of the associated elements list should be zero.
		assertEquals(0, p.getAssociated().size());
	}

}
