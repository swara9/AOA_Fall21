import java.time.Duration;
import java.time.Instant;
import java.util.Random;

/**
 * Parametrize class to run the algo multiple time to plt the running time
 */

public class MSTRunner {

//    public static void run(int limit){
////        int maxVerticesLimit = random.nextInt(limit);
//        RandomConnectedGraphGenerator randomGraph = new RandomConnectedGraphGenerator(limit);
//        randomGraph.generateRandomConnectedGraph();
////            System.out.println("\nThe generated random graph: ");
//        Graph graph = randomGraph.getGraph();
////            graph.printGraph();
//        Instant start = Instant.now();
////            System.out.println(i+1+") START  "+start);
//        Graph MST2 = MinimumSpanningTree.generateMST(graph);
//        Instant end = Instant.now();
////            System.out.print("  END  "+end);
//        Duration timeLapsed = Duration.between(start,end);
////            System.out.println("\nMinimum Spanning Tree: ");
////            MST2.printGraph();
////            System.out.println("Completed "+(i+1)+" Execution in " + timeLapsed.toNanos()+" nanos, " + " OR "+ timeLapsed.toMillis()+" millis.");
////        int complexity = graph.vertices*(graph.edges-graph.vertices+1);
//        System.out.print(graph.edges+", "+graph.vertices+", "+(graph.edges+graph.vertices)+", "+ timeLapsed.toNanos());
//        System.out.println();
//    }

    static Random random = new Random();
    public static void main(String[] args) {
        int loopCount = Integer.parseInt(args[0]);
        int limit = Integer.parseInt(args[1]);
        for (int i = 0; i < loopCount; i++) {
//            run(limit);
        }
    }
}