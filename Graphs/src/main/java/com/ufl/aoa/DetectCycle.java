package com.ufl.aoa;

import java.util.ArrayList;
import java.util.List;

public class DetectCycle {
    int cycleAt = -1;
    List<Integer> traversal;


    Boolean isCycle(int vertex, Boolean[] visited, int parent, List cycle, RandomSparseGraph randomSparseGraph){

        visited[vertex] = true;
        cycle.add(vertex);
        //iterate through current vertex neighbours
        List<Integer> adjList = randomSparseGraph.adjacencyList.get(vertex);
        for(int i =0; i< adjList.size(); i++){
            int currentVertex = adjList.get(i);
            if(!visited[currentVertex]){
                return isCycle(currentVertex, visited, vertex, cycle, randomSparseGraph);
            }
            else if(currentVertex != parent){
                cycleAt = currentVertex;
                return true;

            }
        }
        return false;
    }

    Boolean checkCycle(RandomSparseGraph randomSparseGraph){
        Boolean[] visited = new Boolean[randomSparseGraph.getVertices()];
        for (int i=0; i<visited.length; i++){
            visited[i] = false;
        }
        List<Integer> cycle = new ArrayList<>();
        if(isCycle(0, visited, -1, cycle, randomSparseGraph)) {
            traversal = cycle;
            return true;
        }
        traversal = new ArrayList<>();
        return false;
    }

    public void findCycle(RandomSparseGraph randomSparseGraph){
        if(checkCycle(randomSparseGraph)){
            System.out.print("Cycle: ");
            int index = traversal.indexOf(cycleAt);
            for(int i = index; i<traversal.size(); i++){
                if (i==traversal.size()-1){
                    System.out.print(traversal.get(i));
                }
                else{
                    System.out.print(traversal.get(i)+" -> ");
                }
            }
        }else{
            System.out.println(" No Cycle found!");
        }
    }



    public static void main(String[] args) {
        DetectCycle detectCycle = new DetectCycle();

//        RandomSparseGraph randomSparseGraph = new RandomSparseGraph(7,6);
//        randomSparseGraph.addEdge(0,1);
//        randomSparseGraph.addEdge(0,2);
//        randomSparseGraph.addEdge(0,3);
//        randomSparseGraph.addEdge(3,5);
//        randomSparseGraph.addEdge(3,6);
//        randomSparseGraph.addEdge(2,4);
//        randomSparseGraph.printGraph();
//        detectCycle.findCycle(randomSparseGraph);
//
//
//        RandomSparseGraph randomSparseGraph2 = new RandomSparseGraph(7,6);
//        randomSparseGraph2.addEdge(0,1);
//        randomSparseGraph2.addEdge(0,2);
//        randomSparseGraph2.addEdge(1,4);
//        randomSparseGraph2.addEdge(1,6);
//        randomSparseGraph2.addEdge(5,4);
//        randomSparseGraph2.addEdge(6,5);
//        randomSparseGraph2.addEdge(6,4);
//        randomSparseGraph2.addEdge(5,3);
//        randomSparseGraph2.printGraph();
//        detectCycle.findCycle(randomSparseGraph2);

        RandomSparseGraph randomSparseGraph3 = new RandomSparseGraph();
        detectCycle.findCycle(randomSparseGraph3);


    }
}
