package com.ufl.aoa;

import java.util.ArrayList;
import java.util.Random;

public class RandomConnectedGraphGenerator {

    private Graph graph;
    private Random random = new Random();
    // Set a maximum limit to the vertices
    private int maxVerticesLimit;
    private int maxWeightLimit;

    public RandomConnectedGraphGenerator(int maxVerticesLimit, int maxWeightLimit) {
        this.maxVerticesLimit = maxVerticesLimit;
        this.maxWeightLimit = maxWeightLimit;
        _getGraph();
    }

    // Creating the constructor
    public RandomConnectedGraphGenerator() {
        this(500, 50);
    }

    private void _getGraph() {
        graph = new Graph();
        this.graph.vertices = random.nextInt(maxVerticesLimit - 10 + 1) + 10;
        System.out.println("Number of vertices: " + graph.vertices);

        int maxEdges = graph.vertices + 8;
        this.graph.edges = random.nextInt(maxEdges - graph.vertices + 1) + graph.vertices;
        System.out.println("Number of edges: " + graph.edges);

        // Creating an adjacency list
        // representation for the random graph
        graph.adjacencyList = new ArrayList<>(graph.vertices);
        for (int i = 0; i < graph.vertices; i++)
            graph.adjacencyList.add(new ArrayList<>());

        for (int i = 0; i < graph.vertices; i++) {
            //to make sure graph is connected
            int w = random.nextInt(graph.vertices);
            // Check if there is already an edge between v
            // and w
            if ((i == w) || graph.adjacencyList.get(i).contains(new Edge(i, w)) ||
                    graph.adjacencyList.get(w).contains(new Edge(w, i))) {
                // Reduce the value of i
                // so that again v and w can be chosen
                // for the same edge count
                i = i - 1;
                continue;
            }
            int weight = random.nextInt(maxWeightLimit) + 1;
            graph.addEdge(i, w, weight);
        }
        int diff = graph.edges - graph.vertices;
        // A for loop to randomly generate edges
        for (int i = 0; i < diff; i++) {
            // Randomly select two vertices to
            // create an edge between them
            int v = random.nextInt(graph.vertices);
            int w = random.nextInt(graph.vertices);
            // Check if there is already an edge between v
            // and w
            if ((v == w) || graph.adjacencyList.get(v).contains(new Edge(i, w)) || graph.adjacencyList.get(w).contains(new Edge(w, i))) {
                // Reduce the value of i
                // so that again v and w can be chosen
                // for the same edge count
                i = i - 1;
                continue;
            }
            // Add an edge between them if
            // not previously created
            int weight = random.nextInt(maxWeightLimit) + 1;
            graph.addEdge(v, w, weight);
        }
    }

    public void printRandomGraph() {
        graph.printGraph();
    }

    public Graph getGraph() {
        return graph;
    }

    public static void main(String[] args) {
        RandomConnectedGraphGenerator randomGraphGenerator = new RandomConnectedGraphGenerator();
        randomGraphGenerator.printRandomGraph();
        System.out.println(randomGraphGenerator.getGraph().isConnected());

        Graph graph2 = new Graph(7, 6);
        graph2.addEdge(0, 1, 10);
        graph2.addEdge(0, 2, 12);
        graph2.addEdge(1, 4, 5);
        graph2.addEdge(1, 6, 6);
        graph2.addEdge(5, 4, 55);
        graph2.addEdge(6, 5, 52);
        graph2.addEdge(6, 4, 14);
        graph2.addEdge(5, 3, 27);
        graph2.printGraph();
        System.out.println(graph2.isConnected());

        graph2.removeEdge(1, 4);
        graph2.removeEdge(5, 3);
        graph2.printGraph();
        System.out.println(graph2.isConnected());
    }
}
