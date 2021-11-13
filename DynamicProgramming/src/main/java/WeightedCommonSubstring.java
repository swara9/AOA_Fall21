import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedCommonSubstring {

    Map<Character, Double> letterFrequency;
    int delta;

    public WeightedCommonSubstring(){
        letterFrequency = new HashMap<Character, Double>();
        letterFrequency.put('A', 8.12);
        letterFrequency.put('B', 1.49);
        letterFrequency.put('C', 2.71);
        letterFrequency.put('D', 4.32);
        letterFrequency.put('E', 12.02);
        letterFrequency.put('F', 2.30);
        letterFrequency.put('G', 2.03);
        letterFrequency.put('H', 5.92);
        letterFrequency.put('I', 7.31);
        letterFrequency.put('J', 0.10);
        letterFrequency.put('K', 0.69);
        letterFrequency.put('L', 3.98);
        letterFrequency.put('M', 2.61);
        letterFrequency.put('N', 6.95);
        letterFrequency.put('O', 7.68);
        letterFrequency.put('P', 1.82);
        letterFrequency.put('Q', 0.11);
        letterFrequency.put('R', 6.02);
        letterFrequency.put('S', 6.28);
        letterFrequency.put('T', 9.10);
        letterFrequency.put('U', 2.88);
        letterFrequency.put('V', 1.11);
        letterFrequency.put('W', 2.09);
        letterFrequency.put('X', 0.17);
        letterFrequency.put('Y', 2.11);
        letterFrequency.put('Z', 0.07);
        delta = -3;
    }

    void getLongestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        double[][] weightMatrix = new double[m][n];
        double maxWeight = delta;
        int max_i = 0;
        int max_j = 0;
        int i = 0;
        int j = 0;
        for (i=0; i<m; i++)
        {
            for (j=0; j<n; j++)
            {
                char s1Char = s1.charAt(i);
                char s2Char = s2.charAt(j);
                double weight = delta;
                if (i == 0 || j == 0){
                    if(s1Char == s2Char){
                        weight = letterFrequency.get(s1Char);
                    }
                    weightMatrix[i][j] = weight;
                }else{
                    if(s1Char == s2Char) {
                        weight = letterFrequency.get(s1Char);
                    }
                    if(weightMatrix[i-1][j-1]>=0){
                        weight = weightMatrix[i-1][j-1] + weight;
                    }
                    weightMatrix[i][j] = weight;
                }
                if(maxWeight < weight){
                    maxWeight = weight;
                    max_i = i;
                    max_j = j;
                }
            }
        }
//        printMatrix(weightMatrix);

        i = max_i;
        j = max_j;
        while(weightMatrix[i-1][j-1]>0 && i>=0 && j>=0){
            i--;
            j--;
        }

        System.out.println("\n Longest Common Substring is "+ s1.substring(i, max_i+1)+" with weight "+ maxWeight );
    }

    private void printMatrix(double[][] matrix){
        for (int i= 0; i< matrix[0].length; i++){
            System.out.println();
            for (int j=0; j< matrix.length; j++ ){
                System.out.print(matrix[i][j]+"\t");
            }
        }
    }

    public static void main(String[] args) {
        WeightedCommonSubstring weightedCommonSubstring = new WeightedCommonSubstring();
        weightedCommonSubstring.getLongestCommonSubstring("BCDJEFAD", "BEGAEFHD");
    }

}
