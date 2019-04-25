import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * homework 5 tests
 * renamed :)
 *
 * @author Joseph Azevedo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RenamedMaxHeapStudentTests {
    private static final int TIMEOUT = 5000;
    private MaxHeap<Integer> maxHeap;

    @Before
    public void setUp() {
        maxHeap = new MaxHeap<>();
    }

    private static final int CALLS = 400;
    private static final String FORMAT = "%0"
        + (int) (Math.ceil(Math.log10(CALLS)) + 1) + "d";
    private static final List<IntFunction<Comparable>> GENERATORS = Arrays.asList(
        i -> i, // ints
        i -> String.format(FORMAT, i), // zero padded strings
        i -> (i / 2d) // doubles
    );

    /**
     * scales O(n^3) with CALLS
     */
    @SuppressWarnings( {"unchecked", "ConstantConditions"})
    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        // default constructor
        assertEquals(MaxHeap.INITIAL_CAPACITY,
            maxHeap.getBackingArray().length);
        assertArrayEquals(new Integer[MaxHeap.INITIAL_CAPACITY],
            maxHeap.getBackingArray());
        assertEquals(0, maxHeap.size());

        // ArrayList constructor
        assertThrowableThrown("Null list instantiation did not throw IAE",
            () -> new MaxHeap<Integer>(null), IllegalArgumentException.class);
        for (IntFunction<Comparable> gen : GENERATORS) {
            for (int i = 0; i < CALLS; ++i) {
                class WatchingHeap<K extends Comparable<? super K>> extends MaxHeap<K> {
                    private boolean addCalled = false;

                    public WatchingHeap(ArrayList<K> l) {
                        super(l);
                    }

                    @Override
                    public void add(K item) {
                        addCalled = true;
                        super.add(item);
                    }
                }
                ArrayList<Comparable> l = (ArrayList<Comparable>) generateList(gen, i);
                Collections.shuffle(l);
                WatchingHeap heap = new WatchingHeap(l);

                // size initialization
                assertEquals(i, heap.size());
                // not using add
                assertFalse(heap.addCalled);
                // proper array initial size
                assertEquals((i * 2) + 1, heap.getBackingArray().length);
                // follows properties of heap impl
                assertHeapImpl(heap, l);
            }

            final int s = 10;
            ArrayList l2 = (ArrayList) generateList(gen, s);
            // null containing
            l2.set(s / 2, null);
            assertThrowableThrown(String.format("Null containing list "
                    + "%s did not throw IAE", l2.toString()),
                () -> new MaxHeap(l2), IllegalArgumentException.class);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddBehavior() {
        // null call to add
        assertThrowableThrown("Null add doesn't throw IAE",
            () -> maxHeap.add(null), IllegalArgumentException.class);
    }

    /**
     * scales O(n^2) with CALLS
     */
    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testAddCalls() {
        for (IntFunction<Comparable> gen : GENERATORS) {
            List<Comparable> l = generateList(gen, CALLS);
            List<Comparable> mirror = new ArrayList(CALLS);
            Collections.shuffle(l);
            MaxHeap heap = new MaxHeap();
            for (int i = 0; i < CALLS; ++i) {
                int oldSize = heap.size();
                boolean isGrowing = heap.getBackingArray().length == heap.size() + 1;
                int oldArrLength = heap.getBackingArray().length;
                heap.add(l.get(i));
                mirror.add(l.get(i));

                // size increment
                assertEquals(oldSize + 1, heap.size());
                // follows properties of heap impl
                assertHeapImpl(heap, mirror);
                // if growing, make sure backing array length doubled
                if (isGrowing) {
                    assertEquals(oldArrLength * 2,
                        heap.getBackingArray().length);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testReverseOrder() {
        for (IntFunction<Comparable> gen : GENERATORS) {
            for (int i = 0; i < CALLS; ++i) {
                List<Comparable> l = generateList(gen, CALLS);
                Collections.reverse(l);
                MaxHeap buildHeap = new MaxHeap((ArrayList) l);
                MaxHeap heap = new MaxHeap();
                for (Comparable comparable : l) {
                    heap.add(comparable);
                }
                List<Comparable> bhList = Arrays
                    .stream((Comparable[]) buildHeap.getBackingArray())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
                List<Comparable> heapList = Arrays
                    .stream((Comparable[]) heap.getBackingArray())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
                assertListsEqual(l, bhList, true);
                assertListsEqual(l, heapList, true);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveBehavior() {
        // remove from empty heap
        assertThrowableThrown("Empty heap remove doesn't throw NSEE",
            () -> maxHeap.remove(), NoSuchElementException.class);
    }

    /**
     * scales O(n^2) with CALLS
     * Depends on list constructor
     */
    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testRemoveCalls() {
        for (IntFunction<Comparable> gen : GENERATORS) {
            List<Comparable> l = generateList(gen, CALLS);
            Collections.shuffle(l);
            List<Comparable> mirror = new ArrayList(l);
            MaxHeap heap = new MaxHeap((ArrayList) l);
            for (int i = 0; i < CALLS; ++i) {
                int oldSize = heap.size();
                int oldArrLength = heap.getBackingArray().length;
                Object oldMax = Collections.max(
                    (List) (Arrays.stream(heap.getBackingArray())
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())));
                Comparable removed = heap.remove();
                mirror.remove(removed);

                // make sure removed max
                assertSame(oldMax, removed);
                // size decrement
                assertEquals(oldSize - 1, heap.size());
                // array size unchanged
                assertEquals(oldArrLength, heap.getBackingArray().length);
                // follows properties of heap impl
                assertHeapImpl(heap, mirror);
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMiscBehavior() {
        // null on empty heap
        assertNull(maxHeap.getMax());
        // empty returns true if empty, false otherwise
        assertTrue(maxHeap.isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test(timeout = TIMEOUT)
    public void testMiscCalls() {
        for (IntFunction<Comparable> gen : GENERATORS) {
            List<Comparable> l = generateList(gen, CALLS);
            Collections.shuffle(l);
            MaxHeap heap = new MaxHeap();
            for (int i = 0; i < CALLS; ++i) {
                heap.add(l.get(i));
                // max is 1st element
                assertSame(heap.getBackingArray()[1], heap.getMax());
                // not empty after add
                assertFalse(heap.isEmpty());
            }
            Object[] oldArr = heap.getBackingArray();
            heap.clear();
            // size reset to 0
            assertEquals(0, heap.size());
            // array reference changed (new one allocated)
            assertNotSame(oldArr, heap.getBackingArray());
            // array size of initial size
            assertEquals(MaxHeap.INITIAL_CAPACITY, heap.getBackingArray().length);
        }
    }

    @SuppressWarnings("unchecked")
    public <K extends Comparable<? super K>> void assertHeapImpl(MaxHeap<K> heap, List<K> l) {
        int i = heap.size();
        // same nonnull contents as source list
        assertCollectionsEqual(l, (List) Arrays.stream(heap.getBackingArray())
            .filter(Objects::nonNull)
            .collect(Collectors.toList()), false);
        // first element null
        assertNull(heap.getBackingArray()[0]);
        if (i > 0) {
            Object max = Collections.max(l);
            // max at proper position
            assertSame(max, heap.getBackingArray()[1]);
            // other elements nonnull
            for (int j = 1; j <= i; ++j) {
                assertNotNull(heap.getBackingArray()[j]);
            }
            // overflow null
            for (int j = i + 1; j < heap.getBackingArray().length; ++j) {
                assertNull(heap.getBackingArray()[j]);
            }
            // shape rules followed
            assertHeapOrder(heap);
        }
    }

    // ---------------
    // utility methods
    // ---------------

    @SuppressWarnings("unchecked")
    public <K extends Comparable<? super K>> void assertHeapOrder(MaxHeap<K> heap) {
        K[] arr = (K[]) heap.getBackingArray();
        for (int i = 1; i <= heap.size() / 2; ++i) { // root to last internal node
            K curr = arr[i];
            if (curr == null) {
                continue;
            }
            if (i * 2 >= arr.length) {
                continue;
            }
            K l = arr[i * 2];
            if (l != null && curr.compareTo(l) < 0) {
                fail(String.format("Heap: %n%s%nis not valid: left value %s is" +
                        " greater than its parent %s", toString(heap, Object::toString),
                    l.toString(), curr.toString()));
            }
            if (i * 2 + 1 >= arr.length) {
                continue;
            }
            K r = arr[i * 2 + 1];
            if (r != null && curr.compareTo(r) < 0) {
                fail(String.format("Heap: %n%s%nis not valid: right value %s is" +
                        " greater than its parent %s", toString(heap, Object::toString),
                    r.toString(), curr.toString()));
            }
        }
    }

    public <K> void assertListsEqual(List<K> a, List<K> b, boolean useOrder) {
        if (useOrder) {
            if (a.size() == b.size()) {
                if (a instanceof RandomAccess && b instanceof RandomAccess) {
                    for (int i = 0; i < a.size(); ++i) {
                        K eA = a.get(i);
                        K eB = b.get(i);
                        if (!Objects.equals(eA, eB)) {
                            fail(String.format(
                                "Element %s from list A =/= %s from list B",
                                String.valueOf(eA), String.valueOf(eB)));
                        }
                    }
                } else {
                    Iterator<K> iterA = a.iterator();
                    Iterator<K> iterB = b.iterator();
                    while (iterA.hasNext()) {
                        K eA = iterA.next();
                        K eB = iterB.next();
                        if (!Objects.equals(eA, eB)) {
                            fail(String.format(
                                "Element %s from list A =/= %s from list B",
                                String.valueOf(eA), String.valueOf(eB)));
                        }
                    }
                }
            } else {
                fail();
            }
        } else {
            assertCollectionsEqual(a, b, false);
        }
    }

    public <K> void assertCollectionsEqual(Collection<K> a, Collection<K> b,
                                           boolean ignoreDuplicates) {
        assertTrue((a.size() == b.size() || ignoreDuplicates)
            && (new HashSet<>(a)).equals(new HashSet<>(b)));
    }

    @FunctionalInterface
    public interface Procedure {
        void run();
    }

    public <T extends Throwable> void assertThrowableThrown(String message,
                                                            Procedure op, Class<T> c) {
        boolean caught = false;
        try {
            op.run();
        } catch (Throwable t) {
            if (t.getClass().equals(c)) {
                caught = true;
            }
        } finally {
            assertTrue(message, caught);
        }
    }

    public static <K> List<K> generateList(IntFunction<K> generator,
                                           int size) {
        List<K> accumulator = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            accumulator.add(generator.apply(i));
        }
        return accumulator;
    }

    @SuppressWarnings("unchecked")
    public static <K extends Comparable<? super K>> String toString(
        MaxHeap<K> heap, Function<K, String> toString) {
        return toString(heap,
            h -> 31 - Integer.numberOfLeadingZeros(h.size() + 1), // log2
            h -> 1,
            (h, i) -> toString.apply((K) (h.getBackingArray()[i])),
            (h, i) -> (i << 1) > h.size() ? null : (i << 1),
            (h, i) -> ((i << 1) + 1) > h.size() ? null : ((i << 1) + 1));
    }

    /**
     * Prints a tree
     *
     * @param tree      tree
     * @param getHeight gets the height of the tree
     * @param getRoot   gets the root node of the tree
     * @param getValue  gets the String value that corresponds to the node
     * @param getLeft   gets the node on the left
     * @param getRight  gets the node on the right
     * @param <T>       the type of the Tree
     * @param <N>       the type of the tree's Nodes]
     * @author https://stackoverflow.com/users/2180189/mightypork
     * adapted from https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
     */
    public static <T, N> String toString(T tree,
                                         Function<T, Integer> getHeight,
                                         Function<T, N> getRoot,
                                         BiFunction<T, N, String> getValue,
                                         BiFunction<T, N, N> getLeft,
                                         BiFunction<T, N, N> getRight) {
        List<List<String>> levels = new ArrayList<>(getHeight.apply(tree) + 1);
        List<N> level = new LinkedList<>();
        List<N> next = new LinkedList<>();
        level.add(getRoot.apply(tree));
        int nn = 1;
        int widest = 0;
        int g = 1;
        // levelorder traversal
        while (nn != 0) {
            List<String> line = new ArrayList<>(g);
            g <<= 1;
            nn = 0;
            for (N n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = getValue.apply(tree, n);
                    line.add(aa);
                    if (aa.length() > widest) {
                        widest = aa.length();
                    }
                    next.add(getLeft.apply(tree, n));
                    next.add(getRight.apply(tree, n));
                    if (getLeft.apply(tree, n) != null) {
                        nn++;
                    }
                    if (getRight.apply(tree, n) != null) {
                        nn++;
                    }
                }
            }
            if (widest % 2 == 1) {
                widest++;
            }
            levels.add(line);
            List<N> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }
        StringBuilder[] lines = new StringBuilder[levels.size() * 2 - 1];
        int perpiece = levels.get(levels.size() - 1).size() * (widest + 4);
        for (int i = 0; i < levels.size(); i++) {
            List<String> line = levels.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;
            StringBuilder sb;
            if (i > 0) {
                sb = lines[i * 2 - 1] = new StringBuilder();
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            line.size();
                            if (line.get(j) != null) {
                                c = '└';
                            }
                        }
                    }
                    sb.append(c);
                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            sb.append(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            sb.append(j % 2 == 0 ? " " : "─");
                        }
                        sb.append(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            sb.append(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
            }
            sb = lines[i * 2] = new StringBuilder();
            // print line of numbers
            for (String f : line) {
                if (f == null) {
                    f = "";
                }
                float a = perpiece / 2f - f.length() / 2f;
                int gap1 = (int) Math.ceil(a);
                int gap2 = (int) Math.floor(a);

                // a number
                for (int k = 0; k < gap1; k++) {
                    sb.append(" ");
                }
                sb.append(f);
                for (int k = 0; k < gap2; k++) {
                    sb.append(" ");
                }
            }
            perpiece /= 2;
        }

        StringBuilder e = new StringBuilder();
        String nl = String.format("%n");
        for (int i = 0; i < lines.length; ++i) {
            e.append(lines[i]);
            if (i != lines.length - 1) {
                e.append(nl);
            }
        }
        return e.toString();
    }
}