import java.io.*;
import java.util.*;

public class ApproximateIntervalPartition {

    float[][] sseMatrix;
    float[] M;
    float[] sumArray;
    float[] squareSumArray;
    List<Point> points;
    float c;
    List<Integer> partitions;

    public void setC(float c) {
        this.c = c;
    }

    public ApproximateIntervalPartition(float penalty, List<Point> points){
        c = penalty;
        this.points = points;
        int n = points.size();
        //Initializing opt array
        M = new float[n+1];
        for (int i = 0; i <n+1 ; i++) {
            M[i] = -1;
        }
        M[0] = 0;

    }

    public void initErrorArrays(){
        int n = points.size();
        sumArray = new float[n];
        squareSumArray = new float[n]
        ;

        //Initializing mean squared error
        for (int i = 0; i < n; i++) {
            float y = points.get(i).y;
            if(i==0){
                sumArray[i] = y;
                squareSumArray[i] = y*y;
            } else {
                sumArray[i]= sumArray[i-1]+ y;
                squareSumArray[i]= squareSumArray[i-1]+ (y*y);
            }
        }

    }
    private float computeSSE(int start, int end){
        float sse = 0;
        int n = end - start+1;
        float squaredSum =0;
        float sum = 0;
        if (start == 0){
            sum = sumArray[end];
            squaredSum = squareSumArray[end];
        } else {
            sum = sumArray[end] - sumArray[start-1];
            squaredSum = squareSumArray[end] - squareSumArray[start-1];
        }
        sse = Math.abs(squaredSum - (sum*sum/n));
        return sse;
    }

    public void computeSseMatrix(){
        int n = points.size();
        sseMatrix = new float[n][n];
        for(int i=0; i<n; i++){
            for(int j =0; j<n; j++){
                sseMatrix[i][j] = 0;
            }
        }
        for(int j = 0; j<n; j++){
            for (int i =0; i<j; i++){
                sseMatrix[i][j] = computeSSE(i, j);
            }
        }

    }

    public void computeOptTab(){
        int n = points.size();
        M = new float[n+1];
        M[0] = 0;
        float min;
        float error;
        for (int j = 1; j <= n; j++) {
            min = Float.MAX_VALUE;

            //get all previous errors
            for(int i = 1; i<=j; i++){
                if(i==1){
                    error = sseMatrix[i-1][j-1]+c;
                }else{
                    error = sseMatrix[i-1][j-1]+ c + M[i-1];
                }
                if(error<min){
                    min = error;
                }
            }
            M[j] = min;
        }

    }


    public float computeOptMem(int j, Map<Integer, Float> partitionMap) {
        if(j == 0){
            return 0;
        }
        if(partitionMap.containsKey(j)){
            return  partitionMap.get(j);
        }
        List<Float> previousErrors = new ArrayList<Float>();
        for (int i = 1; i <=j; i++) {
            previousErrors.add(sseMatrix[i-1][j-1] + c + computeOptMem(i - 1, partitionMap));
        }
        //opt[j] = Collections.min(prevErrors);
        partitionMap.put(j, Collections.min(previousErrors));
        return partitionMap.get(j);
    }


    public void findSegment(){
        int j = points.size();
        partitions = new ArrayList<Integer>();
        while(j>0){

            //list of all previous costs
            List<Float> segCosts = new ArrayList<Float>();
            for(int i=1; i<=j; i++){
                segCosts.add(sseMatrix[i-1][j-1]+c+ M[i-1]);
            }
            //find min in list as i and output that segment
            float  min = Collections.min(segCosts);
            int index = segCosts.indexOf(min);
            //print segment
            partitions.add(index);
            //set j to i-1
            j = index;
        }
    }

    public void findSegmentsMem(Map<Integer, Float> partitionMap) {
        int j = points.size();
        partitions = new ArrayList<Integer>();
        while (j > 0) {
            float minCost = Float.MAX_VALUE;
            int index = j;
            for (int i = 1; i <= j; i++) {
                float currCost = sseMatrix[i-1][j-1] + c + partitionMap.get(i-1);
                if (minCost > currCost) {
                    minCost = currCost;
                    index = i;
                }
            }
            partitions.add(index-1);
            j = index -1;
        }
    }

    public void printPoints(){
        System.out.print("[");
        for (int i = 0; i < points.size(); i++) {
            System.out.print("("+points.get(i).x+", "+points.get(i).y+")");
            if(i<points.size()-1){
                System.out.print(",");
            }
        }
        System.out.print("]");
    }

