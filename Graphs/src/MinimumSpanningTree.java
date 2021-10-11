import java.util.ArrayList;
import java.util.List;

public class MinimumSpanningTree {
    List<Edge> allEdges;

    public MinimumSpanningTree(){
        allEdges = new ArrayList<>();
    }

    public static Graph generateMST(Graph graph) {
        CycleDetector cycleDetector = new CycleDetector();
        int noEdgesToRemove = graph.edges-graph.vertices+1;

        for(int i=0; i<noEdgesToRemove; i++){
            cycleDetector.findCycle(graph);
            Edge edgeToRemove = cycleDetector.getEdgeWithHighestWeight();
            graph.removeEdge(edgeToRemove.node1, edgeToRemove.node2);
        }

        return graph;
    }

    public static boolean isSpanningTree(Graph graph){
        CycleDetector cycleDetector = new CycleDetector();
        return (cycleDetector.findCycle(graph).isEmpty() && graph.isConnected());
    }

    public static void main(String[] args) {

        RandomConnectedGraphGenerator randomGraph = new RandomConnectedGraphGenerator();

        Graph g;
        long start;
        long timeLapsed;
        for(int i=0; i<=50000; i= i+1000){
            g= randomGraph.generateRandomConnectedGraph(i);

            start = System.nanoTime();
            generateMST(g);

            timeLapsed = System.nanoTime() - start;
            System.out.println(g.vertices+", "+g.edges+", "+(g.vertices+g.edges)+", "+ timeLapsed);
        }

    }
}
