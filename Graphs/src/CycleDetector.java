import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CycleDetector {
    int cycleAt = -1;
    List<Integer> cycleVertices;
    List<Integer> cycle;
    List<Edge> cycleEdges;

    public CycleDetector(){
        cycleAt = -1;
        cycleVertices = new ArrayList<>();
        cycle = new ArrayList<>();
        cycleEdges = new ArrayList<>();
    }

    Boolean isCycle(int vertex, Boolean[] visited, int parent, List traversal, Graph graph){

        visited[vertex] = true;
        traversal.add(vertex);
        //iterate through current vertex neighbours
        List<Edge> adjList = graph.adjacencyList.get(vertex);
        for(int i =0; i< adjList.size(); i++){
            int currentVertex = adjList.get(i).node2;
            if(!visited[currentVertex]){
                if( isCycle(currentVertex, visited, vertex, traversal, graph)){
                    return true;
                }
            }
            else if(currentVertex != parent){
                cycleAt = currentVertex;
                cycleVertices = traversal;
                return true;
            }
        }
        traversal.remove(traversal.size()-1);
        return false;
    }

    Boolean checkCycle(Graph graph){
        Boolean visited[] = new Boolean[graph.vertices];
        for (int i = 0; i < graph.vertices; i++)
            visited[i] = false;

        // function to detect cycle in different DFS trees
        for (int u = 0; u < graph.vertices; u++)
        {
            List<Integer> traversal = new ArrayList<>();
            // Don't recur for u if already visited
            if (!visited[u])
                if (isCycle(u, visited, -1,traversal, graph))
                    return true;
        }

        return false;
    }

    public List<Integer> findCycle(Graph graph){
        cycle = new ArrayList<>();
        if(checkCycle(graph)){
            cycleEdges = new ArrayList<>();
            int index = cycleVertices.indexOf(cycleAt);
            int edgeIndex = -1;
            for(int i = index; i< cycleVertices.size(); i++){
                cycle.add(cycleVertices.get(i));
                if (i== cycleVertices.size()-1){
                    edgeIndex = graph.adjacencyList.get(cycleVertices.get(i)).indexOf(new Edge(cycleVertices.get(i), cycleVertices.get(index)));
                    cycleEdges.add(graph.adjacencyList.get(cycleVertices.get(i)).get(edgeIndex));
                }
                else{
                    edgeIndex = graph.adjacencyList.get(cycleVertices.get(i))
                            .indexOf(new Edge(cycleVertices.get(i), cycleVertices.get(i+1)));
                    cycleEdges.add(graph.adjacencyList.get(cycleVertices.get(i)).get(edgeIndex));
                }
            }
        }
        return cycle;
    }

    public List<Edge> getCycleEdges(){
        return cycleEdges;
    }

    public void printCycleEdges(){
        System.out.print("\n{");
        for (Edge edge: cycleEdges){
            System.out.print(edge.toString());
        }
        System.out.print("}\n");
    }

    public void printCycle(){
        System.out.println();
        for (int v: cycle){
            System.out.print(v+" -> ");
        }
        System.out.print(cycle.get(0));
    }

    public Edge getHighestWeightedEdgeInCycle(){
        Collections.sort(getCycleEdges(), new EdgeComparator());
        return cycleEdges.get(0);
    }


    public static void main(String[] args) {
        CycleDetector cycleDetector = new CycleDetector();

//        Graph graph1 = new Graph(7,6);
//        graph1.addEdge(0,1, 12);
//        graph1.addEdge(0,2,10);
//        graph1.addEdge(0,3,5);
//        graph1.addEdge(3,5,8);
//        graph1.addEdge(3,6,1);
//        graph1.addEdge(2,4,30);
//        graph1.printGraph();
//        cycleDetector.findCycle(graph1);
//
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
//        cycleDetector.findCycle(graph);
//
//        Graph graph2 = new Graph(9,14);
//        graph2.addEdge(0,1, 4);
//        graph2.addEdge(0,7,8);
//        graph2.addEdge(1,2,8);
//        graph2.addEdge(2,3,7);
//        graph2.addEdge(3,4,9);
//        graph2.addEdge(2,5,4);
//        graph2.addEdge(5,6, 2);
//        graph2.addEdge(6,8,6);
//        graph2.addEdge(2,8,2);
//        graph2.addEdge(6,7,1);
//        graph2.addEdge(7,8,7);
//        graph2.printGraph();
//        cycleDetector.findCycle(graph2);
//
//        Graph disconnectedGraph = new Graph(5, 4);
//        disconnectedGraph.addEdge(0,1, -1);
//        disconnectedGraph.addEdge(2,3, -1);
//        disconnectedGraph.addEdge(3,4, -1);
//        disconnectedGraph.addEdge(2,4, -1);
//        disconnectedGraph.printGraph();
//        cycleDetector.findCycle(disconnectedGraph);

        RandomGraphGenerator randomGraphGenerator = new RandomGraphGenerator();
        Instant start;
        Instant end;
        Duration timeLapsed;
        for(int i=0; i<20; i++){
            Graph g = randomGraphGenerator.generateRandomGraph();
//            System.out.println("vertices "+g.vertices+", edges "+g.edges);
            start = Instant.now();
            System.out.println();
            if(!cycleDetector.findCycle(g).isEmpty()){
//                cycleDetector.printCycle();
            }else{
//                System.out.print(" No Cycle found!");
            }

            end = Instant.now();
            timeLapsed = Duration.between(start,end);
            System.out.println(g.vertices+", "+ timeLapsed.toNanos());
        }


    }
}