    public void printPartitions(){
        for (int i = (partitions.size()-1); i >= 0; i--) {
//            System.out.println(partitions.get(i));
            if(i==0){
                System.out.print("["+partitions.get(i)+"-"+points.get(points.size()-1).x+"]");
            } else{
                System.out.print("["+partitions.get(i)+"-"+(partitions.get(i-1)-1)+"]");
            }
        }
    }

    public void getPartitions(String type){
        initErrorArrays();
        computeSseMatrix();
        if("tab".equalsIgnoreCase(type)){
            computeOptTab();
            findSegment();
            printPartitions();
        } else{
            Map<Integer, Float> partitionMap = new HashMap<Integer, Float>();
            partitionMap.put(0,0f);
            computeOptMem(points.size(),partitionMap);
            findSegmentsMem(partitionMap);
            printPartitions();
        }
    }
    public static void main(String[] args) {
        ApproximateIntervalPartition approximateIntervalPartition;
        List<Point> points = new ArrayList<Point>();
        if(args.length == 0){
            System.out.println("Please enter proper parameters!");
        }
        else if(args[0].equalsIgnoreCase("test")) {
            if (args[1].equalsIgnoreCase("runtime")) {
                Random random = new Random();
                int num = Integer.parseInt(args[2]);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter("runtime_q2.csv.csv"));
                    StringBuilder sb = new StringBuilder();
                    sb.append("num of points,");
                    sb.append("penalty,");
                    sb.append("time(tabulation),");
                    sb.append("time(memoization(Array),");
                    sb.append("time(memoization(HashMap),");
                    sb.append('\n');
                    for (int i = 50; i < num; i = i + 20) {
                        points = new ArrayList<Point>();
                        System.out.println("current iter:" + i);
                        sb.append(i + ",");

                        for (int j = 0; j < i; j++) {
                            //points.add(new Point(random.nextInt(100), random.nextFloat() * 100));
                            points.add(new Point(j + 1, random.nextFloat() * 100));
                        }
                        int penalty = random.nextInt(500);
                        sb.append(penalty + ",");

                        approximateIntervalPartition = new ApproximateIntervalPartition(penalty, points);
                        long start = System.nanoTime();
                        approximateIntervalPartition.getPartitions("tab");
                        long end = System.nanoTime();
                        long tabulationTime = end - start;
                        sb.append(tabulationTime + ",");

                        approximateIntervalPartition = new ApproximateIntervalPartition(penalty, points);
                        long startMemHash = System.nanoTime();
                        approximateIntervalPartition.getPartitions("mem");
                        long endMemHash = System.nanoTime();
                        long memoizeTimeHash = endMemHash - startMemHash;
                        sb.append(memoizeTimeHash);
                        sb.append('\n');

                    }
                    writer.write(sb.toString());
                    writer.close();
                    System.out.println("Result available in runtime_q2.csv!");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (args[1].equalsIgnoreCase("penalty")) {
                points.add(new Point(0, 0));
                points.add(new Point(1, 1));
                points.add(new Point(2, 2));
                points.add(new Point(3, 3));
                points.add(new Point(4, 4));
                points.add(new Point(5, 5));
                points.add(new Point(6, 6));
                points.add(new Point(7, 7));
                points.add(new Point(8, 8));
                points.add(new Point(9, 9));
                approximateIntervalPartition = new ApproximateIntervalPartition(10, points);
                System.out.println("Points: ");
                approximateIntervalPartition.printPoints();
                float[] penalty = {1f, 5f, 10f, 50f};
                for (int i = 0; i < penalty.length; i++) {
                    approximateIntervalPartition.setC(penalty[i]);
                    System.out.print("\n\nPenalty = " + penalty[i]);
                    System.out.print("\nTabulation: ");
                    approximateIntervalPartition.getPartitions("tab");
                    System.out.print("\nMemoization: ");
                    approximateIntervalPartition.getPartitions("mem");
                }
            }
        }
        else{
            int num = Integer.parseInt(args[0]);
            float penalty = Float.parseFloat(args[1]);
            Random random = new Random();
            for (int j = 0; j < num; j++) {
                points.add(new Point(j + 1, random.nextInt(30)));
            }
            approximateIntervalPartition = new ApproximateIntervalPartition(penalty, points);
            System.out.println("Points: ");
            approximateIntervalPartition.printPoints();
            System.out.print("\n\nPenalty = "+penalty);
            System.out.print("\nTabulation: ");
            approximateIntervalPartition.getPartitions("tab");
            System.out.print("\nMemoization: ");
            approximateIntervalPartition.getPartitions("mem");

        }
    }
}

