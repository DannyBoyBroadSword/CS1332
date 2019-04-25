import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class LinkListStudentTestsJAZ {
    private SinglyLinkedList<String> list;

    public static final int TIMEOUT = 500;

    @Before
    public void setUp() {
        list = new SinglyLinkedList<>();
    }

    // ************************
    // Begin of jazevedo6 tests
    // ************************

    private static void addTestData(SinglyLinkedList<String> linkedList) {
        for (int i = 0; i < 300; ++i) {
            String str = Character.toString((char)('a' + (i % 26))) + i;
            linkedList.addToBack(str);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddExceptionBehavior() {
        addTestData(list);
        assertThrowableThrown(() -> list.addAtIndex(-1, "a0"),
            IndexOutOfBoundsException.class);
        assertThrowableThrown(() -> list.addAtIndex(list.size() + 3, "a0"),
            IndexOutOfBoundsException.class);
        assertNoThrowable(() -> list.addAtIndex(list.size() - 1, "a0"));
        assertNoThrowable(() -> list.addAtIndex(list.size(), "a0"));
        assertThrowableThrown(() -> list.addAtIndex(0, null),
            IllegalArgumentException.class);
        assertThrowableThrown(() -> list.addToFront(null),
            IllegalArgumentException.class);
        assertThrowableThrown(() -> list.addToBack(null),
            IllegalArgumentException.class);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveExceptionBehavior() {
        addTestData(list);
        assertThrowableThrown(() -> list.removeAtIndex(-1),
            IndexOutOfBoundsException.class);
        assertThrowableThrown(() -> list.removeAtIndex(list.size() + 3),
            IndexOutOfBoundsException.class);
        assertNoThrowable(() -> list.removeAtIndex(list.size() - 1));
        list.clear();
        assertNoThrowable(() -> list.removeFromBack());
        assertNoThrowable(() -> list.removeFromFront());
        assertNull(list.removeFromBack());
        assertNull(list.removeFromFront());
        assertThrowableThrown(() -> list.removeAtIndex(0),
            IndexOutOfBoundsException.class);
    }

    @Test(timeout = TIMEOUT)
    public void testGetExceptionBehavior() {
        addTestData(list);
        assertThrowableThrown(() -> list.get(-1),
            IndexOutOfBoundsException.class);
        assertThrowableThrown(() -> list.get(list.size()),
            IndexOutOfBoundsException.class);
        assertNoThrowable(() -> list.get(list.size() - 1));
    }

    @FunctionalInterface
    public interface Procedure { void run(); }

    public <T extends Throwable> void assertThrowableThrown(Procedure op,
                                                            Class<T> c) {
        boolean caught = false;
        try {
            op.run();
        } catch (Throwable t) {
            if (t.getClass().equals(c)) caught = true;
        } finally {
            assertTrue(caught);
        }
    }

    public void assertNoThrowable(Procedure op) {
        try {
            op.run();
        } catch (Throwable t) {
            fail();
        }
    }
}