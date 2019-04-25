import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

/**
 * A set of JUnit tests for CS 1332 HW 06 (Hash Maps)
 *
 * @author Akshay Sathiya
 * @version 1.0
 */
public class AkshaySathiyaHashMapJUnitTests {

    private HashMap<Integer, String> hashMap;
    private static final int TIMEOUT = 200;
    private static final int NUMTESTS = 7;
    private static int numTestsPassed = 0;

    @Before
    public void setUp() {
        // create a new HashMap
        hashMap = new HashMap<>();
    }

    @Test (timeout = TIMEOUT)
    public void testNoArgConstructor() {
        // HashMap has already been created with the no-arg constructor

        // check to see that the size of the HashMap is 0
        assertEquals(0, hashMap.size());

        /*
        create an expectation of the HashMap's backing array, and check to see
            if the actual backing array of the HashMap is the same as the
            expected backing array
         */
        MapEntry<Integer, String>[] expectedBackingArray =
            (MapEntry<Integer, String>[])
                new MapEntry<?, ?>[HashMap.INITIAL_CAPACITY];
        assertArrayEquals(expectedBackingArray, hashMap.getTable());

        // report that the test has been passed, increment numTestsPassed
        System.out.println("No-arg constructor test passed!");
        numTestsPassed++;
    }

    @Test (timeout = TIMEOUT)
    public void testOneArgConstructor() {
        // create a new HashMap with the one-arg constructor
        hashMap = new HashMap<>(7);

        /*
        create an expectation of the HashMap's backing array, and check to see
            if the actual backing array of the HashMap is the same as the
            expected backing array
         */
        MapEntry<Integer, String>[] expectedBackingArray =
            (MapEntry<Integer, String>[])
                new MapEntry<?, ?>[7];
        assertArrayEquals(expectedBackingArray, hashMap.getTable());

        // report that the test has been passed, increment numTestsPassed
        System.out.println("One-arg constructor test passed!");
        numTestsPassed++;
    }

    @Test (timeout = TIMEOUT)
    public void testPutAndResizeBackingTable() {
        /*
        create a new hash map from the Java standard library to maintain and
            compare against the student implementation
         */
        java.util.HashMap<Integer, String> stdHashMap =
            new java.util.HashMap<>();

        // check to see that the size of both hash maps is 0
        assertEquals(0, stdHashMap.size());
        assertEquals(0, hashMap.size());

        // check to see that the capacity of the backing array is 11
        assertEquals(11, hashMap.getTable().length);

        /*
        add 10 Integer-String pairs to the Java standard library's hash map,
            this causes a resize of the backing array
         */
        stdHashMap.put(1, "Kyle");
        stdHashMap.put(2, "Jeremy");
        stdHashMap.put(3, "Johnny");
        stdHashMap.put(4, "Todd");
        stdHashMap.put(5, "Derek");
        stdHashMap.put(6, "Kevin");
        stdHashMap.put(7, "Jacob");
        stdHashMap.put(8, "Jake");
        stdHashMap.put(9, "Thomas");
        stdHashMap.put(10, "Daniel");

        /*
        add the same 10 Integer-String pairs to the student hash map, this
            causes a resize of the backing array
         */
        hashMap.put(1, "Kyle");
        hashMap.put(2, "Jeremy");
        hashMap.put(3, "Johnny");
        hashMap.put(4, "Todd");
        hashMap.put(5, "Derek");
        hashMap.put(6, "Kevin");
        hashMap.put(7, "Jacob");
        hashMap.put(8, "Jake");
        hashMap.put(9, "Thomas");
        hashMap.put(10, "Daniel");

        // check to see that the size of both hash maps is 10
        assertEquals(10, stdHashMap.size());
        assertEquals(10, hashMap.size());

        // check to see that the capacity of the backing array is 23
        assertEquals(23, hashMap.getTable().length);

        /*
        add 5 Integer-String pairs to the Java standard library's hash map, this
            results in overwriting values of some key-value pairs
         */
        stdHashMap.put(1, "Karen");
        stdHashMap.put(3, "Jessica");
        stdHashMap.put(5, "Julia");
        stdHashMap.put(7, "Talia");
        stdHashMap.put(9, "Danielle");

        /*
        add the same 5 Integer-String pairs to the student hash map, this also
            results in overwriting values of some key-value pairs, check to
            see if the correct values are being overwritten
         */
        assertEquals("Kyle", hashMap.put(1, "Karen"));
        assertEquals("Johnny", hashMap.put(3, "Jessica"));
        assertEquals("Derek", hashMap.put(5, "Julia"));
        assertEquals("Jacob", hashMap.put(7, "Talia"));
        assertEquals("Thomas", hashMap.put(9, "Danielle"));

        // check to see that the size of both hash maps is 15
        assertEquals(10, stdHashMap.size());
        assertEquals(10, hashMap.size());

        // check to see that the capacity of the backing array is 23
        assertEquals(23, hashMap.getTable().length);

        /*
        add 7 new Integer-String pairs to the Java standard library hash map,
            this causes collisions and a resize of the backing array
         */
        stdHashMap.put(75, "Tara");
        stdHashMap.put(64, "Kelly");
        stdHashMap.put(32, "Sally");
        stdHashMap.put(87, "Yolanda");
        stdHashMap.put(99, "Bertha");
        stdHashMap.put(21, "Molly");
        stdHashMap.put(39, "Carly");

        /*
        add 7 new Integer-String pairs to the student hash map,
            this causes collisions and a resize of the backing array
         */
        hashMap.put(75, "Tara"); // at index 28 of backing array
        hashMap.put(64, "Kelly"); // at index 17 of backing array
        hashMap.put(32, "Sally");
        hashMap.put(87, "Yolanda"); // at index 40 of backing array
        hashMap.put(99, "Bertha"); // at index 5 of backing array
        hashMap.put(21, "Molly");
        hashMap.put(39, "Carly");

        // check to see that the size of both hash maps is 17
        assertEquals(17, stdHashMap.size());
        assertEquals(17, hashMap.size());

        // check to see that the capacity of the backing array is 47
        assertEquals(47, hashMap.getTable().length);

        // report that the test has been passed, increment numTestsPassed
        System.out.println("Put and resize backing table tests passed!");
        numTestsPassed++;
    }

