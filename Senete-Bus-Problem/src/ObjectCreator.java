import java.util.Random;

public class ObjectCreator implements Runnable {

    private float meanArrivalTime;
    private BusStop busStop;
    private static Random random;
    int objectType;

    public ObjectCreator(float meanArrivalTime, BusStop busStop, int objectType) {
        random = new Random();
        this.meanArrivalTime = meanArrivalTime;
        this.busStop = busStop;
        this.objectType = objectType;
//        System.out.println("assigned "+this.objectType+ " recieved "+ob);
    }

    @Override
    public void run() {
        if(objectType == 1) {
//            System.out.println("Riders generator arrives");
            int indexOfRider = 1;
            while (!Thread.currentThread().isInterrupted()) {

                try {
                    Rider rider = new Rider(busStop.getRiderBusStopEntranceSem(), busStop.getRiderBoardingAreaEntranceS(), busStop.getBusDepartureS(), busStop.getMutex(), indexOfRider, busStop);
                    (new Thread(rider)).start();
                    indexOfRider++;
                    Thread.sleep(getInterArrivalTime());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if(objectType == 0){
//            System.out.println("Bus generator arrives");
            int busId = 1;

            while (!Thread.currentThread().isInterrupted()) {

                try {
                    Bus bus = new Bus(busStop.getRiderBoardingAreaEntranceS(), busStop.getBusDepartureS(), busStop.getMutex(), busId, busStop);
                    (new Thread(bus)).start();

                    busId++;
                    Thread.sleep(getInterArrivalTime());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("BUS ARRIVAL IS OVER");
        }
    }

    // Get riders inter arrival time
    public long getInterArrivalTime() {
        float lambda = 1 / meanArrivalTime;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}
