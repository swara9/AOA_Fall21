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


    public static void main(String[] args) {
        CycleDetector cycleDetector = new CycleDetector();


        RandomGraphGenerator randomGraphGenerator = new RandomGraphGenerator();
        long start;
        long end;
        long timeLapsed;
        Graph g;


        for(int i=10; i<=50000; i+=1000){

                g = randomGraphGenerator.generateRandomGraph(i);
                start = System.nanoTime();
                cycleDetector.checkCycle(g);
                end = System.nanoTime();
                timeLapsed = end-start;
                System.out.println(g.vertices+", "+g.edges+", "+(g.vertices+g.edges)+", "+ timeLapsed);

        }
    }
}

