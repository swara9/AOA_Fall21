import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SegmentedLeastSquares {

    float[][] sseMatrix;
    float[] M;
    float[] sumArray;
    float[] squareSumArray;
    List<Point> points;

    public SegmentedLeastSquares(List<Point> points){
        this.points = points;
    }

    public void initErrorArrays(){
        int n = points.size();
        sumArray = new float[n];
        squareSumArray = new float[n];

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

    public void getOptimumError(float c){
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

        System.out.println("\nError matrix: ");
        for (int i=0; i<n+1; i++){
            System.out.print(M[i] +"\t");
        }
    }

    public void findSegment(int c){
        int j = (points.size());
        while(j>0){
            //list of all previous costs
            List<Float> segCosts = new ArrayList<Float>();

            for(int i=1; i<=j; i++){
                segCosts.add(sseMatrix[i-1][j-1]+c+M[i-1]);
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
        SegmentedLeastSquares segmentedLeastSquares = new SegmentedLeastSquares(points);
        segmentedLeastSquares.initErrorArrays();
        segmentedLeastSquares.computeSseMatrix();
        segmentedLeastSquares.getOptimumError(10);
        segmentedLeastSquares.findSegment(10);
    }

}

