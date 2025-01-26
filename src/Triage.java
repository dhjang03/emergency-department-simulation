/**
 * Triage is a department where a patient is examined by a nurse.
 * 
 * @author Dong Hyeog Jang
 * @date   11 March 2024
 * 
 */
public class Triage implements Department {
    
    // Represent current patient who are in Triage
    private Patient currentPatient = null;

    // A patient enters Triage
    public synchronized void enterDepartment(Patient patient) {
        
        // wait if the Triage is current occupied by other patient.
        while (currentPatient != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // update currentPatient to a patient who just entered the tirage
        System.out.println(patient.toString() + " enters triage.");
        currentPatient = patient;
    }

    // Current patient leaves Triage
    public synchronized void leaveDepartment(Patient patient) {

        // update currentPatient to null as the patient left the triage
        System.out.println(currentPatient.toString() + " leaves triage.");
        currentPatient = null;

        // notify a thread waiting at enterDepartment.
        notify();
    }
}
