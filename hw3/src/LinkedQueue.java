/**
 * Your implementation of a linked queue. It should NOT be circular.
 *
 * @author Andrew Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class LinkedQueue<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the given data to the queue.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you inputed is of "
                + "type null therefore can't be enqueue'd");
        } else {
            LinkedNode<T> previousLast = this.tail;
            this.tail = new LinkedNode<T>((T) data);
            if (this.size == 0) {
                this.head = this.tail;
            } else {
                previousLast.setNext(tail);
            }
            size++;
        }
    }

    /**
     * Removes the data from the front of the queue.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @return the data from the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("No elements to "
                + "dequeue because the queue is empty");
        } else {
            T output = this.head.getData();
            LinkedNode<T> previousNext = this.head.getNext();
            this.head = previousNext;
            size--;
            return output;
        }
    }

    /**
     * Retrieves the next data to be dequeued without removing it.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the queue is empty
     */
    public T peek() {
        if (this.size == 0) {
            return null;
        } else {
            return head.getData();
        }
    }

    /**
     * Return the size of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the head node of the queue.
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

    /**
     * Returns the tail node of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }
}