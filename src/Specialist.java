/**
 * Specialist treats the patient from the Treatment department and the specialist leaves the
 * Treatment after treating the patient and return after a period of time.
 * 
 * @author Dong Hyeog Jang
 * @date   11 March 2024
 * 
 */
public class Specialist extends Thread {

    private Treatment treatment;

    // Constructor for Specialist class
    public Specialist(Treatment treatment) {
        this.treatment = treatment;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("Specialist enters treatment room.");
                
                // gets current patient from Treatment who needs to be treated.
                Patient p = treatment.getCurrentPatient();

                // if successfully retrieve a patient, treat the patient.
                if (p != null) {
                    treatPatient(p);
                }

                System.out.println("Specialist leaves treatment room.");

                // time required by specialist to re-enter the Treatment.
                sleep(Params.SPECIALIST_AWAY_TIME);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // treat the patient.
    private void treatPatient(Patient p) {
        System.out.println(p.toString() + " treatment started.");
        try {
            // time required to treat the patient.
            sleep(Params.TREATMENT_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println(p.toString() + " treatment complete.");

        // update the treated flag from the patient to true
        p.treated = true;

        // notify the threads waiting on treatment object
        synchronized(treatment) {
            treatment.notifyAll();
        }
    }
}
