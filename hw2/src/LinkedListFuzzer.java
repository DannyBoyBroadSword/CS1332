import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Random;
import java.util.LinkedList;

/* Yeet my CS 1332 friends! I made this fuzzer-esque test to test base and edge cases, but NOT EXCEPTIONS.
 * I know it doesn't actually tell you what you're doing wrong, but hopefully you smart tech students can
 * figure it out. Currently it's set to 100000 iterations but you can change the variable to add more or less.
 *
 * Note: 100000 iterations is a lot, but sometimes not enough. Run multiple times to make sure it works.
 */


/**
 * Linked List Fuzzer
 *
 * @author Lucas Kiefer
 * @version 1.0
 */
public class LinkedListFuzzer {
    private int iterations = 100000;
    private SinglyLinkedList<String> stringList;
    private LinkedList<String> stringJava;
    private static final int TIMEOUT = 200;
    private Random r = new Random();
    int index;
    String data;


    private String randomString() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int index = (int) (r.nextFloat() * chars.length());
        return chars.substring(index, index + 1);
    }
    @Before
    public void setUp() {
        stringList = new SinglyLinkedList<String>(); //your linkedlist
        stringJava = new LinkedList<String>(); //java.util.LinkedList list
    }

    @Test(timeout = TIMEOUT)
    public void addingStrings() {
        for (int i = 0; i < iterations; i++) { //amount of random iterations
            int test = r.nextInt(7);
            //Add at index
            if (test == 0) {
                index = r.nextInt(stringList.size() + 1);
                data = randomString();
                stringList.addAtIndex(index, data);
                stringJava.add(index, data);
                assertArrayEquals("arrays not equal", stringList.toArray(), stringJava.toArray());
                assertEquals("size failed", stringList.size(),stringJava.size());
            }
            //Add to Front
            if (test == 1) {
                data = randomString();
                stringList.addToFront(data);
                stringJava.addFirst(data);
                assertArrayEquals("arrays not equal", stringList.toArray(), stringJava.toArray());
                assertEquals("size failed", stringList.size(),stringJava.size());
            }
            //Add to Back
            if (test == 2) {
                data = randomString();
                stringList.addToBack(data);
                stringJava.addLast(data);
                assertArrayEquals("arrays not equal", stringList.toArray(), stringJava.toArray());
                assertEquals("size failed", stringList.size(),stringJava.size());
            }
            //remove at index
            if (test == 3) {
                if (stringList.size() != 0) {
                    index = r.nextInt(stringList.size());
                    String first = stringList.removeAtIndex(index);
                    String second = stringJava.remove(index);
                    assertArrayEquals("arrays not equal", stringList.toArray(), stringJava.toArray());
                    assertEquals("size failed", stringList.size(),stringJava.size());
                    assertEquals("wrong return", first, second);
                }
            }
            //remove first
            if (test == 4) {
                String first = stringList.removeFromFront();
                String second = stringJava.pollFirst();
                assertArrayEquals("arrays not equal", stringList.toArray(), stringJava.toArray());
                assertEquals("size failed", stringList.size(),stringJava.size());
                assertEquals("wrong return", first, second);
            }
            //remove last
            if (test == 5) {
                String first = stringList.removeFromBack();
                String second = stringJava.pollLast();
                assertArrayEquals("arrays not equal", stringList.toArray(), stringJava.toArray());
                assertEquals("size failed", stringList.size(),stringJava.size());
                assertEquals("wrong return", first, second);
            }
            //remove last occurance
            if (test == 6) {
                if (stringList.size() == 0) {
                    assertNull("size is 0, but no null", stringList.removeLastOccurrence(""));
                } else {
                    if (r.nextInt(2) == 0) {
                        index = r.nextInt(stringList.size());
                        data = stringJava.get(index);
                    } else {
                        data = randomString();
                    }
                    String first = stringList.removeLastOccurrence(data);
                    boolean second = stringJava.removeLastOccurrence(data);
                    boolean yeet = first != null;
                    assertArrayEquals("arrays not equal", stringList.toArray(), stringJava.toArray());
                    assertEquals("size failed", stringList.size(),stringJava.size());
                    assertEquals("wrong return", second, yeet);
                }
            }
        }
        System.out.println("IT WORKED NICE CODING");
    }
}