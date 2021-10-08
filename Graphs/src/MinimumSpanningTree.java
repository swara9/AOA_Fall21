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
            Edge edgeToRemove = cycleDetector.getHighestWeightedEdgeInCycle();
            graph.removeEdge(edgeToRemove.node1, edgeToRemove.node2);
        }

        return graph;
    }

    public static void main(String[] args) {
//        Graph graph = new Graph(9,14);
//        graph.addEdge(0,1, 4);
//        graph.addEdge(0,7,8);
//        graph.addEdge(1,7, 11);
//        graph.addEdge(1,2,8);
//        graph.addEdge(2,3,7);
//        graph.addEdge(3,4,9);
//        graph.addEdge(4,5,10);
//        graph.addEdge(3,5, 14);
//        graph.addEdge(2,5,4);
//        graph.addEdge(5,6, 2);
//        graph.addEdge(6,8,6);
//        graph.addEdge(2,8,2);
//        graph.addEdge(6,7,1);
//        graph.addEdge(7,8,7);
//        graph.printGraph();

//        Graph MST = MinimumSpanningTree.generateMST(graph);
//        System.out.println("\nMinimum Spanning Tree: ");
//        MST.printGraph();


        RandomConnectedGraphGenerator randomGraph = new RandomConnectedGraphGenerator();
        System.out.println("\nThe generated random graph: ");
        randomGraph.generateRandomConnectedGraph().printGraph();
        Graph MST2 = MinimumSpanningTree.generateMST(randomGraph.getGraph());
        System.out.println("\nMinimum Spanning Tree: ");
        MST2.printGraph();
    }
}
