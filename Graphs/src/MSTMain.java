import java.util.Scanner;

/**
 * Parametrize class to run the algo multiple time to plt the running time
 */

public class MSTMain {
    public static void main(String[] args) {
        MinimumSpanningTree obj = new MinimumSpanningTree();
        if( args.length == 0){
            System.out.println("Enter number of vertices: ");
            Scanner sc = new Scanner(System.in);
            int vertices = sc.nextInt();
            System.out.print("Vertices: "+ vertices);
            obj.runMST(vertices);
        } else if ("test".equalsIgnoreCase(args[0])){
            obj.runTestCase();
        } else{
            System.out.println("Usage: MSTMAin - Invalid program arguments.");
            System.exit(1);
        }
    }
}