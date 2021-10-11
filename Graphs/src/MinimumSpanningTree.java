import java.util.ArrayList;
import java.util.List;

public class MinimumSpanningTree {
    List<Edge> allEdges;

    public MinimumSpanningTree() {
        allEdges = new ArrayList<>();
    }

    public static Graph generateMST(Graph graph) {
        graph.printGraph();
        CycleDetector cycleDetector = new CycleDetector();
        int noEdgesToRemove = graph.edges - graph.vertices + 1;

        for (int i = 0; i < noEdgesToRemove; i++) {
            cycleDetector.findCycle(graph);
            Edge edgeToRemove = cycleDetector.getEdgeWithHighestWeight();
            graph.removeEdge(edgeToRemove.node1, edgeToRemove.node2);
            System.out.println("Edge removed : "+edgeToRemove.toString());
        }
        System.out.println("\nMinimum Spanning Tree Generated:");
        graph.printGraph();
        System.out.println("Check if graph is spanning tree: "+isSpanningTree(graph));
        return graph;
    }

    public static boolean isSpanningTree(Graph graph) {
        CycleDetector cycleDetector = new CycleDetector();
        return (cycleDetector.findCycle(graph).isEmpty() && graph.isConnected());
    }

    public void runTestCase(){
        Graph graph = new Graph(8, 10);
        graph.addEdge(0,1, 9);
        graph.addEdge(0,5, 6);
        graph.addEdge(2,1, 5);
        graph.addEdge(2,3,3);
        graph.addEdge(2,5, 12);
        graph.addEdge(5,6,2);
        graph.addEdge(6,7, 8);
        graph.addEdge(2,4, 8);
        graph.addEdge(7,4, 15);
        graph.addEdge(3,7, 5);
        generateMST(graph);

    }

    /**
     * @param vertices
     */
    public void runMST(int vertices) {
        RandomConnectedGraphGenerator randomConnectedGraphGenerator = new RandomConnectedGraphGenerator();
        Graph graph = randomConnectedGraphGenerator.generateRandomConnectedGraph(vertices);
        generateMST(graph);
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 50000; i = i + 1000) {
            RandomConnectedGraphGenerator randomGraph = new RandomConnectedGraphGenerator();
            Graph g = randomGraph.generateRandomConnectedGraph(i);
            long start = System.nanoTime();
            generateMST(g);
            long timeLapsed = System.nanoTime() - start;
            System.out.println(g.vertices + ", " + g.edges + ", " + (g.vertices + g.edges) + ", " + timeLapsed);
        }
    }
}