    @Test (timeout = TIMEOUT)
    public void testRemoveAndContainsKey() {
        // check to see that the size of the HashMap is 0
        assertEquals(0, hashMap.size());

        // Add 17 Integer-String pairs to the HashMap
        hashMap.put(1, "Kyle");
        hashMap.put(2, "Jeremy");
        hashMap.put(3, "Johnny");
        hashMap.put(4, "Todd");
        hashMap.put(5, "Derek");
        hashMap.put(6, "Kevin");
        hashMap.put(7, "Jacob");
        hashMap.put(8, "Jake");
        hashMap.put(9, "Thomas");
        hashMap.put(10, "Daniel");
        hashMap.put(75, "Tara"); // at index 28 of backing array
        hashMap.put(64, "Kelly"); // at index 17 of backing array
        hashMap.put(32, "Sally");
        hashMap.put(87, "Yolanda"); // at index 40 of backing array
        hashMap.put(99, "Bertha"); // at index 5 of backing array
        hashMap.put(21, "Molly");
        hashMap.put(39, "Carly");

        // overwrite 5 of the String values in the HashMap
        hashMap.put(1, "Karen");
        hashMap.put(3, "Jessica");
        hashMap.put(5, "Julia");
        hashMap.put(7, "Talia");
        hashMap.put(9, "Danielle");

        // check to see that the capacity of the backing array is 47
        assertEquals(47, hashMap.getTable().length);

        // check to see that the size of the HashMap is 17
        assertEquals(17, hashMap.size());

        // check to see if the key 39 is contained in the HashMap
        assertTrue(hashMap.containsKey(39));

        // remove the Integer-String pair with the Integer key 39
        hashMap.remove(39);

        // check to see if the key 39 is no longer contained in the HashMap
        assertFalse(hashMap.containsKey(39));

        // check to see that the size of the HashMap is 16
        assertEquals(16, hashMap.size());

        // check to see if the key 39 is contained in the HashMap
        assertTrue(hashMap.containsKey(32));

        // remove the Integer-String pair with the Integer key 39
        hashMap.remove(32);

        // check to see if the key 39 is no longer contained in the HashMap
        assertFalse(hashMap.containsKey(32));

        // check to see that the size of the HashMap is 15
        assertEquals(15, hashMap.size());

        // check to see if the key 39 is contained in the HashMap
        assertTrue(hashMap.containsKey(21));

        // remove the Integer-String pair with the Integer key 39
        hashMap.remove(21);

        // check to see if the key 39 is no longer contained in the HashMap
        assertFalse(hashMap.containsKey(21));

        // check to see that the size of the HashMap is 14
        assertEquals(14, hashMap.size());

        // check to see if the key 39 is contained in the HashMap
        assertTrue(hashMap.containsKey(9));

        // remove the Integer-String pair with the Integer key 39
        hashMap.remove(9);

        // check to see if the key 39 is no longer contained in the HashMap
        assertFalse(hashMap.containsKey(9));

        // check to see that the size of the HashMap is 13
        assertEquals(13, hashMap.size());

        // check to see if the key 39 is contained in the HashMap
        assertTrue(hashMap.containsKey(3));

        // remove the Integer-String pair with the Integer key 39
        hashMap.remove(3);

        // check to see if the key 39 is no longer contained in the HashMap
        assertFalse(hashMap.containsKey(3));

        // check to see that the size of the HashMap is 12
        assertEquals(12, hashMap.size());

        // check to see that the capacity of the backing array is 47
        assertEquals(47, hashMap.getTable().length);

        // report that the test has been passed, increment numTestsPassed
        System.out.println("Remove and contains key tests passed!");
        numTestsPassed++;
    }

