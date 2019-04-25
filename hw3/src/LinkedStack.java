/**
 * Your implementation of a linked stack. It should NOT be circular.
 *
 * @author Andrew Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 90309743
 */
public class LinkedStack<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you inputed is null"
                + "therefore Illegal Argument");
        } else {
            LinkedNode<T> oldHead = head;
            head = new LinkedNode<T>(data);
            head.setNext(oldHead);
            size++;
        }

    }

    /**
     * Removes and returns the top-most element on the stack.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (head == null || this.size == 0) {
            throw new java.util.NoSuchElementException("The stack is empty "
                + "therefore values cannot be popped from it");
        } else {
            T data = this.head.getData();
            LinkedNode<T> oldFirst = head.getNext();
            this.head = oldFirst;
            size--;
            return data;
        }
    }

    /**
     * Retrieves the next element to be popped without removing it.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the stack is empty
     */
    public T peek() {
        if (head == null) {
            return null;
        } else {
            return head.getData();
        }
    }

    /**
     * Return the size of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the head node of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }
}