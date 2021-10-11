import java.util.*;

public class RandomGraphGenerator {

    private Graph graph;

    public Graph getGraph() {
        return graph;
    }

    // Set a maximum limit to the vertices
    final int MAX_LIMIT = 5000;
    final int MAX_WEIGHT = 20;

    Random random = new Random();

    // Creating the constructor
    public Graph generateRandomGraph(int vertices) {
        graph = new Graph();
        this.graph.vertices = vertices;
//        this.graph.vertices = random.nextInt(MAX_LIMIT - 1) + 1;

        int maxEdges = (graph.vertices * (graph.vertices - 1) / 2);
        if(maxEdges!=0)
            this.graph.edges = random.nextInt(maxEdges);
        else
            this.graph.edges = 0;

        // Creating an adjacency list
        graph.adjacencyList = new ArrayList<>(graph.vertices);
        for (int i = 0; i < graph.vertices; i++)
            graph.adjacencyList.add(new ArrayList<>());

        // A for loop to randomly generate edges
        for (int i = 0; i < graph.edges; i++) {
            // Randomly select two vertices to create an edge between them
            int v = random.nextInt(graph.vertices);
            int w = random.nextInt(graph.vertices);

            // Check if there is already an edge between v and w
            if ((v == w) || graph.adjacencyList.get(v).contains(new Edge(v, w))) {
                i = i - 1;
                continue;
            }

            // Add an edge between them if not previously created
            int weight = random.nextInt(MAX_WEIGHT) + 1;
            graph.addEdge(v, w, weight);
        }
        return graph;
    }
}
