import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

/**
 * A Foyer is one type of department in Emergency Department where a patient arrive and depart from.
 * 
 * @author Dong Hyeog Jang
 * @date   11 March 2024
 * 
 */
public class Foyer implements Department {

    // Store newly arrived patient in queue waiting to be allocated to a nurse
    private volatile Queue<Patient> unallocatedPatients = new ArrayDeque<>();

    // Store patients who finished examination or treatment, ready to depart
    private volatile HashSet<Patient> treatedPatients = new HashSet<>();

    // Store patients who are allocated to a nurse
    private HashSet<Patient> allocatedPatients = new HashSet<>();

    // Add newly arrived patient to queue and notify the thread waiting for new patients
    public synchronized void arriveAtED(Patient patient) {
        System.out.println(patient.toString() + " admitted to ED.");
        unallocatedPatients.add(patient);

        // notify a thread waiting on getNextPatient()
        notify();
    }

    // Clear all patients who have gone through examination or treatment
    public synchronized void departFromED() {
        if (treatedPatients.isEmpty()) {
            return;
        }

        for (Patient patient : treatedPatients) {
            System.out.println(patient.toString() + " discharged from ED.");
        }

        treatedPatients.clear();
    }

    // Return next patient who is not allocated to a nurse
    public synchronized Patient getNextPatient() {

        // wait if there is no patient avaiable
        while (unallocatedPatients.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        // get next patient and return it.
        Patient patient = unallocatedPatients.poll();
        allocatedPatients.add(patient);
        return patient;
    }

    // Simulate the process of the patient entering department
    // e.g., leave triage and enter foyer 
    public synchronized void enterDepartment(Patient patient) {
        System.out.println(patient.toString() + " enters Foyer.");
        treatedPatients.add(patient);
    }

    // Simulate the process of the patient leaving department
    // e.g., leave foyer and enter triage
    public synchronized void leaveDepartment(Patient patient) {
        System.out.println(patient.toString() + " leaves Foyer.");
        allocatedPatients.remove(patient);
    }
}
