import java.util.ArrayList;
import java.util.List;

public class CycleDetection {
    int cycleAt = -1;
    List<Integer> traversal;


    Boolean isCycle(int vertex, Boolean[] visited, int parent, List cycle, RandomGraphGenerator graph){

        visited[vertex] = true;
        cycle.add(vertex);
        //iterate through current vertex neighbours
        List<Integer> adjList = graph.adjacencyList.get(vertex);
        for(int i =0; i< adjList.size(); i++){
            int currentVertex = adjList.get(i);
            if(!visited[currentVertex]){
                return isCycle(currentVertex, visited, vertex, cycle, graph);
            }
            else if(currentVertex != parent){
                cycleAt = currentVertex;
                return true;

            }
        }
        return false;
    }

    Boolean checkCycle(RandomGraphGenerator graph){
        Boolean visited[] = new Boolean[graph.vertices];
        for (int i=0; i<visited.length; i++){
            visited[i] = false;
        }
        List<Integer> cycle = new ArrayList<>();
        if(isCycle(0, visited, -1, cycle, graph)) {
            traversal = cycle;
            return true;
        }
        traversal = new ArrayList<>();
        return false;
    }

    public void findCycle(RandomGraphGenerator graph){
        if(checkCycle(graph)){
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
        CycleDetection cycleDetection = new CycleDetection();

        RandomGraphGenerator graph1 = new RandomGraphGenerator(7,6);
        graph1.addEdge(0,1);
        graph1.addEdge(0,2);
        graph1.addEdge(0,3);
        graph1.addEdge(3,5);
        graph1.addEdge(3,6);
        graph1.addEdge(2,4);
        graph1.printGraph();
        cycleDetection.findCycle(graph1);


        RandomGraphGenerator graph2 = new RandomGraphGenerator(7,6);
        graph2.addEdge(0,1);
        graph2.addEdge(0,2);
        graph2.addEdge(1,4);
        graph2.addEdge(1,6);
        graph2.addEdge(5,4);
        graph2.addEdge(6,5);
        graph2.addEdge(6,4);
        graph2.addEdge(5,3);
        graph2.printGraph();
        cycleDetection.findCycle(graph2);

        RandomGraphGenerator graph3 = new RandomGraphGenerator("awdaw");
        graph3.printGraph();
        cycleDetection.findCycle(graph3);

    }
}
