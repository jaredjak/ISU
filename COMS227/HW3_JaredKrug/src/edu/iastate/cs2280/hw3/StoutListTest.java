//package edu.iastate.cs2280.hw3;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.InputMismatchException;
//import java.util.Iterator;
//import java.util.ListIterator;
//import java.util.NoSuchElementException;
//import java.util.Arrays;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//
//class StoutListTest {
//
//	private StoutList<Integer> sl;
//	
//	@BeforeEach
//	void setUp() throws Exception {
//		sl = new StoutList<>();
//		sl.add(1);
//		sl.add(2);
//		sl.add(2, 3);
//		sl.add(3, 4);
//		sl.add(5);
//	}
//
//	@Test
//	void testGeneral() {
//		// Constructor
//		assertThrows(Exception.class, ()->{
//			new StoutList<Integer>(5);
//		});
//		
//		assertTrue(sl.toStringInternal().equals("[(1, 2, 3, 4), (5, -, -, -)]"));
//		
//		// split
//		sl.add(2, 6);
//		assertTrue(sl.toStringInternal().equals("[(1, 2, 6, -), (3, 4, -, -), (5, -, -, -)]"));
//		
//		// full merge
//		sl.remove(2);
//		assertTrue(sl.toStringInternal().equals("[(1, 2, -, -), (3, 4, -, -), (5, -, -, -)]"));
//		sl.remove(2); 
//		assertTrue(sl.toStringInternal().equals("[(1, 2, -, -), (4, 5, -, -)]"));
//
//		// mini-merge
//		sl.add(4, 6);
//		sl.add(3, 7);
//		assertTrue(sl.toStringInternal().equals("[(1, 2, -, -), (4, 7, 5, 6)]"));
//		sl.remove(0); 
//		assertTrue(sl.toStringInternal().equals("[(2, 4, -, -), (7, 5, 6, -)]"));
//		
//		// delete last node
//		sl.add(8);
//		sl.add(9);
//		assertTrue(sl.toStringInternal().equals("[(2, 4, -, -), (7, 5, 6, 8), (9, -, -, -)]"));
//		sl.remove(6);
//		assertTrue(sl.toStringInternal().equals("[(2, 4, -, -), (7, 5, 6, 8)]"));
//		sl.add(6, 9);
//		sl.remove(5);
//		assertTrue(sl.toStringInternal().equals("[(2, 4, -, -), (7, 5, 6, -), (9, -, -, -)]"));
//		sl.remove(5);
//		assertTrue(sl.toStringInternal().equals("[(2, 4, -, -), (7, 5, 6, -)]"));
//		
//		// set
//		sl.set(1, 30);
//		sl.set(1, 31);
//		sl.set(3, -40);
//		assertTrue(sl.toStringInternal().equals("[(2, 31, -, -), (7, -40, 6, -)]"));
//		
//		// index out of bounds
//		assertThrows(IndexOutOfBoundsException.class, ()->{
//			sl.add(-1, 5);
//		});
//		assertThrows(IndexOutOfBoundsException.class, ()->{
//			sl.add(6, 5);
//		});
//		assertThrows(IndexOutOfBoundsException.class, ()->{
//			sl.remove(-1);
//		});
//		assertThrows(IndexOutOfBoundsException.class, ()->{
//			sl.remove(5);
//		});
//		
//		// size
//		assertEquals(sl.size(), 5);
//		
//		// add null
//		assertThrows(NullPointerException.class, ()->{
//			sl.add(null);
//		});
//		assertThrows(NullPointerException.class, ()->{
//			sl.add(0, null);
//		});
//		
//		//System.out.println(sl.toStringInternal());
//	}
	
	
//	@Test
//	void testIterator() {
//		assertThrows(IndexOutOfBoundsException.class, ()->{
//			sl.listIterator(-1);
//		});
//		
//		Iterator<Integer> iter = sl.iterator();
//
//		// next
//		for (int i = 0; i < 5; i++) {
//			assertEquals(iter.next(), i + 1);
//		}
//		
//		// remove
//		iter = sl.iterator();
//		iter.next();
//		iter.remove();
//		iter.next();
//		iter.remove();
//		assertTrue(sl.toStringInternal().equals("[(3, 4, -, -), (5, -, -, -)]"));
//		
//		// hasNext
//		while (iter.hasNext()) {
//			iter.next();
//			iter.remove();
//		}
//		
//		//I snagged these tests, but I'm not sure this is right.
////		assertTrue(sl.toStringInternal().equals("[]"));
//		
//		System.out.println(sl.toStringInternal());
//	}