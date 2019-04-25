import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Collections;

import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApePatternMatchTest {
    private String bMPattern;
    private String bMText;
    private String bMNoMatch;
    private List<Integer> bMAnswer;

    private CharacterComparator apeComp;
    private static final int TIMEOUT = 200;

    @Before
    public void setup() {
        bMPattern = "ape";
        bMText = "epapepappe";
        bMNoMatch = "eppapaepeaaepeppaepepapaea";
        bMAnswer = new ArrayList<>();

        apeComp = new CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable() {
        // bruh this better not cause too much trouble
        String pattern = "apeappaepepepappe";
        Map<Character, Integer> answer = new HashMap<>();
        answer.put('a', 13);
        answer.put('p', 15);
        answer.put('e', 16);
        assertEquals("returned map has incorrect values", answer, PatternMatching.buildLastTable(pattern));

        answer = new HashMap<>();
        pattern = "you are now breathing manually";
        answer.put('y', 29);
        answer.put('o', 9);
        answer.put('u', 25);
        answer.put('a', 26);
        answer.put('r', 13);
        answer.put('e', 14);
        answer.put('n', 24);
        answer.put('w', 10);
        answer.put('b', 12);
        answer.put('t', 16);
        answer.put('h', 17);
        answer.put('i', 18);
        answer.put('g', 20);
        answer.put('m', 22);
        answer.put('l', 28);
        answer.put(' ', 21);
        assertEquals("returned map has incorrect values", answer, PatternMatching.buildLastTable(pattern));
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMoore() {
        /*
            e p a p e p a p p e
                a p e             <- only match at index 2
         */
        bMAnswer.add(2);
        assertEquals("Incorrect value returned", bMAnswer,
            PatternMatching.boyerMoore(bMPattern, bMText, apeComp));

        /*
            e p a p e p a p p e
            a p e                <- fails after one comparison, shifts 2
                a p e            <- succeeds after 3 comparisons, shift 1
                  a p e          <- fails after 1 comparison, shift 1
                    a p e        <- fails after 1 comparison, shift 2
                        a p e    <- fails after 1 comparison, shift 1
                          a p e  <- fails after 3 comparisons, end process
                            10 total comparisons
         */
        assertTrue("incorrect number of comparisons used. Expected 10 got "
            + apeComp.getComparisonCount() + ".", 10 >= apeComp.getComparisonCount());
        // resetting comparison count
        apeComp = new CharacterComparator();

        /*
            e p p a p a e p e a a e p e p p a e p e p a p a e a <- no matches
         */
        bMAnswer.remove(0);
        assertEquals("Incorrect value returned", bMAnswer,
            PatternMatching.boyerMoore(bMPattern, bMNoMatch, apeComp));

        /*
            e p p a p a e p e a a e p e p p a e p e p a p a e a
            a p e                                                 <-fails after 1 comparison
              a p e                                               <-fails after 1 comparison
                  a p e                                           <-fails after 1 comparison
                      a p e                                       <-fails after 1 comparison
                        a p e                                     <-fails after 3 comparisons
                          a p e                                   <-fails after 1 comparison
                              a p e                               <-fails after 2 comparisons
                                a p e                             <-fails after 1 comparison
                                  a p e                           <-fails after 3 comparisons
                                    a p e                         <-fails after 1 comparison
                                      a p e                       <-fails after 1 comparison
                                        a p e                     <-fails after 1 comparison
                                            a p e                 <-fails after 1 comparison
                                              a p e               <-fails after 3 comparisons
                                                a p e             <-fails after 1 comparison
                                                  a p e           <-fails after 1 comparison
                                                      a p e       <-fails after 1 comparison
                                                          a p e   <-fails after 1 comparison
                            25 total comparisons
         */
        assertTrue("incorrect number of comparisons used", 25 >= apeComp.getComparisonCount());
        // resetting comparison count
        apeComp = new CharacterComparator();


        /*
            a e a p e p e a p e e
                a p e               <- match at index 2
                          a p e     <- match at index 7
         */

        bMAnswer.add(2);
        bMAnswer.add(7);

        assertEquals("Incorrect value returned", bMAnswer,
            PatternMatching.boyerMoore(bMPattern, "aeapepeapee", apeComp));

        /*
            a e a p e p e a p e e
            a p e                   <- fails after 1 comparison
                a p e               <- succeeds after 3 comparisons
                  a p e             <- fails after 1 comparison
                    a p e           <- fails after 3 comparisons
                      a p e         <- fails after 1 comparison
                          a p e     <- succeeds after 3 comparisons
                            a p e   <- fails after 2 comparisons
                            14 total comparisons
         */

        assertTrue("incorrect number of comparisons used", 14 >= apeComp.getComparisonCount());


    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable() {
        /*
         a p e p a p e a p e
         0 0 0 0 1 2 3 1 2 3
        */
        int[] answer = {0,0,0,0,1,2,3,1,2,3};
        assertArrayEquals("Incorrect table returned", answer,
            PatternMatching.buildFailureTable("apepapeape", new CharacterComparator()));
        /*
         now let's get crazy
         m i m i k e m i m i m
         0 0 1 2 0 0 1 2 3 4 3
        */
        int[] answer2 = {0,0,1,2,0,0,1,2,3,4,3};
        assertArrayEquals("Incorrect table returned", answer2,
            PatternMatching.buildFailureTable("mimikemimim", new CharacterComparator()));
        /*
         m m m m m i m m m m
         0 1 2 3 4 0 1 2 3 4
        */
        int[] answer3 = {0,1,2,3,4,0,1,2,3,4};
        assertArrayEquals("Incorrect table returned", answer3,
            PatternMatching.buildFailureTable("mmmmmimmmm", new CharacterComparator()));
    }

    @Test(timeout = TIMEOUT)
    public void testKMP() {
        /*
            An orangutan's lifespan is 35-40 years
            orangutan
             orangutan
              orangutan
               orangutan
                        orangutan
                         orangutan
                          orangutan
                           orangutan
                            orangutan
                             orangutan
                              orangutan
                               orangutan
                                orangutan
                                 orangutan
                                  orangutan
                                   orangutan
                                    orangutan
                                     orangutan
                                      orangutan
                                       orangutan
                                        orangutan
                                         orangutan
                   ok maybe this wasn't all that clean
            38 total comparisons (30 when comparing text and pattern, 8 when creating failure table)
         */
        String text = "An orangutan's lifespan is 35-40 years";
        String pattern = "orangutan";
        CharacterComparator myComp = new CharacterComparator();
        List<Integer> studentResult = PatternMatching.kmp(pattern, text, myComp);
        List<Integer> answer = new ArrayList<>();
        answer.add(3);
        assertEquals("incorrect list returned", answer, studentResult);
        assertEquals("incorrect number of comparisons", 38, myComp.getComparisonCount());

        /*
             c a b d a b c d a b a b c d
             a b c d                        <- fails after 1 comparison
               a b c d                      <- fails after 3 comparisons
                   a b c d                  <- fails after 1 comparison
                     a b c d                <- succeeds after 4 comparisons
                             a b c d        <- fails after 3 comparisons
                                 a b c d    <- succeeds after 4 comparisons
            19 total comparisons (16 when comparing text and pattern, 3 when creating failure table)
         */

        text = "cabdabcdababcd";
        pattern = "abcd";
        myComp = new CharacterComparator();
        studentResult = PatternMatching.kmp(pattern, text, myComp);
        answer = new ArrayList<>();
        answer.add(4);
        answer.add(10);
        assertEquals("incorrect list returned", answer, studentResult);
        assertEquals("incorrect number of comparisons", 19, myComp.getComparisonCount());

        /*
             b r u u h u r b r r b r u r h r u h b
             b r u h                                    <- fails after 4 comparisons
                   b r u h                              <- fails after 1 comparison
                     b r u h                            <- fails after 1 comparison
                       b r u h                          <- fails after 1 comparison
                         b r u h                        <- fails after 1 comparison
                           b r u h                      <- fails after 3 comparisons
                               b r u h                  <- fails after 1 comparison
                                 b r u h                <- fails after 4 comparisons
                                       b r u h          <- fails after 1 comparison
                                         b r u h        <- fails after 1 comparison
                                           b r u h      <- fails after 1 comparison
           22 total comparisons (19 when comparing text and pattern, 3 when creating failure table)
         */
        text = "bruuhurbrrbrurhruhb";
        pattern = "bruh";
        myComp = new CharacterComparator();
        studentResult = PatternMatching.kmp(pattern, text, myComp);
        answer = new ArrayList<>();
        assertEquals("incorrect list returned", answer, studentResult);
        assertEquals("incorrect number of comparisons", 22, myComp.getComparisonCount());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionKMPPattern1() {
        PatternMatching.kmp(null, "please",
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionKMPPattern2() {
        PatternMatching.kmp("", "give",
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionKMPText() {
        PatternMatching.kmp("me", null,
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionKMPComparator() {
        PatternMatching.kmp("an", "internship",
            null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionBuildFTPattern() {
        PatternMatching.buildFailureTable(null, new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionBuildFTComparator() {
        PatternMatching.buildFailureTable("ayyy", null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionBMPattern1() {
        PatternMatching.boyerMoore(null, "please",
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionBMPattern2() {
        PatternMatching.boyerMoore("", "give",
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionBMText() {
        PatternMatching.boyerMoore("me", null,
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionBMComparator() {
        PatternMatching.boyerMoore("an", "internship",
            null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionBuildLTPattern() {
        PatternMatching.buildLastTable(null);
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionRKPattern1() {
        PatternMatching.rabinKarp(null, "please",
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionRKPattern2() {
        PatternMatching.rabinKarp("", "give",
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionRKText() {
        PatternMatching.rabinKarp("me", null,
            new CharacterComparator());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionRKComparator() {
        PatternMatching.rabinKarp("an", "internship",
            null);
    }



    private static int  failures= 0;
    private static int succeded = 0;
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

    @AfterClass
    public static void testCompleted() throws Exception {

        if (failures <= 0 && succeded >= 19 /*test count*/) {
            try {
                System.out.println("Please play APE-Racer!");
                Desktop.getDesktop().browse(
                    new URL("https://ape-racer.herokuapp.com/").toURI());
            } catch (Exception e) { }
        } else {
            System.out.println("Ape game not unlocked."
                + "Please continue to work on your tests.");
        }
    }
}