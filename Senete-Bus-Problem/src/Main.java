import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        float riderArrivalTime = 30f * 1000;
        float busArrivalTime = 20 * 60f * 1000 ;

        Scanner scanner = new Scanner(System.in);
        String userInput;
        BusStop busStop = new BusStop();

        System.out.println("\nPRESS ANY KEY TO TERMINATE\n" );

        ObjectCreator riderC = new ObjectCreator(riderArrivalTime, busStop,1);
        (new Thread(riderC)).start();


        ObjectCreator busC = new ObjectCreator(busArrivalTime,busStop,0);
        (new Thread(busC)).start();

        // Program Termination
        while(true){
            userInput = scanner.nextLine();
            if(userInput != null)
                System.out.println("############ROGRAM TERMINATED##########");
                System.exit(0);
        }
    }
}
