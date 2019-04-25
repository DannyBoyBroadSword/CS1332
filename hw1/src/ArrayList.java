/**
 * Your implementation of an ArrayList.
 *
 * @author Andrew Hennessy
 * @userid ahennessy6
 * @GTID 903309743
 * @version 1.0
 */
public class ArrayList<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of the array list.
     *
     * DO NOT CHANGE THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object array to T[] to get the generic typing.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the element to the index specified.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Adding to index {@code size} should be amortized O(1), all other adds are
     * O(n).
     *
     * @param index the index where you want the new element
     * @param data  the data to add to the list
     * @throws java.lang.IndexOutOfBoundsException if index is negative or index
     *         > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot add null element into data structure.");
        } else {
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for backing array " + "of size "
                    + this.size() + ".");
            } else {
                if (this.size() + 1 == this.backingArray.length
                    && index == this.backingArray.length - 1) {
                    this.backingArray[this.backingArray.length - 1] = data;
                } else {
                    if (this.backingArray.length == this.size()) {
                        T[] buffer = (T[]) new Object[(this.size()) * 2];
                        for (int i = 0; i < index; i++) {
                            buffer[i] = this.get(i);
                        }
                        buffer[index] = data;
                        for (int i = index; i < this.backingArray.length; i++) {
                            buffer[i + 1] = this.get(i);
                        }
                        this.size++;
                        this.backingArray = buffer;
                    } else {
                        T[] buffer = (T[]) new Object[this.backingArray.length];
                        for (int i = 0; i < index; i++) {
                            buffer[i] = this.get(i);
                        }
                        buffer[index] = data;
                        for (int i = index; i < this.size(); i++) {
                            buffer[i + 1] = this.get(i);
                        }
                        this.size++;
                        this.backingArray = buffer;
                    }
                }

            }
        }

    }

    /**
     * Adds the given data to the front of your array list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot add null element into data structure.");
        } else {
            if (this.backingArray.length == this.size()) {
                T[] buffer = (T[]) new Object[(this.size()) * 2];
                buffer[0] = data;
                for (int i = 1; i < this.size() + 1; i++) {
                    buffer[i] = this.get(i - 1);
                }
                this.size++;
                this.backingArray = buffer;
            } else {
                T[] buffer = (T[]) new Object[this.backingArray.length];
                buffer[0] = data;
                for (int i = 1; i < this.size() + 1; i++) {
                    buffer[i] = this.get(i - 1);
                }
                this.size++;
                this.backingArray = buffer;
            }

        }

    }

    /**
     * Adds the given data to the back of your array list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot add null element into data structure.");
        } else {
            if (this.size() + 1 == this.backingArray.length) {
                this.backingArray[this.backingArray.length - 1] = data;
                this.size++;
            } else {
                if (this.backingArray.length == this.size()) {
                    T[] buffer = (T[]) new Object[(this.size()) * 2];
                    for (int i = 0; i < this.backingArray.length; i++) {
                        buffer[i] = this.get(i);
                    }
                    buffer[this.backingArray.length] = data;
                    this.size++;
                    this.backingArray = buffer;
                } else {
                    T[] buffer = (T[]) new Object[this.backingArray.length];
                    for (int i = 0; i < this.size(); i++) {
                        buffer[i] = this.get(i);
                    }
                    buffer[this.size()] = data;
                    this.size++;
                    this.backingArray = buffer;
                }

            }
        }
    }

    /**
     * Removes and returns the element at {@code index}.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * This method should be O(1) for index {@code size - 1} and O(n) in all
     * other cases.
     *
     * @param index the index of the element
     * @return the object that was formerly at that index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException("Index " + index
                + " is out of bounds for backing array " + "of size "
                + this.size() + ".");
        } else {
            if (index == this.size() - 1) {
                T reBuffer = this.backingArray[index];
                this.backingArray[index] = null;
                this.size--;
                return reBuffer;
            } else {
                T[] buffer = (T[]) new Object[this.backingArray.length];
                for (int i = 0; i < index; i++) {
                    buffer[i] = this.backingArray[i];
                }
                T reBuffer = this.backingArray[index];
                for (int i = index; i < this.size() - 1; i++) {
                    buffer[i] = this.backingArray[i + 1];
                }
                this.size--;
                this.backingArray = buffer;
                return reBuffer;
            }
        }
    }

    /**
     * Removes and returns the first element in the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data from the front of the list or null if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            return null;
        } else {
            T reBuffer = this.backingArray[0];
            T[] buffer = (T[]) new Object[this.backingArray.length];
            for (int i = 1; i < this.size(); i++) {
                buffer[i - 1] = this.backingArray[i];
            }
            this.size--;
            this.backingArray = buffer;
            return reBuffer;
        }

    }

    /**
     * Removes and returns the last element in the list.
     *
     * Must be O(1).
     *
     * @return the data from the back of the list or null if the list is empty
     */
    public T removeFromBack() {
        if (this.isEmpty()) {
            return null;
        } else {
            T reBuffer = this.backingArray[this.size - 1];
            this.backingArray[this.size - 1] = null;
            this.size--;
            return reBuffer;
        }

    }

    /**
     * Returns the element at the given index.
     *
     * Must be O(1).
     *
     * @param index the index of the element
     * @return the data stored at that index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException("Index " + index
                + " is out of bounds for backing array " + "of size "
                + this.size() + ".");
        } else {
            return this.backingArray[index];
        }
    }

    /**
     * Finds the index at which the given data is located in the ArrayList.
     *
     * If there are multiple instances of the data in the ArrayList, then return
     * the index of the last instance.
     *
     * Be sure to think carefully about whether value or reference equality
     * should be used.
     *
     * Must be O(n), but consider which end of the ArrayList to start from.
     *
     * @param data the data to find the last index of
     * @return the last index of the data or -1 if the data is not in the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public int lastIndexOf(T data) {
        int position = -1;
        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot add null element into data structure.");
        } else {
            for (int i = this.size() - 1; i != -1; i--) {
                if (this.backingArray[i].equals(data)) {
                    position = i;
                    return position;
                }
            }
            return position;

        }

    }

    /**
     * Returns a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return this.backingArray[0] == null;
    }

    /**
     * Clears the list. Resets the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    public void clear() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the size of the list as an integer.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array for this list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array for this list
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}
