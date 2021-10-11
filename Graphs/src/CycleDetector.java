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
        System.out.print("Cycle: ");
        for (int v: cycle){
            System.out.print(v+" -> ");
        }
        System.out.print(cycle.get(0));
        System.out.println();
    }

    public Edge getEdgeWithHighestWeight(){
        Edge highest = getCycleEdges().get(0);
        EdgeComparator comparator = new EdgeComparator();
        for(Edge edge: getCycleEdges()){
            if(comparator.compare(edge, highest)==1){
                highest = edge;
            }
        }
        return highest;
    }

    public void detectCycle(Graph graph){
        graph.printGraph();
        if(!findCycle(graph).isEmpty()){
            printCycle();
        }else{
            System.out.println("No cycle found!");
        }

    }

    public void testRunner(){
        System.out.println("\nCase 1: Connected graph with no cycles");
        Graph g1 = new Graph(5, 4);
        g1.addEdge(0,1);
        g1.addEdge(1,4);
        g1.addEdge(2,1);
        g1.addEdge(2,3);
        detectCycle(g1);

        System.out.println("\nCase 2: Connected graph with multiple cycles");
        Graph g2 = new Graph(6,7);
        g2.addEdge(0,1);
        g2.addEdge(2,1);
        g2.addEdge(0,2);
        g2.addEdge(1,3);
        g2.addEdge(3,4);
        g2.addEdge(4,5);
        g2.addEdge(3,5);
        detectCycle(g2);

        System.out.println("\nCase 3: Disconnected graph with no cycles");
        Graph g3 = new Graph(5, 3);
        g3.addEdge(0,1);
        g3.addEdge(2,3);
        g3.addEdge(3,4);
        detectCycle(g3);

        System.out.println("\nCase 4: Disconnected graph with cycle in first part");
        Graph g4 = new Graph(6,5);
        g4.addEdge(0,1);
        g4.addEdge(1,2);
        g4.addEdge(2,3);
        g4.addEdge(3,0);
        g4.addEdge(4,5);
        detectCycle(g4);

        System.out.println("\nCase 5: Disconnected graph with cycle not in first part");
        Graph g5 = new Graph(8, 7);
        g5.addEdge(0,1);
        g5.addEdge(1,2);
        g5.addEdge(3,4);
        g5.addEdge(4,5);
        g5.addEdge(5,6);
        g5.addEdge(6,7);
        g5.addEdge(7,4);
        detectCycle(g5);
    }

    public static void main(String[] args) {
        CycleDetector cycleDetector = new CycleDetector();
        cycleDetector.testRunner();

//        RandomGraphGenerator randomGraphGenerator = new RandomGraphGenerator();
//        long start;
//        long end;
//        long timeLapsed;
//        Graph g;
//
//
//        for(int i=10; i<=50000; i+=1000){
//
//                g = randomGraphGenerator.generateRandomGraph(i);
//                start = System.nanoTime();
//                cycleDetector.checkCycle(g);
//                end = System.nanoTime();
//                timeLapsed = end-start;
//                System.out.println(g.vertices+", "+g.edges+", "+(g.vertices+g.edges)+", "+ timeLapsed);
//
//        }
    }
}

