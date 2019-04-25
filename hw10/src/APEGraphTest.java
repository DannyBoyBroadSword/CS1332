import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;


import java.awt.Desktop;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class  APEGraphTest {
    private static int  failures= 0;
    private static int succeded = 0;
    public static final int TIMEOUT = 200;

    Graph<Integer> APESampleGraph = setSampleGraph();
    Graph<Integer> APEEmpty = setSampleEmpty();
    Graph<Integer> APEOneVertex = setSampleOneVertex();

    private Graph<Integer> setSampleGraph() {
        Map<Vertex<Integer>, Integer> expected = new  HashMap<Vertex<Integer>, Integer>();
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        int[] puts = {0, 7, 9, 20, 20, 11, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int i = 1; i < 10; i++) {
            vertices.add(new Vertex<Integer>(i));
            expected.put(new Vertex<Integer>(i), puts[i - 1]);
        }


        Set<Edge<Integer>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(6), 14));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(3), 9));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(2), 7));

        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(3), 10));
        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(4), 15));

        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(6), 2));
        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(4), 11));
        edges.add(new Edge<>(new Vertex<Integer>(4), new Vertex<Integer>(5), 6));

        edges.add(new Edge<>(new Vertex<Integer>(6), new Vertex<Integer>(5), 9));

        return new Graph<Integer>(vertices, edges);
    }

    private Graph<Integer> setSampleEmpty() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        return new Graph<Integer>(vertices, edges);
    }

    private Graph<Integer> setSampleOneVertex() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(1));
        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        return new Graph<Integer>(vertices, edges);
    }

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

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestNullGraphBFS() {
        GraphAlgorithms.bfs(new Vertex<Integer>(5), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestNullStartBFS() {
        GraphAlgorithms.bfs(null, APESampleGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestNullGraphDFS() {
        GraphAlgorithms.dfs(new Vertex<Integer>(5), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestNullStartDFS() {
        GraphAlgorithms.dfs(null, APESampleGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestStartNotInBFS() {
        GraphAlgorithms.bfs(new Vertex<Integer>(10), APESampleGraph);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void exceptionTestStartNotInDFS() {
        GraphAlgorithms.dfs(new Vertex<Integer>(10), APESampleGraph);
    }

    @Test(timeout = TIMEOUT)
    public <T> void GenericBFS() {
        List<Vertex<Integer>> expected = new LinkedList<>();
        int[] puts = {1, 2, 6, 3, 4, 5};
        for (int item : puts) {
            expected.add(new Vertex<Integer>(item));
        }
        assertEquals(expected,
            GraphAlgorithms.bfs(new Vertex<Integer>(1),
                APESampleGraph));
    }

    @Test(timeout = TIMEOUT)
    public <T> void OneVertexBFS() {
        List<Vertex<Integer>> expected = new LinkedList<>();
        expected.add(new Vertex<>(1));
        assertEquals(expected,
            GraphAlgorithms.bfs(new Vertex<Integer>(1),
                APEOneVertex));
    }

    @Test(timeout = TIMEOUT)
    public <T> void GenericDFS() {
        List<Vertex<Integer>> expected = new LinkedList<>();
        int[] puts = {1, 2, 4, 5, 3, 6};
        for (int item : puts) {
            expected.add(new Vertex<Integer>(item));
        }
        assertEquals(expected,
            GraphAlgorithms.dfs(new Vertex<Integer>(1),
                APESampleGraph));
    }

    @Test(timeout = TIMEOUT)
    public <T> void OneVertexDFS() {
        List<Vertex<Integer>> expected = new LinkedList<>();
        expected.add(new Vertex<>(1));
        assertEquals(expected,
            GraphAlgorithms.dfs(new Vertex<Integer>(1),
                APEOneVertex));
    }

    @Test(timeout = TIMEOUT)
    public <T> void dijkstra() {
        Map<Vertex<Integer>, Integer> expected = new  HashMap<Vertex<Integer>, Integer>();
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        int[] puts = {0, 7, 9, 20, 20, 11};
        for (int i = 1; i < 7; i++) {
            vertices.add(new Vertex<Integer>(i));
            expected.put(new Vertex<Integer>(i), puts[i - 1]);
        }


        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(6), 14));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(3), 9));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(2), 7));

        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(3), 10));
        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(4), 15));

        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(6), 2));
        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(4), 11));
        edges.add(new Edge<>(new Vertex<Integer>(4), new Vertex<Integer>(5), 6));

        edges.add(new Edge<>(new Vertex<Integer>(6), new Vertex<Integer>(5), 9));

        assertEquals(expected,
            GraphAlgorithms.dijkstras(new Vertex<Integer>(1),
                new Graph<Integer>(vertices, edges)));
    }

    @Test(timeout = TIMEOUT)
    public <T> void dijkstra2() {
        Map<Vertex<Integer>, Integer> expected = new  HashMap<Vertex<Integer>, Integer>();
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        int[] puts = {0, 7, 9, 20, 20, 11, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int i = 1; i < 10; i++) {
            vertices.add(new Vertex<Integer>(i));
            expected.put(new Vertex<Integer>(i), puts[i - 1]);
        }


        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(6), 14));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(3), 9));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(2), 7));

        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(3), 10));
        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(4), 15));

        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(6), 2));
        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(4), 11));
        edges.add(new Edge<>(new Vertex<Integer>(4), new Vertex<Integer>(5), 6));

        edges.add(new Edge<>(new Vertex<Integer>(6), new Vertex<Integer>(5), 9));

        assertEquals(expected,
            GraphAlgorithms.dijkstras(new Vertex<Integer>(1),
                new Graph<Integer>(vertices, edges)));
    }

    @Test(timeout = TIMEOUT)
    public <T> void prims1() {
        Set<Edge<String>> expected = new  HashSet<Edge<String>>();
        Set<Vertex<String>> vertices = new HashSet<Vertex<String>>();
        int[] puts = {0, 7, 9, 20, 20, 11, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int i = 0; i < 9; i++) {
            vertices.add(new Vertex<String>("" + (char) (i + 65)));
        }

        expected.add(new Edge<String>(new Vertex<String>("A"), new Vertex<String>("B"), 4));
        expected.add(new Edge<String>(new Vertex<String>("B"), new Vertex<String>("A"), 4));


        expected.add(new Edge<String>(new Vertex<String>("A"), new Vertex<String>("H"), 8));
        expected.add(new Edge<String>(new Vertex<String>("H"), new Vertex<String>("A"), 8));


        expected.add(new Edge<String>(new Vertex<String>("H"), new Vertex<String>("G"), 1));
        expected.add(new Edge<String>(new Vertex<String>("G"), new Vertex<String>("H"), 1));

        expected.add(new Edge<String>(new Vertex<String>("F"), new Vertex<String>("G"), 2));
        expected.add(new Edge<String>(new Vertex<String>("G"), new Vertex<String>("F"), 2));

        expected.add(new Edge<String>(new Vertex<String>("F"), new Vertex<String>("C"), 4));
        expected.add(new Edge<String>(new Vertex<String>("C"), new Vertex<String>("F"), 4));

        expected.add(new Edge<String>(new Vertex<String>("I"), new Vertex<String>("C"), 2));
        expected.add(new Edge<String>(new Vertex<String>("C"), new Vertex<String>("I"), 2));

        expected.add(new Edge<String>(new Vertex<String>("C"), new Vertex<String>("D"), 7));
        expected.add(new Edge<String>(new Vertex<String>("D"), new Vertex<String>("C"), 7));


        expected.add(new Edge<String>(new Vertex<String>("D"), new Vertex<String>("E"), 9));
        expected.add(new Edge<String>(new Vertex<String>("E"), new Vertex<String>("D"), 9));


        Set<Edge<String>> edges = new HashSet<Edge<String>>();
        edges.add(new Edge<String>(new Vertex<String>("A"), new Vertex<String>("B"), 4));
        edges.add(new Edge<String>(new Vertex<String>("A"), new Vertex<String>("H"), 8));

        edges.add(new Edge<String>(new Vertex<String>("B"), new Vertex<String>("A"), 4));
        edges.add(new Edge<String>(new Vertex<String>("B"), new Vertex<String>("H"), 11));
        edges.add(new Edge<String>(new Vertex<String>("B"), new Vertex<String>("C"), 9));

        edges.add(new Edge<String>(new Vertex<String>("C"), new Vertex<String>("B"), 9));
        edges.add(new Edge<String>(new Vertex<String>("C"), new Vertex<String>("I"), 2));
        edges.add(new Edge<String>(new Vertex<String>("C"), new Vertex<String>("F"), 4));
        edges.add(new Edge<String>(new Vertex<String>("C"), new Vertex<String>("D"), 7));

        edges.add(new Edge<String>(new Vertex<String>("D"), new Vertex<String>("F"), 14));
        edges.add(new Edge<String>(new Vertex<String>("D"), new Vertex<String>("E"), 9));
        edges.add(new Edge<String>(new Vertex<String>("D"), new Vertex<String>("C"), 7));

        edges.add(new Edge<String>(new Vertex<String>("E"), new Vertex<String>("D"), 9));
        edges.add(new Edge<String>(new Vertex<String>("E"), new Vertex<String>("F"), 10));

        edges.add(new Edge<String>(new Vertex<String>("F"), new Vertex<String>("E"), 10));
        edges.add(new Edge<String>(new Vertex<String>("F"), new Vertex<String>("D"), 14));
        edges.add(new Edge<String>(new Vertex<String>("F"), new Vertex<String>("C"), 4));
        edges.add(new Edge<String>(new Vertex<String>("F"), new Vertex<String>("G"), 2));

        edges.add(new Edge<String>(new Vertex<String>("H"), new Vertex<String>("I"), 7));
        edges.add(new Edge<String>(new Vertex<String>("H"), new Vertex<String>("B"), 11));
        edges.add(new Edge<String>(new Vertex<String>("H"), new Vertex<String>("A"), 8));
        edges.add(new Edge<String>(new Vertex<String>("H"), new Vertex<String>("G"), 1));

        edges.add(new Edge<String>(new Vertex<String>("I"), new Vertex<String>("H"), 7));
        edges.add(new Edge<String>(new Vertex<String>("I"), new Vertex<String>("C"), 2));
        edges.add(new Edge<String>(new Vertex<String>("I"), new Vertex<String>("G"), 6));

        edges.add(new Edge<String>(new Vertex<String>("G"), new Vertex<String>("I"), 6));
        edges.add(new Edge<String>(new Vertex<String>("G"), new Vertex<String>("H"),    1));
        edges.add(new Edge<String>(new Vertex<String>("G"), new Vertex<String>("F"), 2));

        assertEquals(expected,
            GraphAlgorithms.prims(new Vertex<String>("H"),
                new Graph<String>(vertices, edges)));
    }

    @Test(timeout = TIMEOUT)
    public <T> void prims2() {
        //Map<Vertex<Integer>, Integer> expected = new  HashMap<Vertex<Integer>, Integer>();
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        int[] puts = {0, 7, 9, 20, 20, 11, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int i = 1; i < 10; i++) {
            vertices.add(new Vertex<Integer>(i));
        }


        Set<Edge<Integer>> edges = new HashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(6), 14));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(3), 9));
        edges.add(new Edge<>(new Vertex<Integer>(1), new Vertex<Integer>(2), 7));

        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(3), 10));
        edges.add(new Edge<>(new Vertex<Integer>(2), new Vertex<Integer>(4), 15));

        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(6), 2));
        edges.add(new Edge<>(new Vertex<Integer>(3), new Vertex<Integer>(4), 11));
        edges.add(new Edge<>(new Vertex<Integer>(4), new Vertex<Integer>(5), 6));

        edges.add(new Edge<>(new Vertex<Integer>(6), new Vertex<Integer>(5), 9));

        assertEquals(null,
            GraphAlgorithms.prims(new Vertex<Integer>(1),
                new Graph<Integer>(vertices, edges)));
    }
    //tests go here
    //
    //@Test(expected = NullPointerException.class)

    @AfterClass
    public static void testCompleted() throws Exception {

        if (failures <= 0 && succeded >= 14 /*test count*/) {
            try {
                Desktop.getDesktop().browse(
                    new URL("http://ape-unit.github.io/RamRanchGBA"
                        + "").toURI());
            } catch (Exception e) { }
        } else {
            System.out.println("Ape game not unlocked."
                + "Please continue to work on your tests.");
        }
    }
}