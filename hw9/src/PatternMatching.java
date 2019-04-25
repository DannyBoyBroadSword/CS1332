import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Andrew Boughan Hennessy
 * @userid ahennessy6
 * @GTID 903309743
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null t"
                + "herefore KMP cannot be executed");
        }
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is of length 0"
                + " therefore KMP cannot be executed");
        }
        if (text == null) {
            throw new IllegalArgumentException("text is null"
                + " therefore KMP cannot be executed");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null"
                + " therefore KMP cannot be executed");
        }
        if (pattern.length() > text.length()) {
            List<Integer> output = new ArrayList<>();
            return output;
        }
        int[] failureTable = buildFailureTable(pattern, comparator);
        List<Integer> output = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.
                compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    output.add(i);
                }
                int nextAlignment = failureTable[j - 1];
                i = i + j - nextAlignment;
                j = nextAlignment;
            }
        }
        return output;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Character Sequence "
                + "pattern null therfore cannot build failure table");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator null "
                + "therefore cannot build failure table.");
        }
        if (pattern.length() == 0) {
            int[] output = new int[0];
            return output;
        }
        int[] output = new int[pattern.length()];
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                output[j] = i + 1;
                i++;
                j++;
            } else {
                if (i != 0) {
                    i = output[i - 1];
                } else if (i == 0) {
                    output[j] = 0;
                    j++;
                }
            }
        }
        return output;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null t"
                + "herefore BoyerMoore cannot be executed");
        }
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is of length 0"
                + " therefore BoyerMoore cannot be executed");
        }
        if (text == null) {
            throw new IllegalArgumentException("text is null"
                + " therefore BoyerMoore cannot be executed");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null"
                + " therefore BoyerMoore cannot be executed");
        }
        if (pattern.length() > text.length()) {
            List<Integer> output = new ArrayList<>();
            return output;
        }
        List<Integer> output = new ArrayList<>();
        Map<Character, Integer> lastTable = buildLastTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j),
                pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                output.add(i);
                i++;
            } else {
                int shiftedIndex = lastTable.getOrDefault(text.charAt(i + j),
                    -1);
                if (shiftedIndex == -1) {
                    i = i + (j - shiftedIndex);
                } else {
                    if (shiftedIndex < j) {
                        i = i + (j - shiftedIndex);
                    } else {
                        i++;
                    }
                }
            }
        }
        return output;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Character Sequence "
                + "pattern null therfore cannot build last occurence table");
        }
        if (pattern.length() == 0) {
            Map<Character, Integer> output = new HashMap<>();
            return output;
        }
        Map<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 101;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow. However, you will not need to handle this case.
     * You may assume there will be no overflow.
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 101 hash
     * = b * 101 ^ 3 + u * 101 ^ 2 + n * 101 ^ 1 + n * 101 ^ 0 = 98 * 101 ^ 3 +
     * 117 * 101 ^ 2 + 110 * 101 ^ 1 + 110 * 101 ^ 0 = 102174235
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 101
     * hash("unny") = (hash("bunn") - b * 101 ^ 3) * 101 + y =
     * (102174235 - 98 * 101 ^ 3) * 101 + 121 = 121678558
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow() for this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null t"
                + "herefore rabinKarp cannot be executed");
        }
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("pattern is of length 0"
                + " therefore rabinKarp cannot be executed");
        }
        if (text == null) {
            throw new IllegalArgumentException("text is null"
                + " therefore rabinKarp cannot be executed");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator is null"
                + " therefore rabinKarp cannot be executed");
        }
        if (pattern.length() > text.length()) {
            List<Integer> output = new ArrayList<>();
            return output;
        }
        List<Integer> output = new ArrayList<>();
        int base = 0;
        int patternHash = 0;
        int textHash = 0;

        for (int l = 0; l < pattern.length(); l++) {
            if (l == 0) {
                base = 1;
                int number = pattern.charAt(l);
                patternHash *= BASE;
                patternHash += number;

                number = text.charAt(l);
                textHash *= BASE;
                textHash += number;
            } else {
                int number = pattern.charAt(l);
                patternHash *= BASE;
                patternHash += number;

                number = text.charAt(l);
                textHash *= BASE;
                textHash += number;

                base *= BASE;
            }

        }
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternHash == textHash) {
                int j = 0;
                while (j < pattern.length() && comparator.compare(
                    text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    output.add(i);
                }
            }
            i++;
            if (i <= text.length() - pattern.length()) {
                textHash = rollingHashShift(text, pattern.length(), base,
                    textHash, i);
            }
        }

        return output;
    }


    /**
     * Private method to complete rabinKarp rollingHash
     *
     * @param text the CharSequence to be hashed
     * @param hashes of a character sequence how many should be hashed
     * @param base which was calculated with intital hashes of pattern and text
     *             length pattern.
     * @param existingHash calculated from rollingHash in beggining of program
     * @param offset where do we start hashing from aka what do we remove and
     *               then add from that removal index(offset) + pattern length.
     * @return the hash as an integer.
     */
    private static int rollingHashShift(CharSequence text, int hashes,
                                        int base, int existingHash,
                                        int offset) {
        int number = text.charAt(offset - 1);
        int numberOffset = text.charAt(hashes + offset - 1);
        return (existingHash - (number * base)) * BASE + numberOffset;

    }

}
