import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Tests for the stack and queue classes.
 *
 * @author Emma Barron
 * @version 1.0
 */
public class BarronTest {

    private ArrayStack<Integer> arrayStack;
    private ArrayQueue<Integer> arrayQueue;
    private LinkedStack<Integer> linkedStack;
    private LinkedQueue<Integer> linkedQueue;

    public static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT)
    public void testArrayStackPush() {
        arrayStack = new ArrayStack<>();
        Object[] expected = new Object[9];
        Object[] expected2 = new Object[18];

        for (int i = 0; i < 8; i++) {
            arrayStack.push(i);
            expected[i] = i;
            expected2[i] = i;
        }
        // [0, 1, 2, 3, 4, 5, 6, 7, null]
        assertArrayEquals(expected, arrayStack.getBackingArray());

        // test doubling the array's size
        for (int j = 8; j < 12; j++) {
            arrayStack.push(j);
            expected2[j] = j;
        }

        assertArrayEquals(expected2, arrayStack.getBackingArray());
        assertEquals(12, arrayStack.size());

    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayStackPop() {
        arrayStack = new ArrayStack<>();

        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        // [1 2 3 4 n n n n n]

        // check basic pop
        assertEquals((Integer) 4, arrayStack.pop());
        assertEquals((Integer) 3, arrayStack.pop());
        // [1 2 n n n n n n n]

        // check size
        assertEquals(2, arrayStack.size());

        for (int i = 3; i < 10; i++) {
            arrayStack.push(i);
        }

        // pop when the array is full
        assertEquals((Integer) 9, arrayStack.pop());

        // double the backingArray!
        arrayStack.push(9);
        arrayStack.push(10);

        // check size
        assertEquals(10, arrayStack.size());

        // pop when array was just doubled, and pop until end
        for (int i = 10; i > 0; i--) {
            assertEquals((Integer) i, arrayStack.pop());
        }

        // error message for pop() at 0
        arrayStack.pop();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayStackPeek() {
        arrayStack = new ArrayStack<>();
        assertEquals(0, arrayStack.size());

        // check when arrayStack is empty
        assertNull(arrayStack.peek());

        // checks peeking works for 0 to 9
        // it checks peek() works when backingArray is doubled, too
        for (int i = 0; i < 9; i++) {
            arrayStack.push(i);
        }
        assertEquals((Integer) 8, arrayStack.peek());
        arrayStack.push(9);

        for (int i = 9; i >= 0; i--) {
            assertEquals((Integer) i, arrayStack.peek());
            assertEquals(i + 1, arrayStack.size());
            arrayStack.pop();
        }

        assertNull(arrayStack.peek());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedStackPush() {
        linkedStack = new LinkedStack<>();
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.getHead());

        // 0 1 2
        for (int i = 0; i < 3; i++) {
            linkedStack.push(i);
        }
        assertEquals(3, linkedStack.size());

        LinkedNode<Integer> curr = linkedStack.getHead();
        for (int i = 2; i >= 0; i--) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getNext();
            assertEquals((Integer) i, linkedStack.pop());
        }
        assertNull(curr);
        assertEquals(0, linkedStack.size());

        linkedStack.push(1);
        assertEquals((Integer) 1, linkedStack.pop());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedStackPop() {
        linkedStack = new LinkedStack<>();
        assertEquals(0, linkedStack.size());

        for (int i = 0; i < 6; i++) {
            linkedStack.push(i);
        }
        assertEquals((Integer) 5, linkedStack.pop());
        assertEquals(5, linkedStack.size());

        for (int i = 4; i >= 0; i--) {
            assertEquals((Integer) i, linkedStack.pop());
        }
        assertEquals(0, linkedStack.size());

        linkedStack.pop();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedStackPeek() {
        linkedStack = new LinkedStack<>();
        assertEquals(0, linkedStack.size());

        for (int i = 0; i < 6; i++) {
            linkedStack.push(i);
        }
        assertEquals((Integer) 5, linkedStack.peek());
        assertEquals(6, linkedStack.size());

        for (int i = 5; i >= 0; i--) {
            assertEquals((Integer) i, linkedStack.peek());
            linkedStack.pop();
        }
        assertEquals(0, linkedStack.size());
        assertNull(linkedStack.peek());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueueEnqueue() {
        arrayQueue = new ArrayQueue<>();
        assertEquals(0, arrayQueue.size());
        Object[] expected = new Object[9];
        Object[] expected2 = new Object[9 * 2];

        // test basic enqueue
        // [0, 1, 2, 3, _, _, _, _, _]
        for (int i = 0; i < 4; i++) {
            arrayQueue.enqueue(i);
            expected[i] = i;
        }
        assertEquals(4, arrayQueue.size());
        assertArrayEquals(expected, arrayQueue.getBackingArray());

        // test basic dequeue
        // [_, _, 2, 3, _, _, _, _, _]
        for (int i = 0; i < 2; i++) {
            assertEquals((Integer) i, arrayQueue.dequeue());
            expected[i] = null;
        }
        assertEquals(2, arrayQueue.size());

        // enqueue at end after having dequeued
        // [_, _, 2, 3, 4, 5, 6, 7, 8]
        for (int i = 4; i < 9; i++) {
            arrayQueue.enqueue(i);
            expected[i] = i;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(7, arrayQueue.size());

        // enqueue wrap-around
        // [9, 10, 2, 3, 4, 5, 6, 7, 8]
        for (int i = 0; i < 2; i++) {
            arrayQueue.enqueue(i + 9);
            expected[i] = i + 9;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(9, arrayQueue.size());

        // enqueue double and straighten backing array
        // [2, 3, 4, 5, ... 10, 11, _, _, _ ....]
        arrayQueue.enqueue(11);
        assertEquals(10, arrayQueue.size());
        for (int i = 0; i < 10; i++) {
            expected2[i] = i + 2;
        }
        assertArrayEquals(expected2, arrayQueue.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayQueueDequeue() {
        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        assertEquals(0, arrayQueue.size());
        Object[] expected = new Object[9];

        // test basic enqueue
        // [0, 1, 2, 3, _, _, _, _, _]
        for (int i = 0; i < 4; i++) {
            arrayQueue.enqueue(i);
            expected[i] = i;
        }
        assertEquals(4, arrayQueue.size());
        assertArrayEquals(expected, arrayQueue.getBackingArray());

        // test basic dequeue
        // [_, _, 2, 3, _, _, _, _, _]
        for (int i = 0; i < 2; i++) {
            //arrayQueue.dequeue();
            assertEquals((Integer) i, arrayQueue.dequeue());
            expected[i] = null;
        }
        assertEquals(2, arrayQueue.size());

        // enqueue at end after having dequeued
        // [_, _, 2, 3, 4, 5, 6, 7, 8]
        for (int i = 4; i < 9; i++) {
            arrayQueue.enqueue(i);
            expected[i] = i;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(7, arrayQueue.size());

        // enqueue wrap-around
        // [9, 10, 2, 3, 4, 5, 6, 7, 8]
        for (int i = 0; i < 2; i++) {
            arrayQueue.enqueue(i + 9);
            expected[i] = i + 9;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(9, arrayQueue.size());

        // dequeue
        // [9, 10, _, _, _, _, _, _, 8]
        for (int i = 2; i < 8; i++ ) {
            assertEquals((Integer) i, arrayQueue.dequeue());
            //arrayQueue.dequeue();
            expected[i] = null;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(3, arrayQueue.size());

        // dequeue last one
        assertEquals((Integer) 8, arrayQueue.dequeue());
        expected[8] = null;

        // dequeue wrap around?
        assertEquals((Integer) 9, arrayQueue.dequeue());
        expected[0] = null;

        // dequeue last element
        assertEquals((Integer) 10, arrayQueue.dequeue());
        expected[1] = null;
        assertEquals(0, arrayQueue.size());

        // check if arrayQueue resets front = 0
        arrayQueue.enqueue(10);
        expected[0] = 10;
        //assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(1, arrayQueue.size());

        // check NoSuchElement error
        arrayQueue.dequeue(); // return 10
        arrayQueue.dequeue(); // throws error

    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueuePeek() {
        arrayQueue = new ArrayQueue<>();
        assertEquals(0, arrayQueue.size());
        Object[] expected = new Object[9];

        // test basic enqueue
        // [0, 1, 2, 3, _, _, _, _, _]
        for (int i = 0; i < 4; i++) {
            arrayQueue.enqueue(i);
            expected[i] = i;
        }

        // test basic peek
        // [_, _, 2, 3, _, _, _, _, _]
        for (int i = 0; i < 2; i++) {
            assertEquals((Integer) i, arrayQueue.peek());
            arrayQueue.dequeue();
            expected[i] = null;
        }
        assertEquals(2, arrayQueue.size());

        // enqueue at end after having dequeued
        // [_, _, 2, 3, 4, 5, 6, 7, 8]
        for (int i = 4; i < 9; i++) {
            arrayQueue.enqueue(i);
            expected[i] = i;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(7, arrayQueue.size());

        // enqueue wrap-around
        // [9, 10, 2, 3, 4, 5, 6, 7, 8]
        for (int i = 0; i < 2; i++) {
            arrayQueue.enqueue(i + 9);
            expected[i] = i + 9;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(9, arrayQueue.size());

        // peek
        // [9, 10, _, _, _, _, _, _, 8]
        for (int i = 2; i < 8; i++ ) {
            assertEquals((Integer) i, arrayQueue.peek());
            arrayQueue.dequeue();
            expected[i] = null;
        }
        assertArrayEquals(expected, arrayQueue.getBackingArray());
        assertEquals(3, arrayQueue.size());

        // peek last index before wrap-around
        assertEquals((Integer) 8, arrayQueue.peek());
        arrayQueue.dequeue();
        expected[8] = null;

        // peek wrap around
        assertEquals((Integer) 9, arrayQueue.peek());
        arrayQueue.dequeue();
        expected[0] = null;

        // peek at last element (which is at index 1)
        assertEquals((Integer) 10, arrayQueue.peek());
        arrayQueue.dequeue();
        expected[1] = null;
        assertEquals(0, arrayQueue.size());

        // check null when queue is empty
        assertNull(arrayQueue.peek());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedQueueEnqueue() {
        linkedQueue = new LinkedQueue<>();
        assertEquals(0, linkedQueue.size());

        // basic enqueue
        for (int i = 0; i < 4; i++) {
            linkedQueue.enqueue(i);
        }
        assertEquals(4, linkedQueue.size());

        LinkedNode<Integer> curr = linkedQueue.getHead();

        // check you can add after emptying list
        for (int i = 0; i < 4; i++) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getNext();
            assertEquals((Integer) i, linkedQueue.dequeue());
        }
        assertNull(linkedQueue.getHead());
        linkedQueue.enqueue(10);
        assertEquals(1, linkedQueue.size());

        // check you cannot add null
        linkedQueue.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedQueueDequeue() {
        linkedQueue = new LinkedQueue<>();
        assertEquals(0, linkedQueue.size());

        // basic enqueue
        for (int i = 0; i < 4; i++) {
            linkedQueue.enqueue(i);
        }
        assertEquals(4, linkedQueue.size());

        LinkedNode<Integer> curr = linkedQueue.getHead();

        // dequeue entire list
        for (int i = 0; i < 4; i++) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getNext();
            assertEquals((Integer) i, linkedQueue.dequeue());
        }
        assertEquals(0, linkedQueue.size());

        // enqueue then dequeue again
        linkedQueue.enqueue(10);
        assertEquals((Integer)10, linkedQueue.dequeue());
        assertEquals(0, linkedQueue.size());

        // check cannot dequeue when size = 0
        linkedQueue.dequeue();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedQueuePeek() {
        linkedQueue = new LinkedQueue<>();
        assertEquals(0, linkedQueue.size());

        // basic enqueue
        for (int i = 0; i < 4; i++) {
            linkedQueue.enqueue(i);
        }
        assertEquals(4, linkedQueue.size());

        LinkedNode<Integer> curr = linkedQueue.getHead();

        // check you can add after emptying list
        for (int i = 0; i < 4; i++) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getNext();
            assertEquals((Integer) i, linkedQueue.peek());
            linkedQueue.dequeue();
        }

        linkedQueue.enqueue(10);
        assertEquals((Integer) 10, linkedQueue.peek());
        assertEquals(1, linkedQueue.size());
        linkedQueue.dequeue();

        // peek when list is empty
        assertNull(linkedQueue.peek());
    }
}