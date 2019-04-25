import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Your implementation of HashMap.
 *
 * @author Andrew Hennessy
 * @version 1.0
 * @userid ahennessy6
 * @GTID 903309743
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 11;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of the initialCapacity parameter.
     * <p>
     * You may assume the initialCapacity parameter will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * <p>
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     * <p>
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     * <p>
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. As a
     * warning, be careful about using integer division in the LF calculation!
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   key to add into the HashMap
     * @param value value to add into the HashMap
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Your key value is null");
        }
        if (value == null) {
            throw new IllegalArgumentException("The value your trying to put "
                + "is null");
        }
        double lf = ((double) (size() + 1)) / (double) table.length;
        if (lf > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int index = hashHelper(key);
        if (table[index] != null) {
            MapEntry<K, V> cursor = table[index];
            while (cursor != null) {
                if (cursor.getKey().equals(key)) {
                    V oldCursorValue = cursor.getValue(); //hold old crsr value
                    cursor.setValue(value); //set new value
                    return oldCursorValue;
                }
                cursor = cursor.getNext();
            }
        }
        MapEntry<K, V> nextBuffer = table[index];
        table[index] = new MapEntry<K, V>(key, value);
        table[index].setNext(nextBuffer);
        size++;
        return null;
    }




    /**
     * private helper method to reduce clutter when getting hashCode
     *
     * @param key the key of the entry to be hashed
     * @return the compressed hash
     */
    private int hashHelper(K key) {
        return (key.hashCode() < 0) ? table.length % key.hashCode()
            : key.hashCode() % table.length;
    }

    /**
     * private helper method to reduce clutter when getting hashCode for
     * resize method
     *
     * @param key the old key from old backing array
     * @param length the new length parameter equal to legal backing array size
     * @return the compressed hash.
     */
    private int hashHelperResize(K key, int length) {
        return (key.hashCode() < 0) ? length % hashCode()
            : key.hashCode() % length;
    }


    /**
     * Resizes the backing table to the specified length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index, and
     * iterate over each chain from front to back. Add entries to the new table
     * in the order in which they are traversed.
     * <p>
     * Remember, you cannot just simply copy the entries over to the new array.
     * You will have to rehash all of the entries and add them to the new index
     * of the new table. Feel free to create new MapEntry objects to use when
     * adding to the new table to avoid pointer dependency issues between the
     * new and old tables.
     * <p>
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates. This matters especially for external chaining since it can
     * cause the performance of resizing to go from linear to quadratic time.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     *                                  the number of items in the hash map.
     */
    public void resizeBackingTable(int length) {
        if (length >= size() || length == (table.length * 2 + 1)) {
            MapEntry<K, V>[] buffer = new MapEntry[length];
            for (MapEntry<K, V> item : table) {
                MapEntry<K, V> holder = item;
                while (holder != null) {
                    int index = hashHelperResize(holder.getKey(), length);
                    if (buffer[index] != null) {
                        MapEntry<K, V> nextBuffer = buffer[index];
                        buffer[index] = new MapEntry<K, V>(holder.getKey(),
                            holder.getValue());
                        buffer[index].setNext(nextBuffer);

                    } else {
                        buffer[index] = new MapEntry<K, V>(holder.getKey(),
                            holder.getValue());
                    }
                    holder = holder.getNext();
                }

            }
            table = buffer;
        } else {
            if (length < 0) {
                throw new IllegalArgumentException("length is non-positive!");
            } else {
                throw new IllegalArgumentException("length is less than "
                    + "current number of items");
            }
        }
    }


    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws IllegalArgumentException         if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is Null");
        } else {
            if (!containsKey(key)) {
                throw new java.util.NoSuchElementException("key does not "
                    + "exist in map");
            } else {
                MapEntry<K, V> cursor = table[hashHelper(key)];
                while (cursor.getNext() != null) {
                    if (cursor.getKey().equals(key)) {
                        V value = cursor.getValue();
                        table[hashHelper(key)] = cursor.getNext();
                        size--;
                        return value;
                    } else if (cursor.getNext().getKey().equals(key)) {
                        V value = cursor.getNext().getValue();
                        cursor.setNext(cursor.getNext().getNext());
                        size--;
                        return value;
                    } else {
                        cursor = cursor.getNext();
                    }
                }
                V value = cursor.getValue();
                table[hashHelper(key)] = null;
                size--;
                return value;
            }
        }
    }



    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @return the value associated with the given key
     * @throws IllegalArgumentException         if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        } else {
            MapEntry<K, V> cursor = table[hashHelper(key)];
            if (cursor == null) {
                throw new java.util.NoSuchElementException("Key is not in map");
            } else {
                while (cursor != null) {
                    if (cursor.getKey().equals(key)) {
                        return cursor.getValue();
                    } else {
                        cursor = cursor.getNext();
                    }
                }
                throw new java.util.NoSuchElementException("Key is not in map");
            }
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @return whether or not the key is in the map
     * @throws IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key provided is null");
        } else {
            try {
                get(key);
                return true;
            } catch (java.util.NoSuchElementException e) {
                return false;
            }
        }
    }

    /**
     * Returns a Set view of the keys contained in this map. The Set view is
     * used instead of a List view because keys are unique in a HashMap, which
     * is a property that elements of Sets also share.
     * <p>
     * Use java.util.HashSet.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> output = new HashSet<K>();
        for (MapEntry<K, V> item : table) {
            MapEntry<K, V> holder = item;
            while (holder != null) {
                int index = hashHelperResize(holder.getKey(), table.length);
                if (table[index] != null) {
                    output.add(holder.getKey());
                } else {
                    output.add(holder.getKey());
                }
                holder = holder.getNext();
            }
        }
        return output;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index, and
     * iterate over each chain from front to back. Add entries to the List in
     * the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> output = new ArrayList<V>();
        for (MapEntry<K, V> item : table) {
            MapEntry<K, V> holder = item;
            while (holder != null) {
                int index = hashHelperResize(holder.getKey(), table.length);
                if (table[index] != null) {
                    output.add(holder.getValue());
                } else {
                    output.add(holder.getValue());
                }
                holder = holder.getNext();
            }
        }
        return output;
    }

    /**
     * Clears the table and resets it to a new table of length INITIAL_CAPACITY.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the size of the HashMap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing table of the HashMap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing table of the HashMap
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}