import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Point{
    public int x;
    public float y;

    public Point(int x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}

public class SegmentedLeastSquares {

    float[][] sseMatrix;
    float[] M;

    private float computeSSE(int start, int end, List<Point> p){
            float sse = 0;
            int i = 0;
            int n = end - start+1;
            float squaredSum =0;
            float sum = 0;
            float y = 0;
            //compute sum of y squares
            for(i = start; i<=end ; i++){
                y = p.get(i).y;
                squaredSum += (y * y);
                sum += y;
            }
            sse = Math.abs(squaredSum - (sum*sum/n));
            return sse;
     }

    public void computeSseMatrix(List<Point> points){
        int n = points.size();
        sseMatrix = new float[n][n];
        for(int i=0; i<n; i++){
            for(int j =0; j<n; j++){
                sseMatrix[i][j] = 0;
            }
        }
        for(int j = 0; j<n; j++){
            for (int i =0; i<j; i++){
                sseMatrix[i][j] = computeSSE(i, j, points);
            }
        }

        for(int i=0; i<n; i++){
            System.out.println();
            for(int j =0; j<n; j++){
                System.out.print(sseMatrix[i][j]+"\t");
            }
        }
    }

    public float[] getOptimumError(int n, float c){
        M = new float[n+1];
        List<Float> previousErrors;
        M[0] = 0;
        for (int j = 1; j <= n; j++) {
            previousErrors = new ArrayList<Float>();
            //get all previous errors
            for(int i = 1; i<=j; i++){
                if(i==1){
                    previousErrors.add(sseMatrix[i-1][j-1]+c);
                }else{
                    previousErrors.add(sseMatrix[i-1][j-1]+ c + M[i-1]);
                }
            }
            M[j] = Collections.min(previousErrors);
        }

        System.out.println("\nError matrix: ");
        for (int i=0; i<n+1; i++){
            System.out.print(M[i] +"\t");
        }

        return M;
    }

    public void traceback(int n){

    }

    public static void main(String[] args) {
        SegmentedLeastSquares segmentedLeastSquares = new SegmentedLeastSquares();
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
        segmentedLeastSquares.computeSseMatrix(points);
        segmentedLeastSquares.getOptimumError(points.size(), 10);
    }

}

