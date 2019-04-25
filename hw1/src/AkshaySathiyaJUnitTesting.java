import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This is a JUnit testing class for CS 1332, HW 01, made by Akshay Sathiya.
 *
 * @author Akshay Sathiya
 * @version 1.2
 */
public class AkshaySathiyaJUnitTesting {

    private ArrayList<Double> myList;
    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        myList = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        assertNotNull(myList);
        assertTrue(myList.isEmpty());
        assertTrue(myList.size() == 0);
        assertTrue(myList.getBackingArray().length == 9);
        assertArrayEquals(new Double[9], myList.getBackingArray());
        System.out.println("Constructor test passed!");
    }

    @Test (timeout = TIMEOUT)
    public void testAddAtIndex() {
        try {
            myList.addAtIndex(20, 100.2);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index " + 20 + " is out of bounds for backing array "
                + "of size " + myList.size() + ".", e.getMessage());
        }
        try {
            myList.addAtIndex(0, null);
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot add null element to data structure.",
                e.getMessage());
        }
        myList.addToFront(2.13);
        myList.addToBack(3.51);
        myList.addAtIndex(1, 3.41);
        assertTrue(myList.size() == 3);
        assertFalse(myList.isEmpty());
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        expectedBackingArray[0] = 2.13;
        expectedBackingArray[1] = 3.41;
        expectedBackingArray[2] = 3.51;
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("addAtIndex test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        try {
            myList.addToFront(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot add null element to data structure.",
                e.getMessage());
        }
        myList.addToFront(3.14);
        myList.addToFront(2.71);
        myList.addToFront(1.11);
        assertTrue(myList.size() == 3);
        assertFalse(myList.isEmpty());
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        expectedBackingArray[0] = 1.11;
        expectedBackingArray[1] = 2.71;
        expectedBackingArray[2] = 3.14;
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("addToFront test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        try {
            myList.addToBack(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot add null element to data structure.",
                e.getMessage());
        }
        myList.addToBack(4.50);
        myList.addToBack(3.50);
        myList.addToBack(2.50);
        assertTrue(myList.size() == 3);
        assertFalse(myList.isEmpty());
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        expectedBackingArray[0] = 4.50;
        expectedBackingArray[1] = 3.50;
        expectedBackingArray[2] = 2.50;
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("addToBack test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        try {
            myList.removeAtIndex(100);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index " + 100 + " is out of bounds for backing array "
                + "of size " + myList.size() + ".", e.getMessage());
        }
        myList.addToFront(4.50);
        myList.addToFront(3.50);
        myList.addToFront(2.50);
        myList.addToFront(1.50);
        myList.removeAtIndex(0);
        myList.removeAtIndex(2);
        assertTrue(myList.size() == 2);
        assertFalse(myList.isEmpty());
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        expectedBackingArray[0] = 2.50;
        expectedBackingArray[1] = 3.50;
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("removeAtIndex test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        assertNull(myList.removeFromFront());
        myList.addToFront(3.14);
        myList.addToFront(2.71);
        myList.addToFront(1.11);
        assertTrue(myList.removeFromFront() == 1.11);
        assertTrue(myList.size() == 2);
        assertFalse(myList.isEmpty());
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        expectedBackingArray[0] = 2.71;
        expectedBackingArray[1] = 3.14;
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("removeFromFront test passed!");
    }

    @Test (timeout = TIMEOUT)
    public void testRemoveFromBack() {
        assertNull(myList.removeFromBack());
        myList.addToFront(3.14);
        myList.addToFront(2.71);
        myList.addToFront(1.11);
        assertTrue(myList.removeFromBack() == 3.14);
        assertTrue(myList.size() == 2);
        assertFalse(myList.isEmpty());
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        expectedBackingArray[0] = 1.11;
        expectedBackingArray[1] = 2.71;
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("removeFromBack test passed!");
    }

    @Test (timeout = TIMEOUT)
    public void testSize() {
        myList.addToFront(3.14);
        myList.addToFront(2.71);
        myList.addToFront(1.11);
        assertTrue(myList.size() == 3);
        myList.addToFront(3.14);
        myList.addToFront(2.71);
        myList.addToFront(1.11);
        assertTrue(myList.size() == 6);
        myList.removeFromFront();
        myList.removeFromBack();
        assertTrue(myList.size() == 4);
        System.out.println("size test passed!");
    }

    @Test (timeout = TIMEOUT)
    public void testGet() {
        try {
            myList.get(-20);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index " + -20 + " is out of bounds for backing array "
                + "of size " + myList.size() + ".", e.getMessage());
        }
        myList.addToFront(3.14);
        myList.addToFront(2.71);
        myList.addToFront(1.11);
        myList.addToFront(3.14);
        myList.addToFront(2.71);
        myList.addToFront(1.11);
        myList.removeFromFront();
        myList.removeFromBack();
        assertTrue(myList.get(0) == 2.71);
        assertTrue(myList.get(1) == 3.14);
        assertTrue(myList.get(2) == 1.11);
        assertTrue(myList.get(3) == 2.71);
        System.out.println("get test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testLastIndexOf() {
        try {
            myList.lastIndexOf(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot add null element to data structure.",
                e.getMessage());
        }
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.removeFromFront();
        myList.removeFromBack();
        assertTrue(myList.lastIndexOf(2.71) == 3);
        assertTrue(myList.lastIndexOf(1.11) == 1);
        assertTrue(myList.lastIndexOf(3.14) == 2);
        assertTrue(myList.lastIndexOf(-123.2) == -1);
        System.out.println("lastIndexOf test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        assertTrue(myList.isEmpty());
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.removeFromFront();
        myList.removeFromBack();
        assertFalse(myList.isEmpty());
        System.out.println("isEmpty test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        assertTrue(myList.isEmpty());
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.removeFromFront();
        myList.removeFromBack();
        assertFalse(myList.isEmpty());
        myList.clear();
        assertTrue(myList.isEmpty());
        System.out.println("clear test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testGetBackingArray() {
        assertArrayEquals(new Double[ArrayList.INITIAL_CAPACITY],
            myList.getBackingArray());
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.addToBack(3.14);
        myList.addToBack(2.71);
        myList.addToBack(1.11);
        myList.removeFromFront();
        myList.removeFromBack();
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        expectedBackingArray[0] = 2.71;
        expectedBackingArray[1] = 1.11;
        expectedBackingArray[2] = 3.14;
        expectedBackingArray[3] = 2.71;
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("getBackingArray test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontResizing() {
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        for (int i = 0; i < 9; i++) {
            myList.addToFront(3.14);
            expectedBackingArray[i] = 3.14;
        }
        assertTrue(myList.size() == 9);
        assertTrue(myList.getBackingArray().length == 9);
        assertTrue(myList.size() == myList.getBackingArray().length);
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        myList.addToFront(3.14);
        assertTrue(myList.size() == 10);
        assertTrue(myList.getBackingArray().length == 18);
        assertFalse(myList.size() == myList.getBackingArray().length);
        expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY * 2];
        for (int i = 0; i < 10; i++) {
            expectedBackingArray[i] = 3.14;
        }
        for (int i = 10; i < 18; i++) {
            expectedBackingArray[i] = null;
        }
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("addToFront resizing test passed!");
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackResizing() {
        Object[] expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY];
        for (int i = 0; i < 9; i++) {
            myList.addToBack(3.14);
            expectedBackingArray[i] = 3.14;
        }
        assertTrue(myList.size() == 9);
        assertTrue(myList.getBackingArray().length == 9);
        assertTrue(myList.size() == myList.getBackingArray().length);
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        myList.addToBack(3.14);
        assertTrue(myList.size() == 10);
        assertTrue(myList.getBackingArray().length == 18);
        assertFalse(myList.size() == myList.getBackingArray().length);
        expectedBackingArray = new Object[ArrayList.INITIAL_CAPACITY * 2];
        for (int i = 0; i < 10; i++) {
            expectedBackingArray[i] = 3.14;
        }
        for (int i = 10; i < 18; i++) {
            expectedBackingArray[i] = null;
        }
        assertArrayEquals(expectedBackingArray, myList.getBackingArray());
        System.out.println("addToFront resizing test passed!");
    }

}