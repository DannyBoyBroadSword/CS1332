import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;


import java.awt.Desktop;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APESortTests {
    private static int failures = 0;
    private static int succeded = 0;
    public static final int TIMEOUT = 200;
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            failures++;
        }

        @Override
        protected void succeeded(Description description) {
            succeded++;
        }
    };

    @Test(timeout = TIMEOUT)
    public void APEMergeTest() {
        String[] list1 = {"A", "B", "C", "D", "E", "F", "G"};
        NamedAPE[] inOrder = NamedAPE.makeApeList(list1);
        String[] list2 = {"D", "B", "C", "G", "A", "E", "F"};
        NamedAPE[] outorder = NamedAPE.makeApeList(list2);
        ComparatorPlus<APE> comp = NamedAPE.getIDComparator();
        Sorting.mergeSort(outorder, comp);
        compare(inOrder, outorder);
        assertTrue("Number of comparisons: " + comp.getCount() + ". Should be less than or equal to 12.",comp.getCount() <= 12 && comp.getCount() != 0);
        ComparatorPlus<APE> comp2 = NamedAPE.getIDComparator();
        Sorting.mergeSort(inOrder, comp2);
        compare(inOrder, outorder);
        assertTrue("Number of comparisons: " + comp2.getCount() + ". Should be less than or equal to 12.",comp2.getCount() <= 9 && comp2.getCount() != 0);


        String[] list3 = {"B", "U", "Y", "_", "A", "P", "E", "merch!"};
        NamedAPE[] outOrder2 = NamedAPE.makeApeList(list3);
        String[] list4 = {"A", "B", "E", "P",  "U", "Y",  "_", "merch!"};
        NamedAPE[] inOrder3 = NamedAPE.makeApeList(list4);
        ComparatorPlus<NamedAPE> comp3 = NamedAPE.getNameComparator();
        Sorting.mergeSort(outOrder2, comp3);
        compare(inOrder3, outOrder2);
        assertTrue("Number of comparisons: " + comp3.getCount() + ". Should be less than or equal to 16.", comp3.getCount() <= 16 && comp3.getCount() != 0);
        //add more comprehensive
    }

    @Test(timeout = TIMEOUT)
    public void APEQuickTest() {
        String[] list1 = {"A", "B", "C", "D", "E", "F", "G"};
        NamedAPE[] inOrder = NamedAPE.makeApeList(list1);
        String[] list2 = {"D", "B", "C", "G", "A", "E", "F"};
        NamedAPE[] outorder = NamedAPE.makeApeList(list2);
        ComparatorPlus<NamedAPE> comp = NamedAPE.getNameComparator();
        Random nm = new Random(12);
        Sorting.quickSort(outorder, comp, nm);
        compare(inOrder, outorder);
        assertTrue("Number of comparisons: " + comp.getCount() + ". Should be less than or equal to 15.", comp.getCount() <= 15 && comp.getCount() != 0);
        ComparatorPlus<NamedAPE> comp2 = NamedAPE.getNameComparator();
        Sorting.quickSort(inOrder, comp2, nm);
        compare(inOrder, outorder);
        assertTrue("Number of comparisons: " + comp2.getCount() + ". Should be less than or equal to 15.", comp2.getCount() <= 15 && comp2.getCount() != 0);


        String[] list3 = {"B", "U", "Y", "_", "A", "P", "E", "merch!"};
        NamedAPE[] outOrder2 = NamedAPE.makeApeList(list3);
        String[] list4 = {"A", "B", "E", "P",  "U", "Y",  "_", "merch!"};
        NamedAPE[] inOrder3 = NamedAPE.makeApeList(list4);
        Random rand2 = new Random(6420);
        ComparatorPlus<NamedAPE> comp3 = NamedAPE.getNameComparator();
        Sorting.quickSort(outOrder2, comp3, rand2);
        compare(inOrder3, outOrder2);
        assertTrue("Number of comparisons: " + comp3.getCount() + ". Should be less than or equal to 19.", comp3.getCount() <= 19 && comp3.getCount() != 0);


    }

    @Test(timeout = TIMEOUT)
    public void APEInsertionTest() {
        int[] list1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        APE[] inOrder = APE.makeApeList(list1);
        APE[] inOrder2 = APE.makeApeList(list1);
        ComparatorPlus<APE> comp = APE.getIDComparator();
        Sorting.insertionSort(inOrder, comp);
        compare(inOrder, inOrder2);
        assertTrue("Number of comparisons: " + comp.getCount() + ". Should be less than or equal to 8.",comp.getCount() <= 8 && comp.getCount() != 0);

        int[] order = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        APE[] outOrder = APE.makeApeList(order);
        ComparatorPlus<APE> comp2 = APE.getIDComparator();
        Sorting.insertionSort(outOrder, comp2);
        compare(outOrder, inOrder2);
        assertTrue("Number of comparisons: " + comp2.getCount() + ". Should be less than or equal to 36.", comp2.getCount() <= 36 && comp2.getCount() != 0);
        assertTrue("Correct number of comparisons", comp.getCount() <= list1.length * list1.length);

        int[] halforder = {5, 4, 3, 2, 1, 6, 7, 8, 9};
        APE[] halfOrder = APE.makeApeList(halforder);
        ComparatorPlus<APE> comp3 = APE.getIDComparator();
        Sorting.insertionSort(halfOrder, comp3);
        compare(halfOrder, inOrder2);
        assertTrue("Number of comparisons: " + comp2.getCount() + ". Should be less than or equal to  15.", comp3.getCount() <= 14 && comp3.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void APESelectionTest() {
        int[] list1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        APE[] inOrder = APE.makeApeList(list1);
        APE[] inOrder2 = APE.makeApeList(list1);
        ComparatorPlus<APE> comp = APE.getIDComparator();
        Sorting.selectionSort(inOrder, comp);
        compare(inOrder, inOrder2);
        assertTrue("Number of comparisons: " + comp.getCount() + ". Should be less than or equal to 36.",comp.getCount() <= 36 && comp.getCount() != 0);

        int[] order = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        APE[] outOrder = APE.makeApeList(order);
        ComparatorPlus<APE> comp2 = APE.getIDComparator();
        Sorting.selectionSort(outOrder, comp2);
        compare(outOrder, inOrder2);
        assertTrue("Number of comparisons: " + comp2.getCount() + ". Should be less than or equal to  36.", comp2.getCount() <= 36 && comp2.getCount() != 0);
        assertTrue("Correct number of comparisons", comp.getCount() <= list1.length * list1.length);

        int[] halforder = {5, 4, 3, 2, 1, 6, 7, 8, 9};
        APE[] halfOrder = APE.makeApeList(halforder);
        ComparatorPlus<APE> comp3 = APE.getIDComparator();
        Sorting.selectionSort(halfOrder, comp3);
        compare(halfOrder, inOrder2);
        assertTrue("Number of comparisons: " + comp2.getCount() + ". Should be less than or equal to  36.", comp3.getCount() <= 36 && comp3.getCount() != 0);
    }

    //@Test(timeout =TIMEOUT)
    @Test(timeout = TIMEOUT)
    public void APELSDTest() {
        int[] inOrder = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] inOrderClone = inOrder.clone();
        int[] order = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Sorting.lsdRadixSort(order);
        Sorting.lsdRadixSort(inOrder);
        assertArrayEquals("Arrays not equal:" + Arrays.toString(inOrderClone) + "\n" + Arrays.toString(order), inOrderClone, order);
        assertArrayEquals(inOrder, order);

        int[] notOrder = {1234567, 8, 9};
        int[] inOrder2 = {8, 9, 1234567};
        int[] order2 = {9, 8, 1234567};
        Sorting.lsdRadixSort(order2);
        Sorting.lsdRadixSort(notOrder);
        assertArrayEquals(inOrder2, order2);
        assertArrayEquals(notOrder, order2);

        int[] reorder3 = {9, 9, 9, 9, 9, 9, 9, 9, 1};
        int[] inorder3 = {1, 9, 9, 9, 9, 9, 9, 9, 9};
        Sorting.lsdRadixSort(reorder3);
        assertArrayEquals(reorder3, inorder3);


        int[] reorder4 = {9999, 7777, 99, -9999, Integer.MIN_VALUE, Integer.MAX_VALUE, 9, 999, 9, 9, 88, 8, -1};
        int[] inorder4 = {Integer.MIN_VALUE, -9999, -1, 8, 9, 9, 9, 88, 99, 999, 7777, 9999, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(reorder4);
        assertArrayEquals(reorder4, inorder4);
    }

    public void compare(Object[] arr1, Object[] arr2) {
        assertArrayEquals("Arrays not equal:" + Arrays.toString(arr1) + ""
            + "\n" + Arrays.toString(arr2), arr1, arr2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void ApeMergEexceptionArrayNull() {
        Sorting.mergeSort( null, NamedAPE.getNameComparator());
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeMergexceptionCompareNull() {
        Sorting.mergeSort( new Object[1], null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeQuickxceptionArrayNull() {
        Sorting.quickSort( null, NamedAPE.getNameComparator(), new Random());
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeQuickxceptionCompareNull() {
        Sorting.quickSort(new Object[1], null, new Random());
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeQuickxceptionRandNull() {
        Sorting.quickSort(new NamedAPE[1], NamedAPE.getNameComparator(), null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeSelectionexceptionArrayNull(){
        Sorting.selectionSort( null, NamedAPE.getNameComparator());
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeSelectionexceptionCompareNull(){
        Sorting.selectionSort( new Object[1], null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeInsertionexceptionArrayNull() {
        Sorting.insertionSort(null, NamedAPE.getIDComparator());
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeInsertxceptionCompareNull(){
        Sorting.selectionSort( new Object[1], null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ApeL$DexceptionArrayNull() {
        Sorting.lsdRadixSort(null);
    }

    /**
     * * Class for testing proper sorting.
     */
    private static class APE {
        private int GTID;

        enum Apespecies { GREATAPE, CHIMP, GORILLA, BONOBO, ORANGATAN };
        protected Apespecies apeType;

        /**
         * Create an APE.
         * @param id id number of TA
         */
        private APE(int id) {
            this.GTID = id;
            apeType = Apespecies.values()[(id*id) % Apespecies.values().length];
        }

        @Override
        public String toString() {
            return apeType.toString() + ":" + GTID;
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof APE)) {
                return false;
            }
            APE cc = (APE) o;
            return cc.GTID() == this.GTID();
        }



        /**
         *
         * @return id of ape
         */
        private int GTID() {
            return GTID;
        }

        /**
         * Create a comparator that compares the GTID's of the APES
         *
         * @return comparator that compares the GTID of the ape
         */
        public static APESortTests.ComparatorPlus<APESortTests.APE> getIDComparator() {
            return new APESortTests.ComparatorPlus<APESortTests.APE>() {
                @Override
                public int compare(APESortTests.APE ta1,
                                   APESortTests.APE ta2) {
                    incrementCount();
                    return ((Integer) ta1.GTID()).compareTo((Integer) ta2.GTID());
                }
            };
        }

        private static APE[] makeApeList(int[] arr) {
            APE[] returning = new APE[arr.length];
            int i = 0;
            for (int id : arr) {
                returning[i++] = new APE(id);
            }
            return returning;
        }
    }
    private static class NamedAPE extends APE {
        private String name;

        /**
         * Constructor for named ape
         * @param name of named ape
         */
        private NamedAPE(String name) {
            super(name.hashCode());
            this.name = name;
        }

        /**
         *
         * @return name of ape
         */
        private String name() {
            return name;
        }

        @Override
        public String toString() {
            return apeType.toString() + name;
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof APE)) {
                return false;
            }
            NamedAPE cc = (NamedAPE) o;
            return cc.name() == this.name();
        }

        private static NamedAPE[] makeApeList(String[] arr) {
            NamedAPE[] returning = new NamedAPE[arr.length];
            int i = 0;
            for (String name : arr) {
                returning[i++] = new NamedAPE(name);
            }
            return returning;
        }
        /**
         * Create a comparator that compares the names of the APEs
         *
         * @return comparator that compares the names of the APES
         */
        public static APESortTests.ComparatorPlus<APESortTests.NamedAPE> getNameComparator() {
            return new APESortTests.ComparatorPlus<APESortTests.NamedAPE>() {
                @Override
                public int compare(APESortTests.NamedAPE APE1,
                                   APESortTests.NamedAPE APE2) {
                    incrementCount();
                    return ((String) APE1.name()).compareTo((String) APE2.name());
                }
            };
        }
    }




    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }



    @AfterClass
    public static void testCompleted() throws Exception {

        if (failures <= 0 && succeded >= 15 /*test count*/) {
            try {
                Desktop.getDesktop().browse(
                    new URL("https://goo.gl/forms/cJ7FQGgarI7APy9n1").toURI());
                Desktop.getDesktop().browse(
                    new URL("https://ape-racer.herokuapp.com/").toURI());
            } catch (Exception e) { }
        } else {
            System.out.println("Ape game not unlocked."
                + "Please continue to work on your tests.");
        }
    }
}