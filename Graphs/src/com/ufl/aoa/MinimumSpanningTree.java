package com.ufl.aoa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimumSpanningTree {
    List<Edge> allEdges;

    public MinimumSpanningTree(){
        allEdges = new ArrayList<>();
    }

    public static Graph generateMST(Graph graph) {
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
        RandomConnectedGraphGenerator randomGraph = new RandomConnectedGraphGenerator();
        System.out.println("\nThe generated random graph: ");
        Graph graph = randomGraph.getGraph();
        graph.printGraph();
        Graph MST2 = MinimumSpanningTree.generateMST(graph);
        System.out.println("\nMinimum Spanning Tree: ");
        MST2.printGraph();
    }
}
