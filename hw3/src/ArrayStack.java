/**
 * Your implementation of an array-backed stack.
 *
 * @author Andrew Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class ArrayStack<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of a stack with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     * <p>
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length.
     * <p>
     * This method should be implemented in amortized O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("No data in Queue ther"
                + "efore unable to dequeue");
        } else {
            if (this.size == this.backingArray.length) {
                T[] bufferArray = (T[]) new Object[this.backingArray.length
                    * 2];
                for (int i = 0; i < this.size(); i++) {
                    bufferArray[i] = this.backingArray[i];
                }
                bufferArray[this.size()] = data;
                this.backingArray = bufferArray;
                size++;
            } else {
                this.backingArray[this.size()] = data;
                size++;
            }
        }
    }

    /**
     * Removes and returns the top-most element on the stack.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * You should replace any spots that you pop from with null. Failure to do
     * so can result in a loss of points.
     * <p>
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (this.size() == 0) {
            throw new java.util.NoSuchElementException("The stack is therefore "
                + "empty "
                + "thus No Element "
                + "can be popped from the stack.");
        } else {
            T outputData = this.backingArray[this.size - 1];
            this.backingArray[this.size - 1] = null;
            this.size--;
            return outputData;
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
        if (this.size() == this.backingArray.length) {
            return this.backingArray[this.size - 1];
        } else if (this.size() == 0) {
            return null;
        } else {
            return this.backingArray[this.size - 1];
        }
    }

    /**
     * Returns the size of the stack.
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
     * Returns the backing array of the stack.
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