    @Test (timeout = TIMEOUT)
    public void testGet() {
        // check to see that the size of the HashMap is 0
        assertEquals(0, hashMap.size());

        // Add 17 Integer-String pairs to the HashMap
        hashMap.put(1, "Kyle");
        hashMap.put(2, "Jeremy");
        hashMap.put(3, "Johnny");
        hashMap.put(4, "Todd");
        hashMap.put(5, "Derek");
        hashMap.put(6, "Kevin");
        hashMap.put(7, "Jacob");
        hashMap.put(8, "Jake");
        hashMap.put(9, "Thomas");
        hashMap.put(10, "Daniel");
        hashMap.put(75, "Tara"); // at index 28 of backing array
        hashMap.put(64, "Kelly"); // at index 17 of backing array
        hashMap.put(32, "Sally");
        hashMap.put(87, "Yolanda"); // at index 40 of backing array
        hashMap.put(99, "Bertha"); // at index 5 of backing array
        hashMap.put(21, "Molly");
        hashMap.put(39, "Carly");

        // overwrite 5 of the String values in the HashMap
        hashMap.put(1, "Karen");
        hashMap.put(3, "Jessica");
        hashMap.put(5, "Julia");
        hashMap.put(7, "Talia");
        hashMap.put(9, "Danielle");

        // check to see that the size of the HashMap is 17
        assertEquals(17, hashMap.size());

        // check to see that the capacity of the backing array is 47
        assertEquals(47, hashMap.getTable().length);

        /*
        check to see if the correct String values are paired with the correct
            Integer keys
         */
        assertEquals("Karen", hashMap.get(1));
        assertEquals("Jeremy", hashMap.get(2));
        assertEquals("Jessica", hashMap.get(3));
        assertEquals("Todd", hashMap.get(4));
        assertEquals("Julia", hashMap.get(5));
        assertEquals("Kevin", hashMap.get(6));
        assertEquals("Talia", hashMap.get(7));
        assertEquals("Jake", hashMap.get(8));
        assertEquals("Danielle", hashMap.get(9));
        assertEquals("Daniel", hashMap.get(10));
        assertEquals("Sally", hashMap.get(32));
        assertEquals("Bertha", hashMap.get(99));

        // check to see that the size of the HashMap is 17
        assertEquals(17, hashMap.size());

        // check to see that the capacity of the backing array is 47
        assertEquals(47, hashMap.getTable().length);

        // report that the test has been passed, increment numTestsPassed
        System.out.println("Get test passed!");
        numTestsPassed++;
    }

