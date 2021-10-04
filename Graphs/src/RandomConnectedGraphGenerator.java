import java.util.*;

public class RandomConnectedGraphGenerator {

    private Graph graph;

    public Graph getGraph() {
        return graph;
    }
    // Set a maximum limit to the vertices
    final int MAX_LIMIT = 20;
    final int MIN_VERTICES = 6;

    final int MAX_WEIGHT = 20;

    Random random = new Random();

    // Creating the constructor
    public Graph generateRandomConnectedGraph()
    {
        graph = new Graph();
        this.graph.vertices = random.nextInt(MAX_LIMIT-MIN_VERTICES) + MIN_VERTICES;
        System.out.println("Number of vertices: "+ graph.vertices);
        int maxEdges = graph.vertices+8;
        this.graph.edges = random.nextInt(maxEdges - graph.vertices-1) + graph.vertices-1;
        System.out.println("Number of edges: "+ graph.edges);

        // Creating an adjacency list
        graph.adjacencyList = new ArrayList<>(graph.vertices);
        for (int i = 0; i < graph.vertices; i++)
            graph.adjacencyList.add(new ArrayList<>());

        //to keep track of added vertices
        Set<Integer> graphVerticesSet = new HashSet<>();
        List<Integer> graphVerticesList = null;

        graphVerticesSet.add(0);
        int edgesAdded=0;
        for(int i= 0; i<graph.vertices; i++){
            int w=-1;
            //to make sure graph is connected select from previous set of nodes
            if(i==0){
                w = random.nextInt(graph.vertices);
                graphVerticesSet.add(w);

            }else {
                graphVerticesList = new ArrayList<>(graphVerticesSet);
                int item = random.nextInt(graphVerticesList.size());
                w = graphVerticesList.get(item);

                // Check if there is already an edge between v and w
                if ((i == w) || graph.adjacencyList.get(i).contains(new Edge(i, w))) {
                    continue;
                }
                graphVerticesSet.add(i);
            }
            int weight = random.nextInt(MAX_WEIGHT) + 1;
            graph.addEdge(i, w, weight);
            edgesAdded++;
        }

        int diff = graph.edges-edgesAdded;
        // A for loop to randomly generate edges
        for (int i = 0; i < diff; i++) {
            // Randomly select two vertices to
            // create an edge between them
            int v = random.nextInt(graph.vertices);
            int w = random.nextInt(graph.vertices);

            // Check if there is already an edge between v
            // and w
            if ((v == w) || graph.adjacencyList.get(v).contains(new Edge(v,w))) {
                i = i - 1;
                continue;
            }

            // Add an edge between them if
            // not previously created
            int weight = random.nextInt(MAX_WEIGHT) + 1;
            graph.addEdge(v, w, weight);
        }
        return graph;

    }

    public void printRandomGraph(){
        graph.printGraph();
    }


    public static void main(String[] args) {
        RandomConnectedGraphGenerator randomGraphGenerator = new RandomConnectedGraphGenerator();
        randomGraphGenerator.generateRandomConnectedGraph().printGraph();
        System.out.println(randomGraphGenerator.getGraph().isConnected());
        for (int i=0; i<10; i++){
            System.out.println(randomGraphGenerator.generateRandomConnectedGraph().isConnected());
        }


        Graph graph2 = new Graph(7,6);
        graph2.addEdge(0,1, 10);
        graph2.addEdge(0,2,12);
        graph2.addEdge(1,4, 5);
        graph2.addEdge(1,6,6);
        graph2.addEdge(5,4,55);
        graph2.addEdge(6,5,52);
        graph2.addEdge(6,4, 14);
        graph2.addEdge(5,3, 27);
        graph2.printGraph();
        System.out.println(graph2.isConnected());
        graph2.removeEdge(1,4);
        graph2.removeEdge(5,3);
        graph2.printGraph();
        System.out.println(graph2.isConnected());
    }
}
