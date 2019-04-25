import java.util.LinkedList;
import java.util.Comparator;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Andrew Hennessy
 * @userid ahennessy6
 * @GTID 903309743
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr is null therefore "
                + "cannot be sorted");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null therefore "
                + "cannot be sorted");
        }
        int length = arr.length;
        for (int i = 1; i <= length - 1; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T buffer = arr[j - 1];
                T swap = arr[j];
                arr[j] = buffer;
                arr[j - 1] = swap;
                j--;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr is null therefore "
                + "cannot be sorted");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null therefore "
                + "cannot be sorted");
        }
        for (int i = 0; i <= arr.length - 1; i++) {
            T smallest = arr[i];
            int smallestIndex = i;
            for (int j = i + 1; j <= arr.length - 1; j++) {
                if (comparator.compare(arr[j], smallest) < 0) {
                    smallest = arr[j];
                    smallestIndex = j;
                }
            }
            T buffer = arr[i];
            T swap = arr[smallestIndex];
            arr[i] = swap;
            arr[smallestIndex] = buffer;
        }

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr is null therefore "
                + "cannot be sorted");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null therefore "
                + "cannot be sorted");
        }

        int length = arr.length;
        int midIndex = length / 2;

        T[] leftArray = (T[]) createArray(arr, 0, midIndex - 1);
        T[] rightArray = (T[]) createArray(arr, midIndex, length - 1);

        if (length < 2) {
            return;
        }

        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        int leftIndex = 0;
        int rightIndex = 0;
        int currentIndex = 0;





        while (leftIndex < midIndex && rightIndex < length - midIndex) {
            if (comparator.compare(leftArray[leftIndex],
                rightArray[rightIndex]) <= 0) {
                arr[currentIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                arr[currentIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            currentIndex++;
        }
        while (leftIndex < midIndex) {
            arr[currentIndex] = leftArray[leftIndex];
            leftIndex++;
            currentIndex++;
        }
        while (rightIndex < length - midIndex) {
            arr[currentIndex] = rightArray[rightIndex];
            rightIndex++;
            currentIndex++;
        }


    }

    /**
     * private helper method to create arrays given a set of indices.
     *
     * @param arr the array to be split
     * @param leftPtr the left most index from the arr that will be 0th elem in
     *                new arr.
     * @param rightPtr the right most index from the arr that will be 0th
     *                 elem in new arr.
     * @param <T> generic type parameter
     * @return array of objects that will be casted to generic type.
     */
    private static <T> T[] createArray(T[] arr, int leftPtr,
                                            int rightPtr) {
        T[] output = (T[]) new Object[rightPtr - leftPtr + 1];
        for (int i = 0; i <= rightPtr - leftPtr; i++) {
            //System.out.println(arr[i + leftPtr]);
            output[i] = arr[i + leftPtr];
        }
        return output;
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr is null therefore "
                + "cannot be sorted");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null therefore "
                + "cannot be sorted");
        }
        if (rand == null) {
            throw new IllegalArgumentException("rand is null therefore "
                + "cannot be sorted");
        }
        quickSort(arr, comparator, rand, 0, arr.length);

    }

    /**
     *
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand rand the Random object used to select pivots
     * @param left marker
     * @param right marker
     * @param <T> data type to sort
     */
    private static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                      Random rand, int left, int right) {
        if (right - left < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(right - left) + left;
        T pivot = arr[pivotIndex];
        T leftSwap = arr[left];
        T pivotSwap = arr[pivotIndex];
        arr[left] = pivotSwap;
        arr[pivotIndex] = leftSwap;
        int leftIndex = left + 1;
        int rightIndex = right - 1;
        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex && comparator.compare(
                arr[leftIndex], pivot) <= 0) {
                leftIndex++;
            }
            while (leftIndex < rightIndex
                && comparator.compare(arr[rightIndex], pivot) >= 0) {
                rightIndex--;
            }
            if (leftIndex <= rightIndex) {
                T leftBufferSwap = arr[leftIndex];
                T rightBufferSwap = arr[rightIndex];
                arr[leftIndex] = rightBufferSwap;
                arr[rightIndex] = leftBufferSwap;
                leftIndex++;
                rightIndex--;
            }
        }
        T pivotBufferSwap = arr[left];
        T pivotRightSwap = arr[rightIndex];
        arr[left] = pivotRightSwap;
        arr[rightIndex] = pivotBufferSwap;
        quickSort(arr, comparator, rand, left, rightIndex);
        quickSort(arr, comparator, rand, rightIndex + 1, right);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Arr is null therefore "
                + "cannot be sorted");
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[])
            new LinkedList[19];
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            int arrIdx = 0;
            for (int k = 0; k < buckets.length; k++) {
                if (buckets[k] != null) {
                    for (int num : buckets[k]) {
                        arr[arrIdx++] = num;
                    }
                    buckets[k].clear();
                }
            }
            div *= 10;
        }
    }
}
