import java.util.Scanner;

public class CycleDetectorRunner {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: CycleDetectorRunner <mode>. Valid values - 'test', 'prod'");
            System.exit(-1);
        } else {
            String mode = args[0].toLowerCase();
            CycleDetector obj = new CycleDetector();
            if ("test".equals(mode)) {
                obj.runTestCases();
            } else if ("prod".equals(mode)) {
                System.out.println("Enter number of vertices: ");
                Scanner sc = new Scanner(System.in);
                int vertices = sc.nextInt();
                System.out.print("Vertices: "+ vertices);
                obj.runCycleDetector(vertices);
            }else{
                System.out.println("Invalid mode selected. Please run the program again.");
                System.exit(-1);
            }
        }
    }
}
