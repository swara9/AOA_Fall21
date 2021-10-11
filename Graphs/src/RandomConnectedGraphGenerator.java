import java.util.*;

public class RandomConnectedGraphGenerator {

    private int maxVerticesLimit;
    private Graph graph;
    // Set a maximum limit to the vertices
    final int MIN_VERTICES = 6;
    final int MAX_WEIGHT = 20;

    public RandomConnectedGraphGenerator(int maxVerticesLimit) {
        this.maxVerticesLimit = maxVerticesLimit;
    }

    // Creating the constructor
    public RandomConnectedGraphGenerator() {
        this(100);
    }

    public Graph getGraph() {
        return graph;
    }

    Random random = new Random();

    // Creating the constructor
    public Graph generateRandomConnectedGraph(int vertices)
    {
        graph = new Graph();

        //Remove this
//        this.graph.vertices = random.nextInt(maxVerticesLimit);
        this.graph.vertices = vertices;
        if(graph.vertices<MIN_VERTICES){
            graph.vertices = graph.vertices+MIN_VERTICES;
        }
        int maxEdges = graph.vertices+8;
        this.graph.edges = random.nextInt(maxEdges - graph.vertices-1) + graph.vertices-1;

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
            // Randomly select two vertices to create an edge between them
            int v = random.nextInt(graph.vertices);
            int w = random.nextInt(graph.vertices);

            // Check if there is already an edge between v and w
            if ((v == w) || graph.adjacencyList.get(v).contains(new Edge(v,w))) {
                i = i - 1;
                continue;
            }

            // Add an edge between them if not previously created
            int weight = random.nextInt(MAX_WEIGHT) + 1;
            graph.addEdge(v, w, weight);
        }
        return graph;
    }
}
