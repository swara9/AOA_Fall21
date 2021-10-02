import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimumSpanningTree {
    List<Edge> allEdges;

    public MinimumSpanningTree(){
        allEdges = new ArrayList<>();
    }

    private static Graph generateMST(Graph graph) {
        //sort edges in descending order
        Collections.sort(graph.allEdges, new EdgeComparator());
        graph.printAllEdges();

        List<Edge> sortedEdges = graph.allEdges;
        int edgesCount = graph.edges;
        while( edgesCount > (graph.vertices-1)){
            //remove longest route
            Edge currentEdge = sortedEdges.get(0);
            int node1 = currentEdge.node1;
            int node2 = currentEdge.node2;
            graph.removeEdge(node1, node2);
            graph.allEdges.remove(0);
//            check if graph is disconnected
            if(!graph.isConnected()){
                graph.addEdge(node1, node2, currentEdge.weight);
                System.out.println("graph disconnected! "+currentEdge.toString());
            }else{
                //update edges
                edgesCount--;
                System.out.println("Removed edge: " + currentEdge.toString());
            }
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
//        graph.printAllEdges();
//
//        Graph MST = MinimumSpanningTree.generateMST(graph);
//        MST.printGraph();
//        MST.printAllEdges();


        RandomConnectedGraphGenerator randomGraph = new RandomConnectedGraphGenerator();
        System.out.println("\nThe generated random graph: ");
        randomGraph.getGraph().printGraph();
        Graph MST2 = MinimumSpanningTree.generateMST(randomGraph.getGraph());
        System.out.println("\nMinimum Spanning Tree: ");
        MST2.printGraph();
    }


}
