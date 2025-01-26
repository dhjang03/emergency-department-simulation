/**
 * Orderlies are acquired and released by nurse to transfer patient from one dpeartment to another.
 * 
 * @author Dong Hyeog Jang
 * @date   11 March 2024
 * 
 */
public class Orderlies {

    // Number of orderlies avaiable
    private volatile int numOrderlies = Params.ORDERLIES;

    // Number of orderlies required for transferring patient
    private static int transferOrderlies = Params.TRANSFER_ORDERLIES;

    // Update and return number of orderlies avaiable after a nurse hire orderlies for transfer
    public synchronized int getOrderlies() {

        // wait until there is enough orderlies left to acquire
        while (numOrderlies - transferOrderlies <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // update number of orderlies avaiable
        numOrderlies -= transferOrderlies;
        return numOrderlies;
    }

    // Update and return number of orderlies when a nurse release the orderlies.
    public synchronized int returnOrderlies() {

        // upate number of orderlies avaiable
        numOrderlies += transferOrderlies;

        // notify the thread waiting on getOrderlies()
        notifyAll();

        return numOrderlies;
    }
}
