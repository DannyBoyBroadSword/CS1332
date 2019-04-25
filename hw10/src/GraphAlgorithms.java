import java.util.*;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Andrew Boughan Hennessy
 * @userid ahennessy6
 * @GTID 903309743
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        List<Vertex<T>> visited = new ArrayList<>();
        Queue<Vertex<T>> q = new LinkedList<>();
        if (start == null) {
            throw new IllegalArgumentException("start vertex is null "
                + "therefore cannot perform BFS");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null "
                + "therefore cannot perform BFS");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex does "
                + "not exist in graph");
        }
        visited.add(start);
        q.add(start);
        while (!q.isEmpty()) {
            Vertex<T> pointer = q.poll();
            for (VertexDistance<T> vertex: adjList.get(pointer)) {
                if (!visited.contains(vertex.getVertex())) {
                    visited.add(vertex.getVertex());
                    q.add(vertex.getVertex());
                }
            }
        }
        return visited;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        List<Vertex<T>> visited = new ArrayList<>();
        if (start == null) {
            throw new IllegalArgumentException("start vertex is null "
                + "therefore cannot perform DFS");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null "
                + "therefore cannot perform DFS");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex does "
                + "not exist in graph");
        }
        return dfsHelper(graph, start, visited, adjList);
    }

    /**
     * private helper method to complete DFS.
     *
     * @param graph to be searched
     * @param start vertex to start search at (recursive pointer to adjVertices)
     * @param visited a list of previosly visited vertices.
     * @param adjList list of vertices adjacent to current vertex
     * @param <T> generic type parameter.
     * @return a list of visited by DFS vertices
     */
    private static <T> List<Vertex<T>> dfsHelper(Graph<T> graph,
                                                 Vertex<T> start,
                                           List<Vertex<T>> visited,
                                                 Map<Vertex<T>,
                                                     List<VertexDistance<T>>>
                                                     adjList) {
        if (visited.contains(start)) {
            return visited;
        }
        visited.add(start);
        for (VertexDistance<T> vertex: adjList.get(start)) {
            if (!visited.contains(vertex.getVertex())) {
                dfsHelper(graph, vertex.getVertex(), visited, adjList);
            }
        }
        return visited;
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>>  pq = new PriorityQueue<>();
        if (start == null) {
            throw new IllegalArgumentException("start vertex is null "
                + "therefore cannot perform DFS");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null "
                + "therefore cannot perform DFS");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex does "
                + "not exist in graph");
        }

        for (Vertex<T> vertex : graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        pq.add(new VertexDistance<>(start, 0));
        while (visited.size() < graph.getVertices().size() && !pq.isEmpty()) {
            VertexDistance<T> pqV = pq.poll();
            Vertex<T> vertex = pqV.getVertex();
            int distance = pqV.getDistance();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                distanceMap.put(vertex, distance);
                for (VertexDistance<T> vertexDistance
                    : graph.getAdjList().get(vertex)) {
                    if (!visited.contains(vertexDistance.getVertex())) {
                        pq.add(new VertexDistance<>(vertexDistance.getVertex(),
                            vertexDistance.getDistance() + distance));
                    }
                }
            }
        }

        return distanceMap;
    }


    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start vertex is null "
                + "therefore cannot perform DFS");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null "
                + "therefore cannot perform DFS");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex does "
                + "not exist in graph");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> edgeSet = new HashSet<>();
        PriorityQueue<Edge<T>>  pq = new PriorityQueue<>();
        for (VertexDistance<T> vertexD: graph.getAdjList().get(start)) {
            pq.add(new Edge<>(start, vertexD.getVertex(),
                vertexD.getDistance()));
        }
        visited.add(start);
        while (!pq.isEmpty()) {
            Edge<T> edge = pq.poll();
            if (!visited.contains(edge.getV())) {
                edgeSet.add(edge);
                edgeSet.add(new Edge<>(edge.getV(), edge.getU(),
                    edge.getWeight()));
                visited.add(edge.getV());
                for (VertexDistance<T> vertexDistance
                    : graph.getAdjList().get(edge.getV())) {
                    if (!visited.contains(vertexDistance.getVertex())) {
                        pq.add(new Edge<T>(edge.getV(),
                            vertexDistance.getVertex(),
                            vertexDistance.getDistance()));
                    }
                }

            }
        }
        if (visited.size() < graph.getVertices().size()) {
            return null;
        }
        return edgeSet;

    }
}