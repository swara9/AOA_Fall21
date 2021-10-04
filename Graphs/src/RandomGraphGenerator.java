import java.util.*;

public class RandomGraphGenerator {

    private Graph graph;

    public Graph getGraph() {
        return graph;
    }

    // Set a maximum limit to the vertices
    final int MAX_LIMIT = 20;
    final int MAX_WEIGHT = 20;

    Random random = new Random();

    // Creating the constructor
    public Graph generateRandomGraph() {
        graph = new Graph();
        this.graph.vertices = random.nextInt(MAX_LIMIT - 1) + 1;
        System.out.println("Number of vertices: " + graph.vertices);
        int maxEdges = (graph.vertices * (graph.vertices - 1) / 2);

        this.graph.edges = random.nextInt(maxEdges - graph.vertices - 1) + graph.vertices - 1;
        System.out.println("Number of edges: " + graph.edges);

        // Creating an adjacency list
        graph.adjacencyList = new ArrayList<>(graph.vertices);
        for (int i = 0; i < graph.vertices; i++)
            graph.adjacencyList.add(new ArrayList<>());

        // A for loop to randomly generate edges
        for (int i = 0; i < graph.edges; i++) {
            // Randomly select two vertices to
            // create an edge between them
            int v = random.nextInt(graph.vertices);
            int w = random.nextInt(graph.vertices);

            // Check if there is already an edge between v
            // and w
            if ((v == w) || graph.adjacencyList.get(v).contains(new Edge(i, w)) || graph.adjacencyList.get(w).contains(new Edge(w, i))) {
                i = i - 1;
                continue;
            }

            // Add an edge between them if
            // not previously created
            int weight = random.nextInt(MAX_WEIGHT) + 1;
            graph.addEdge(v, w, weight);
        }
        return graph;

    }

    public void printRandomGraph() {
        graph.printGraph();
    }


    public static void main(String[] args) {
        RandomGraphGenerator randomGraphGenerator = new RandomGraphGenerator();
        randomGraphGenerator.generateRandomGraph().printGraph();
        for (int i = 0; i < 10; i++) {
            System.out.println(randomGraphGenerator.generateRandomGraph().isConnected());
        }


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
