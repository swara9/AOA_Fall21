import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class SegmentedLeastSquaresMem {

    float[][] sseMatrix;
    float[] Mem;
    float[] M;
    float[] sumArray;
    float[] squareSumArray;
    List<Point> points;
    float c;


    public SegmentedLeastSquaresMem(int penalty, List<Point> points){
        c = penalty;
        this.points = points;
    }

    public void initErrorArrays(){
        int n = points.size();
        sumArray = new float[n];
        squareSumArray = new float[n]
        ;
        //Initializing opt array
        Mem = new float[n+1];
        for (int i = 0; i <n+1 ; i++) {
            Mem[i] = -1;
        }
        Mem[0] = 0;

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
        int i = 0;
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

        for(int i=0; i<n; i++){
            System.out.println();
            for(int j =0; j<n; j++){
                System.out.print(sseMatrix[i][j]+"\t");
            }
        }
    }

    public void getOptimumError(int n, float c){
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

        System.out.println("\nError matrix: ");
        for (int i=0; i<n+1; i++){
            System.out.print(M[i] +"\t");
        }
    }

    public float computeOpt(int j, float c){
        List<Float> previousErrors;
        if(j == 0){
            return 0;
        }
        if(Mem[j]!=-1){
            return Mem[j];
        }
        previousErrors = new ArrayList<Float>();
        for (int i = 1; i <=j ; i++) {
            previousErrors.add(sseMatrix[i-1][j-1]+c+ computeOpt(i-1, c));
        }
        Mem[j] = Collections.min(previousErrors);
        return Mem[j];
    }

    public float computeOptMap(int j, Map<Integer, Float> partitionMap) {
        if(j == 0){
            return 0;
        }
        if(partitionMap.containsKey(j)){
            return  partitionMap.get(j);
        }
        List<Float> previousErrors = new ArrayList<Float>();
        for (int i = 1; i <=j; i++) {
            previousErrors.add(sseMatrix[i-1][j-1] + c + computeOptMap(i - 1, partitionMap));
        }
        //opt[j] = Collections.min(prevErrors);
        partitionMap.put(j, Collections.min(previousErrors));
        return partitionMap.get(j);
    }


    public void findSegment(){
        int j = points.size();
        while(j>0){

            //list of all previous costs
            List<Float> segCosts = new ArrayList<Float>();
            for(int i=1; i<=j; i++){
                segCosts.add(sseMatrix[i-1][j-1]+c+ Mem[i-1]);
            }
            //find min in list as i and output that segment
            float  min = Collections.min(segCosts);
            int index = segCosts.indexOf(min);
            //print segment
            System.out.println("["+points.get(index).x+"-"+points.get(j-1).x+"]");
            //set j to i-1
            j = index;
        }
    }

    public void findPartitions(Map<Integer, Float> partitionMap) {
        int j = points.size();
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
            System.out.println("["+points.get(index-1).x+"-"+points.get(j-1).x+"]");
            j = index -1;
        }
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(2,2));
        points.add(new Point(3,3));
        points.add(new Point(4,4));
        points.add(new Point(5,5));
        points.add(new Point(6,6));
        points.add(new Point(7,7));
        points.add(new Point(8,8));
        points.add(new Point(9,9));
        SegmentedLeastSquaresMem segmentedLeastSquares = new SegmentedLeastSquaresMem(10, points);

        segmentedLeastSquares.initErrorArrays();
        segmentedLeastSquares.computeSseMatrix();

        //without hashmap
        segmentedLeastSquares.computeOpt(points.size(), 10);
        for (int i=0; i<points.size()+1; i++){
            System.out.print(segmentedLeastSquares.Mem[i] +"\t");
        }
        segmentedLeastSquares.findSegment();

        //with hashmap
        Map<Integer, Float> partitionMap = new HashMap<Integer, Float>();
        partitionMap.put(0, 0f);
        segmentedLeastSquares.computeOptMap(points.size(), partitionMap);
        System.out.println(partitionMap);
        segmentedLeastSquares.findPartitions(partitionMap);
    }

}

