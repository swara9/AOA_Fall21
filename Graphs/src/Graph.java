import java.util.*;

public class Graph {
    public int vertices;
    public int edges;
    public List<List<Edge>> adjacencyList;

    public Graph(){
        vertices = 0;
        edges = 0;
        adjacencyList = new ArrayList();
    }

    public Graph(int vertices, int edges){
        this.vertices = vertices;
        this.edges = edges;
        adjacencyList = new ArrayList();
        for(int i =0; i< vertices; i++){
            adjacencyList.add(new ArrayList<>());
        }
    }

    // Method to add edges between given vertices
    public void addEdge(int v, int w, int weight)
    {
        // Add w to v's adjacency list
        adjacencyList.get(v).add(new Edge(v, w, weight));
        adjacencyList.get(w).add(new Edge(w, v, weight));
    }

    public void removeEdge(int v, int w){
        adjacencyList.get(v).remove(new Edge(v,w));
        adjacencyList.get(w).remove(new Edge(w,v));
    }


    public boolean isConnected(){
        boolean[] visited = new boolean[vertices];
        for (int i=0; i<vertices; i++){
            visited[i] = false;
        }
        Stack<Integer> stack = new Stack<>();
        visited[0] = true;
        int visistedVertices = 1;
        stack.push(0);
        while (!stack.empty()) {
            int v = stack.pop();
            for (Edge e : adjacencyList.get(v)) {
                int current = e.node2;
                if (!visited[current]) {
                    visited[current] = true;
                    visistedVertices++;
                    stack.push(current);
                }
            }
        }
        return visistedVertices==vertices;
    }

    public void printGraph() {
        // Print the graph
        System.out.println();
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print(i + " -> { ");

            List<Edge> list = adjacencyList.get(i);

            if (list.isEmpty())
                System.out.print(" No adjacent vertices ");
            else {
                int size = list.size();
                for (int j = 0; j < size; j++) {

                    System.out.print(list.get(j).toString());
                    if (j < size - 1)
                        System.out.print(" , ");
                }
            }
            System.out.println("}");
        }
    }

}
