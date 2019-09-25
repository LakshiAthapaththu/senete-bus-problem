import java.util.concurrent.Semaphore;

public class Bus implements Runnable {

    private final Semaphore riderBoardingS;
    private final Semaphore busDepartureS;
    private final Semaphore mutex;
    private final int index;
    private BusStop busStop;


    public Bus(Semaphore riderBoardingS, Semaphore busDepartureS, Semaphore mutex, int index, BusStop busStop) {
        this.riderBoardingS = riderBoardingS;
        this.busDepartureS = busDepartureS;
        this.mutex = mutex;
        this.index = index;
        this.busStop = busStop;
    }

    @Override
    public void run() {

        try {
            mutex.acquire();
            Arrive();

            // Looking for waiting riders
            if (busStop.getRidersCount() > 0) {

                // Allowing a rider to get into the bus by releasing the rider boarding semaphore
                riderBoardingS.release();
                // Wait the bus until the riders get boarded by acquiring the bus departure semaphore
                busDepartureS.acquire();
            }

            mutex.release();
            depart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void depart() {
        System.out.println("----(Bus : " + index + ") DEPARTED");
    }

    public void Arrive() {
        System.out.println("----(Bus : " + index + ") ARRIVED");
        System.out.println("Waiting rider count : " + busStop.getRidersCount()+"]");
    }
}