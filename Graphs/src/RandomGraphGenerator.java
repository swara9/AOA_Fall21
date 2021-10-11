import java.util.*;

public class RandomGraphGenerator {

    // Set a maximum limit to the vertices
    final int MAX_LIMIT = 500000;
    final int MAX_WEIGHT = 20;
    Random random = new Random();
    private Graph graph;

    public Graph getRandomGraph(int vertices) {
        int maxEdges = Math.min((vertices * (vertices - 1) / 2),MAX_LIMIT);
        graph = new Graph(vertices,maxEdges);
        generateRandomEdges();
        return graph;
    }

    private void generateRandomEdges() {
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
    }
}