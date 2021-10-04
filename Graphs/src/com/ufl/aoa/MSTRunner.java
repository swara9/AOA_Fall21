package com.ufl.aoa;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

/**
 * Parametrize class to run the algo multiple time to plt the running time
 */

public class MSTRunner {

    static Random random = new Random();
    public static void main(String[] args) {
        int loopCount = Integer.parseInt(args[0]);
        for (int i = 0; i < loopCount; i++) {
            int maxVerticesLimit = random.nextInt(Integer.parseInt(args[1]));
            int maxWeightLimit = random.nextInt(Integer.parseInt(args[2]));
            RandomConnectedGraphGenerator randomGraph = new RandomConnectedGraphGenerator(maxVerticesLimit, maxWeightLimit);
            System.out.println("\nThe generated random graph: ");
            Graph graph = randomGraph.getGraph();
            graph.printGraph();
            Instant start = Instant.now();
            Graph MST2 = MinimumSpanningTree.generateMST(graph);
            Instant end = Instant.now();
            Duration timeLapsed = Duration.between(start,end);
            System.out.println("\nMinimum Spanning Tree: ");
            MST2.printGraph();
            System.out.println("Completed "+(i+1)+" Execution in " + timeLapsed.toNanos()+" nanos" + " OR "+ timeLapsed.toMillis()+" millis.");
        }
    }
}
