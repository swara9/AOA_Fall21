import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WeightedCommonSubstring {

    Map<Character, Double> letterFrequency;
    float delta;

    public WeightedCommonSubstring(float delta){
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
        this.delta = delta;
    }

    void getLongestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        double[][] LCS = new double[m][n];
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
                if(s1Char == s2Char) {
                    weight = letterFrequency.get(s1Char);
                }
                if (i == 0 || j == 0){
                    LCS[i][j] = weight;
                }else{
                    weight = Math.max((LCS[i-1][j-1] + weight), delta);
                    LCS[i][j] = weight;
                }
                if(maxWeight < weight){
                    maxWeight = weight;
                    max_i = i;
                    max_j = j;
                }
            }
        }

        i = max_i;
        j = max_j;
        while(i>0 && j>0 && LCS[i-1][j-1]>0){
            i--;
            j--;
        }
        System.out.println("\n LCS for "+s1+" "+ s2+" with penalty "+ -delta+" is "+ s1.substring(i, max_i+1)+", "+ s2.substring(j, max_j+1)+" of weight "+ maxWeight );
    }

    private void printMatrix(double[][] matrix){
        for (int i= 0; i< matrix[0].length; i++){
            System.out.println();
            for (int j=0; j< matrix.length; j++ ){
                System.out.print(matrix[j][i]+"\t");
            }
        }
    }

    public static String randomStrGenerator(int len) {
        StringBuilder sc = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = (char) Math.floor(Math.random()*(90-65+1)+65);
            sc.append(c);
        }
        return sc.toString();
    }

    public static void main(String[] args) {
        WeightedCommonSubstring weightedCommonSubstring = new WeightedCommonSubstring(-10);
        if(args.length == 0){
            System.out.println("Please enter proper parameters!");
        }
        else {
            if (args[0].equalsIgnoreCase("test")) {
                if (args[1].equalsIgnoreCase("delta")) {
                    float[] deltaArr = {0.5f, 2f, 3.5f, 4f, 5.5f, 6.5f, 7f, 8.5f, 9f, 10.5f, 11f, 12.f};
                    for (int i = 0; i < deltaArr.length; i++) {
                        weightedCommonSubstring.delta = -deltaArr[i];
                        weightedCommonSubstring.getLongestCommonSubstring("ABCAABCAA", "ABBCAACCBBBBBB");
                    }
                } else {
                    int length = Integer.parseInt(args[1]);
                    weightedCommonSubstring.delta = -Float.parseFloat(args[2]);
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter("runtime_q1.csv"));
                        StringBuilder sb = new StringBuilder();
                        sb.append("m*n,");

                        sb.append("time");
                        sb.append('\n');
                        int m;
                        int n;
                        long totalTime = 0;
                        for (int i = 10; i < length; i = i + 10) {
                            sb.append(i * i);
                            sb.append(",");
                            totalTime = 0;
                            for (int j = 0; j < 10; j++) {
                                String first = randomStrGenerator(i);
                                String second = randomStrGenerator(i);

                                long start = System.nanoTime();
                                weightedCommonSubstring.getLongestCommonSubstring(first.toUpperCase(), second.toUpperCase());
                                long end = System.nanoTime();
                                totalTime += (end - start);

                            }
                            sb.append(totalTime / 10);
                            sb.append('\n');

                        }

                        writer.write(sb.toString());
                        writer.close();
                        System.out.println("Result available in runtime_q1.csv!");
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String s1 = args[0];
                String s2 = args[1];
                weightedCommonSubstring.delta = -Float.parseFloat(args[2]);
                weightedCommonSubstring.getLongestCommonSubstring(s1.toUpperCase(), s2.toUpperCase());
            }
        }
    }
}
