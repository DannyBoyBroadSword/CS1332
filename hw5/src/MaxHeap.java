import java.util.ArrayList;

/**
 * Your implementation of a max heap.
 *
 * @author Andrew Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class MaxHeap<T extends Comparable<? super T>> {

    // DO NOT ADD OR MODIFY THESE INSTANCE/CLASS VARIABLES.
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of INITIAL_CAPACITY
     * for the backing array.
     * <p>
     * Use the constant field provided. Do not use magic numbers!
     */
    public MaxHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * The data in the backingArray should be in the same order as it appears
     * in the passed in ArrayList before you start the Build Heap Algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     * <p>
     * If i is the index of the parent, then the index of the left child is 2i,
     * and the index of the right child is 2i + 1.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("list of data you are "
                + "trying to input is null");
        } else {
            this.backingArray = (T[]) new Comparable[2 * data.size() + 1];
            this.size = 0;
            for (T item : data) {
                if (item == null) {
                    throw new IllegalArgumentException("data you are "
                        + "trying to input is null");
                } else {
                    this.backingArray[size + 1] = item;
                    this.size++;
                }
            }
            for (int i = size() / 2; i > 0; i--) {
                heapify(i);
            }
        }
    }

    /**
     * returns if current node has a left child
     *
     * @param pos the node to check if parent has left child. Unlike
     * asking for leaf we need to check if there is a left node in order to swap
     * nodes that have one child but also break order.
     * @return boolean if node pos * 2 is less than size.
     */
    private boolean hasLeft(int pos) {
        return (pos * 2) < this.size() + 1;
    }

    /**
     * returns boolean if node has child to its right.
     *
     * @param pos the parent node
     * @return if the parent node at index pos has a right child
     */
    private boolean hasRight(int pos) {
        return (pos * 2 + 1) < this.size() + 1;
    }

    /**
     * returns leftChild of current node
     *
     * @param pos the position of the parent node
     * @return the index of the left child.
     */
    private int leftChild(int pos) {
        return (2 * pos);
    }

    /**
     * returns rightChild of current node
     *
     * @param pos the position of the parent node
     * @return the index of the right child.
     */
    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    /**
     * swaps elements who have been determined to break order prop of max heap
     *
     * @param fpos the first position (parent)
     * @param spos the second position or child (swap)
     */
    private void swap(int fpos, int spos) {
        T tmp;
        tmp = this.backingArray[fpos];
        this.backingArray[fpos] = this.backingArray[spos];
        this.backingArray[spos] = tmp;
    }

    /**
     * private method to return data of node's parent.
     *
     * @param pos root that dictates location of parent index.
     * @return data at parent node
     */
    private T parentData(int pos) {
        return this.backingArray[pos / 2];
    }

    /**
     * A private helper method that makes a root and children look like a heap.
     *
     * @param pos is the position to create the struct of a heap at this node
     */
    private void heapify(int pos) {
        if (hasLeft(pos)) {
            if (hasRight(pos)) {
                if (this.backingArray[pos].
                    compareTo(this.backingArray[leftChild(pos)]) < 0
                    || this.backingArray[pos].
                    compareTo(this.backingArray[rightChild(pos)]) < 0) {
                    if (this.backingArray[leftChild(pos)].
                        compareTo(this.backingArray[rightChild(pos)]) > 0) {
                        swap(pos, leftChild(pos));
                        heapify(leftChild(pos));
                    } else if (this.backingArray[rightChild(pos)].
                        compareTo(this.backingArray[leftChild(pos)]) > 0) {
                        swap(pos, rightChild(pos));
                        heapify(rightChild(pos));
                    }
                }
            } else {
                if (this.backingArray[leftChild(pos)].
                    compareTo(this.backingArray[pos]) > 0) {
                    swap(pos, leftChild(pos));
                    heapify(leftChild(pos));
                }
            }
        }
    }


    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @param item the item to be added to the heap
     * @throws IllegalArgumentException if the item is null
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("the item you are trying to add"
                + "is null");
        }
        if (this.size() + 2 > this.getBackingArray().length) {
            T[] bufferBacking = (T[]) new
                Comparable[this.getBackingArray().length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                bufferBacking[i] = backingArray[i];
            }
            this.backingArray = bufferBacking;
        }
        backingArray[this.size() + 1] = item;
        this.size++;
        heapUp(this.size());
    }

    /**
     * recursive function that bubbles data from bottom to the proper position
     * in array
     *
     * @param pos the index to start heapUp. usually occurs at last index.
     */
    private void heapUp(int pos) {
        if (parentData(pos) != null && parentData(pos).
            compareTo(this.backingArray[pos]) < 0) {
            swap(pos, pos / 2);
            heapUp(pos / 2);
        }
    }


    /**
     * Removes and returns the max item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the removed item
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty");
        }
        T buffer = this.backingArray[1];
        this.backingArray[1] = this.backingArray[this.size()];
        this.backingArray[this.size()] = null;
        this.size--;
        downHeap(1);
        return buffer;
    }

    /**
     * Recursive private method to make sure order property is present
     * in max heap.
     * @param pos the root node.
     */
    private void downHeap(int pos) {
        if (hasLeft(pos)) {
            if (hasRight(pos)) {
                if (this.backingArray[pos].
                    compareTo(this.backingArray[leftChild(pos)]) < 0
                    || this.backingArray[pos].
                    compareTo(this.backingArray[rightChild(pos)]) < 0) {
                    if (this.backingArray[leftChild(pos)].
                        compareTo(this.backingArray[rightChild(pos)]) > 0) {
                        swap(pos, leftChild(pos));
                        downHeap(leftChild(pos));
                    } else if (this.backingArray[rightChild(pos)].
                        compareTo(this.backingArray[leftChild(pos)]) > 0) {
                        swap(pos, rightChild(pos));
                        downHeap(rightChild(pos));
                    }
                }
            } else {
                if (this.backingArray[leftChild(pos)].
                    compareTo(this.backingArray[pos]) > 0) {
                    swap(pos, leftChild(pos));
                    downHeap(leftChild(pos));
                }
            }
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element, null if the heap is empty
     */
    public T getMax() {
        return this.backingArray[1];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.backingArray[1] == null;
    }

    /**
     * Clears the heap and rests the backing array to a new array of capacity
     * {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}