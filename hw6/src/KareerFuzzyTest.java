import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class KareerFuzzyTest {
    HashMap<Integer, Double> studentMap;
    java.util.HashMap javaMap;
    final int ITERATIONS = 100000;

    @Before
    public void setUp() throws Exception {
        studentMap = new HashMap<>(10);
        javaMap = new java.util.HashMap(10);
    }

    @Test
    public void fuzzyTest() {
        Random a = new Random();
        for (int i = 0; i < ITERATIONS; i++) {
            int next = a.nextInt(4);
            int key = a.nextInt(ITERATIONS/2);
            double value =  a.nextDouble();

            switch (next) {
            case 0:
                assertEquals(javaMap.put(key, value), studentMap.put(key, value));
                assertEquals(javaMap.get(key), studentMap.get(key));
                assertEquals(javaMap.size(), studentMap.size());
                assertEquals(javaMap.keySet(), studentMap.keySet());
                assertEquals(javaMap.containsKey(key), studentMap.containsKey(key));
//                    assertEquals(javaMap.values(), studentMap.values());
                break;
            case 1:
                Set<Integer> keys = javaMap.keySet();
                if (keys.size() > 0) {
                    Integer toRem = (Integer) keys.toArray()[a.nextInt(keys.size())];
                    studentMap.remove(toRem);
                    javaMap.remove(toRem);
                    assertFalse(studentMap.containsKey(toRem));
                }
                break;
            }
        }
    }
}