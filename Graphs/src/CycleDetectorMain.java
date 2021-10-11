import java.util.Scanner;

public class CycleDetectorMain {
    public static void main(String[] args) {
        CycleDetector obj = new CycleDetector();
        if( args.length == 0){
            System.out.println("Enter number of vertices: ");
            Scanner sc = new Scanner(System.in);
            int vertices = sc.nextInt();
            System.out.print("Vertices: "+ vertices);
            obj.runCycleDetector(vertices);
        } else if ("test".equalsIgnoreCase(args[0])){
            obj.runTestCases();
        } else{
            System.out.println("Usage: CycleDetectorMain - Invalid program arguments.");
            System.exit(1);
        }
    }
}
