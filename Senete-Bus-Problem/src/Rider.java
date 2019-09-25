import java.util.concurrent.Semaphore;

class Rider implements Runnable {

    private final int index;
    private BusStop busStop;
    private final Semaphore riderBoardingSemaphore;
    private final Semaphore busDepartureSemaphore;
    private final Semaphore riderBusStopEntranceSemaphore;
    private final Semaphore mutex;

    public Rider(Semaphore riderBusStopEntranceSem, Semaphore riderBoardingSem, Semaphore busDepartureSem, Semaphore mutex, int index, BusStop busStop) {
        this.riderBusStopEntranceSemaphore = riderBusStopEntranceSem;
        this.riderBoardingSemaphore = riderBoardingSem;
        this.busDepartureSemaphore = busDepartureSem;
        this.index = index;
        this.mutex = mutex;
        this.busStop = busStop;
    }

    @Override
    public void run() {

        try {
            // Enter the bus stop by acquiring the semaphore
            riderBusStopEntranceSemaphore.acquire();

            mutex.acquire();
            enterBoardingArea();
            busStop.incrementRidersCount();
            mutex.release();

            //Board the bus by acquiring the semaphore
            riderBoardingSemaphore.acquire();
            board();

            // Enter waiting area by Releasing the semaphore
            riderBusStopEntranceSemaphore.release();
            busStop.decrementRidersCount();

            // When the riders are boarded, allow the bus to depart
            if (busStop.getRidersCount() == 0)
                busDepartureSemaphore.release();
            else
                // If there are more riders waiting, allow them to get into the bus
                riderBoardingSemaphore.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void board() {
        System.out.println("!!!![ RIDER : " + index + "] boarded");
    }

    public void enterBoardingArea() {
        System.out.println("****************[Rider :" + index + " ] Entered to Bus Stop");
    }
}
