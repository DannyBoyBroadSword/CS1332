/**
 * Your implementation of a circular singly linked list.
 *
 * @author Andrew Boughan Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;


    /**
     * Adds the element to the index specified.
     * <p>
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data  the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     *                                             index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        LinkedListNode<T> newNode = new LinkedListNode<>(data, null);
        LinkedListNode<T> current = getHead();
        LinkedListNode<T> nextBuffer;

        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot add null element into data structure.");
        } else {
            if (index < 0 || index > this.size()) {
                throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for backing array " + "of size "
                    + this.size() + ".");
            } else {
                if (index == 0) {
                    addToFront(data);
                } else if (index == this.size()) {
                    addToBack(data);
                } else {
                    for (int i = 0; i < index - 1; i++) {
                        current = current.getNext();
                    }
                    nextBuffer = current;
                    newNode.setNext(nextBuffer.getNext());
                    current.setNext(newNode);
                    size++;
                }

            }
        }
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        LinkedListNode<T> nextBuffer;
        LinkedListNode<T> newNode = new LinkedListNode<>(data, null);

        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot add null element into data structure.");
        } else {
            if (head == null) {
                head = newNode;
                head.setNext(head);
                size++;
            } else {
                newNode.setData(head.getData());
                newNode.setNext(head.getNext());
                head.setNext(newNode);
                head.setData(data);
                size++;
            }
        }
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        LinkedListNode<T> nextBuffer;
        LinkedListNode<T> newNode = new LinkedListNode<>(data, null);

        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot add null element into data structure.");
        } else {
            if (head == null) {
                head = newNode;
                head.setNext(head);
                size++;
            } else {
                newNode.setData(head.getData());
                head.setData(data);
                newNode.setNext(head.getNext());
                head.setNext(newNode);
                head = head.getNext();
                size++;
            }


        }
    }

    /**
     * Removes and returns the element from the index specified.
     * <p>
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     *                                             index >= size
     */
    public T removeAtIndex(int index) {
        LinkedListNode<T> current = head;
        if (index < 0 || index >= this.size() || this.isEmpty()) {
            throw new IndexOutOfBoundsException("Index " + index
                + " is out of bounds for backing array " + "of size "
                + this.size() + ".");
        } else {
            if (index == 0) {
                return removeFromFront();
            } else if (index == this.size()) {
                return removeFromBack();
            } else {
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                T dataBuffer = current.getNext().getData();
                //System.out.println(current.getNext().getData());
                LinkedListNode<T> nextCurr = current.getNext().getNext();
                current.setNext(nextCurr);
                size--;
                if (size == 0) {
                    head = null;
                }
                return dataBuffer;
            }
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     * <p>
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            return null;
        } else {
            T dataBuffer = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            if (size == 0) {
                head = null;
            }
            return dataBuffer;
        }

    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        LinkedListNode<T> current = head;
        if (this.isEmpty()) {
            return null;
        } else if (this.size() == 1) {
            T data = current.getNext().getData();
            current.setNext(head);
            size--;
            if (size == 0) {
                head = null;
            }
            return data;
        } else {
            for (int i = 0; i < this.size() - 2; i++) {
                //System.out.println(current.getNext().getData());
                current = current.getNext();
            }
            T data = current.getNext().getData();
            //System.out.println(data);
            current.setNext(current.getNext().getNext());
            size--;
            if (size == 0) {
                head = null;
            }
            return data;
        }
    }


    /**
     * Removes the last copy of the given data from the list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the removed data occurrence from the list itself (not the data
     * passed in), null if no occurrence
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public T removeLastOccurrence(T data) {
        int lastIndex = 0;
        boolean cleared = false;
        LinkedListNode<T> current = head;
        if (data == null) {
            throw new IllegalArgumentException(
                "Cannot remove null element from circularly linked list.");
        } else {
            for (int i = 0; i < size(); i++) {
                if (current.getData().equals(data)) {
                    lastIndex = i;
                    cleared = true;
                }
                current = current.getNext();
            }
            if (cleared) {
                return this.removeAtIndex(lastIndex);
            } else {
                return null;
            }
        }
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Getting index 0 should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     *                                             index >= size
     */
    public T get(int index) {
        LinkedListNode<T> current = head;
        if (index < 0 || index >= this.size() || this.isEmpty()) {
            throw new IndexOutOfBoundsException("Index " + index
                + " is out of bounds for backing array " + "of size "
                + this.size() + ".");
        } else {
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        LinkedListNode<T> current = head;
        Object[] output = new Object[size()];
        for (int i = 0; i < size; i++) {
            output[i] = current.getData();
            //System.out.println(output[i]);
            current = current.getNext();
        }
        return output;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     * <p>
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return head == null || size == 0;
    }

    /**
     * Clears the list of all data.
     * <p>
     * Must be O(1) for all cases.
     */
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Returns the number of elements in the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}