    @Test (timeout = TIMEOUT)
    public void testKeySetAndValues() {
        // Add 17 Integer-String pairs to the HashMap
        hashMap.put(1, "Kyle");
        hashMap.put(2, "Jeremy");
        hashMap.put(3, "Johnny");
        hashMap.put(4, "Todd");
        hashMap.put(5, "Derek");
        hashMap.put(6, "Kevin");
        hashMap.put(7, "Jacob");
        hashMap.put(8, "Jake");
        hashMap.put(9, "Thomas");
        hashMap.put(10, "Daniel");
        hashMap.put(75, "Tara"); // at index 28 of backing array
        hashMap.put(64, "Kelly"); // at index 17 of backing array
        hashMap.put(32, "Sally");
        hashMap.put(87, "Yolanda"); // at index 40 of backing array
        hashMap.put(99, "Bertha"); // at index 5 of backing array
        hashMap.put(21, "Molly");
        hashMap.put(39, "Carly");

        // overwrite 5 of the String values in the HashMap
        hashMap.put(1, "Karen");
        hashMap.put(3, "Jessica");
        hashMap.put(5, "Julia");
        hashMap.put(7, "Talia");
        hashMap.put(9, "Danielle");

        // create a Set of the expected Integer keys
        Set<Integer> expectedKeySet = new HashSet<>();
        for (int i = 1; i < 11; i++) {
            expectedKeySet.add(i);
        }
        expectedKeySet.add(75);
        expectedKeySet.add(64);
        expectedKeySet.add(32);
        expectedKeySet.add(87);
        expectedKeySet.add(99);
        expectedKeySet.add(21);
        expectedKeySet.add(39);

        // obtain the actual key set of the HashMap
        Set<Integer> actualKeySet = hashMap.keySet();

        /*
        check to see if the expected key set is the same as the actual key set
            for the HashMap
         */
        assertArrayEquals(expectedKeySet.toArray(), actualKeySet.toArray());

        // create a List of the expected values of the HashMap
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("Karen");
        expectedValues.add("Jeremy");
        expectedValues.add("Jessica");
        expectedValues.add("Todd");
        expectedValues.add("Bertha");
        expectedValues.add("Julia");
        expectedValues.add("Kevin");
        expectedValues.add("Talia");
        expectedValues.add("Jake");
        expectedValues.add("Danielle");
        expectedValues.add("Daniel");
        expectedValues.add("Kelly");
        expectedValues.add("Molly");
        expectedValues.add("Tara");
        expectedValues.add("Sally");
        expectedValues.add("Carly");
        expectedValues.add("Yolanda");

        // obtain the actual values list of the HashMap
        List<String> actualValues = hashMap.values();

        // check to see that the expected and actual lists of values match
        assertArrayEquals(expectedValues.toArray(), actualValues.toArray());

        // report that the tests have been passed, increment numTestsPassed
        System.out.println("Key set and values tests passed!");
        numTestsPassed++;
    }

    @Test (timeout = TIMEOUT)
    public void testClearSizeAndGetTable() {
        // check to see that the size of the HashMap is 0
        assertEquals(0, hashMap.size());

        /*
        create an expectation of the HashMap's backing array, and check to see
            if the actual backing array of the HashMap is the same as the
            expected backing array
         */
        MapEntry<Integer, String>[] expectedBackingArray =
            (MapEntry<Integer, String>[])
                new MapEntry<?, ?>[HashMap.INITIAL_CAPACITY];
        assertArrayEquals(expectedBackingArray, hashMap.getTable());


        // Add 17 Integer-String pairs to the HashMap
        hashMap.put(1, "Kyle");
        hashMap.put(2, "Jeremy");
        hashMap.put(3, "Johnny");
        hashMap.put(4, "Todd");
        hashMap.put(5, "Derek");
        hashMap.put(6, "Kevin");
        hashMap.put(7, "Jacob");
        hashMap.put(8, "Jake");
        hashMap.put(9, "Thomas");
        hashMap.put(10, "Daniel");
        hashMap.put(75, "Tara"); // at index 28 of backing array
        hashMap.put(64, "Kelly"); // at index 17 of backing array
        hashMap.put(32, "Sally");
        hashMap.put(87, "Yolanda"); // at index 40 of backing array
        hashMap.put(99, "Bertha"); // at index 5 of backing array
        hashMap.put(21, "Molly");
        hashMap.put(39, "Carly");

        // check to see that the size of the HashMap is 17
        assertEquals(17, hashMap.size());

        // clear the HashMap and the Java standard library hash map
        hashMap.clear();

        // check to see that the size of the HashMap is 0
        assertEquals(0, hashMap.size());

        /*
        check to see if the actual backing array of the HashMap is the same as
            expected backing array
         */
        assertArrayEquals(expectedBackingArray, hashMap.getTable());

        // report that the tests have been passed, increment numTestsPassed
        System.out.println("Clear, size, and get table tests passed!");
        numTestsPassed++;
    }

    @AfterClass
    public static void takeDown() {
        if (numTestsPassed ==  NUMTESTS) {
            System.out.println("\nAll tests passed! Congratulations!");
        } else {
            System.out.println("\n" + numTestsPassed + " out of " + NUMTESTS
                + " passed, try again.");
        }
    }
}