/**
 * Treatment is one type of department in emergency department where sever patient come in to be
 * treated by a specialist.
 * 
 * @author Dong Hyeog Jang
 * @date   11 March 20204
 * 
 */
public class Treatment implements Department {
    
    // represents current patient who are in Treatment
    private volatile Patient currentPatient = null;

    // a patient enters Treatment
    public synchronized void enterDepartment(Patient patient) {

        // wait until Treatment is emptied if currently occupied by another patient.
        while (currentPatient != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // update currentPatient to patient who just arrive at the Treatment
        System.out.println(patient.toString() + " enters treatment.");
        currentPatient = patient;

        // notify all the threads waiting at the Treatment.
        notifyAll();
    }

    // a patient leaves Treatment
    public synchronized void leaveDepartment(Patient patient) {

        // update the currentPatient to null as the patient leaves the Treatment.
        System.out.println(currentPatient.toString() + " leaves treatment.");
        currentPatient = null;

        // notify all the threads wating at the Treatment.
        notifyAll();
    }

    // start treatment on the patient
    public synchronized void startTreatment() {

        // wait until the patient is treated by a specialist
        while (!currentPatient.treated) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // returns current patients who need treatment from the specialist
    public synchronized Patient getCurrentPatient() {
        
        // wait until new patients enter the treatment or untreated patients is present.
        while (currentPatient == null || currentPatient.treated) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return currentPatient;
    }
}
