/**
 * Your implementation of an array-backed queue.
 *
 * @author Andrew Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class ArrayQueue<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * The initial capacity of a queue with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.front = 0;
    }

    /**
     * Adds the given data to the queue.
     * <p>
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length. If a resize is necessary,
     * you should copy elements to the front of the new array and reset
     * front to 0.
     * <p>
     * This method should be implemented in amortized O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null elemen"
                + "t into data structure.");
        } else {
            if (this.size() == this.backingArray.length) {
                T[] outputArray = (T[]) new
                    Object[this.backingArray.length * 2];
                if (this.front > 0) {
                    for (int i = 0; i < this.size() - this.front; i++) {
                        outputArray[i] = this.backingArray[i + this.front];
                    }
                    for (int i = 0; i < this.front; i++) {
                        outputArray[i + this.size() - this.front] =
                            this.backingArray[i];
                    }
                } else {
                    for (int i = 0; i < this.size(); i++) {
                        outputArray[i] = this.backingArray[i];
                    }
                }
                outputArray[this.size()] = data;
                this.backingArray = outputArray;
                size++;
                this.front = 0;
            } else {
                this.backingArray[(this.size() + front)
                    % this.backingArray.length] = data;
                size++;
            }
        }
    }

    /**
     * Removes the data from the front of the queue.
     * <p>
     * Do not shrink the backing array. If the queue becomes empty as a result
     * of this call, you should explicitly reset front to 0.
     * <p>
     * You should replace any spots that you dequeue from with null. Failure to
     * do so can result in a loss of points.
     * <p>
     * This method should be implemented in O(1) time.
     * <p>
     * See the homework pdf for more information on implementation details.
     *
     * @return the data from the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("No data in Queue ther"
                + "efore unable to dequeue");
        }
        int index = front;
        if (index + 1 == this.backingArray.length) {
            this.front = 0;
        } else {
            front += 1;
        }
        T data = this.backingArray[index];
        this.backingArray[index] = null;
        size--;
        return data;
    }

    /**
     * Retrieves the next data to be dequeued without removing it.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the queue is empty
     */
    public T peek() {
        if (this.size() == 0) {
            return null;
        } else {
            return this.backingArray[this.front];
        }
    }

    /**
     * Returns the size of the queue.
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
     * Returns the backing array of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}