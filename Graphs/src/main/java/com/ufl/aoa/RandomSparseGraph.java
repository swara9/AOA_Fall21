package com.ufl.aoa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSparseGraph {


    private int vertices;
    private int edges;

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    // Set a maximum limit to the vertices
    final int MAX_LIMIT = 20;

    // A Random instance to generate random values
    Random random = new Random();
    // An adjacency list to represent a graph
    public List<List<Integer>> adjacencyList;

    public RandomSparseGraph(int vertices, int edges){
        this.vertices = vertices;
        this.edges = edges;

        // Creating an adjacency list
        // representation for the random graph
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++)
            adjacencyList.add(new ArrayList<>());
    }

    // Creating the constructor
    public RandomSparseGraph()
    {
        // Set a maximum limit for the
        // number of vertices say 20
        this.vertices = random.nextInt(MAX_LIMIT) + 1;

        // compute the maximum possible number of edges
        // and randomly choose the number of edges less than
        // or equal to the maximum number of possible edges
        this.edges
                = random.nextInt(computeMaxEdges(vertices)) + 1;

        // Creating an adjacency list
        // representation for the random graph
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++)
            adjacencyList.add(new ArrayList<>());

        // A for loop to randomly generate edges
        for (int i = 0; i < edges; i++) {
            // Randomly select two vertices to
            // create an edge between them
            int v = random.nextInt(vertices);
            int w = random.nextInt(vertices);

            // Check if there is already an edge between v
            // and w
            if ((v == w)
                    || adjacencyList.get(v).contains(w)) {
                // Reduce the value of i
                // so that again v and w can be chosen
                // for the same edge count
                i = i - 1;
                continue;
            }

            // Add an edge between them if
            // not previously created
            addEdge(v, w);
        }
        printGraph();
    }

    // Method to compute the maximum number of
    // possible edges for a given number of vertices
//    int computeMaxEdges(int numOfVertices)
//    {
//        // As it is a directed graph
//        // So, for a given number of vertices
//        // there can be at-most v*(v-1) number of edges
//        return numOfVertices + 8;
//    }
    int computeMaxEdges(int numOfVertices)
    {
        // As it is a directed graph
        // So, for a given number of vertices
        // there can be at-most v*(v-1) number of edges
        return numOfVertices * (numOfVertices - 1);
    }
    // Method to add edges between given vertices
    public void addEdge(int v, int w)
    {
        // Add w to v's adjacency list
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);

    }

    public void printGraph(){
        // Print the graph
        System.out.println("\nThe generated random graph :");
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print(i + " -> { ");

            List<Integer> list
                    = adjacencyList.get(i);

            if (list.isEmpty())
                System.out.print(" No adjacent vertices ");
            else {
                int size = list.size();
                for (int j = 0; j < size; j++) {

                    System.out.print(list.get(j));
                    if (j < size - 1)
                        System.out.print(" , ");
                }
            }
            System.out.println("}");
        }
    }

    public static void main(String[] args)
    {
        // Create a GFGRandomGraph object
        RandomSparseGraph randomGraph = new RandomSparseGraph();

        // Print the graph
        randomGraph.printGraph();
    }
}

