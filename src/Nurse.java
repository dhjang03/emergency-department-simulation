/**
 * Nurse is allocated to one of the patient from foyer and care the patient during the stay in the
 * emergency department. The nurse transfer the patient between the department and acquire/release
 * orderlies for transferring the patient. The nurse also examine the patient in the Triage to 
 * determine if patient needs to be sent to Treatment or return to Foyer.
 * 
 * @author Dong Hyeog Jang
 * @date   11 March 2024
 * 
 */
public class Nurse extends Thread {

    private int id;
    private Foyer foyer;
    private Triage triage;
    private Orderlies orderlies;
    private Treatment treatment;

    // Constructor for Nurse class
    public Nurse(int i, Foyer foyer, Triage triage, Orderlies orderlies, Treatment treatment) {
        this.id = i;
        this.foyer = foyer;
        this.triage = triage;
        this.orderlies = orderlies;
        this.treatment = treatment;
    }

    public void run() {
        while (!isInterrupted()) {

            // get patient from Foyer.
            Patient patient = getPatient();

            // if not succesful in getting the patient then continue to next loop.
            if (patient == null) continue;

            System.out.println(patient.toString() + " allocated to " + this.toString()  + ".");

            // transfer the patient from Foyer to Triage.
            transferPatient(patient, foyer, triage);

            // examine the patient in the Triage.
            boolean isSevere = examinePatient(patient);

            // if patient is severe then transfer the patient to Treatment for treatment otherwise
            // transfer the patient back to foyer.
            if (isSevere) {

                // transfer the patient from Triage to Treatment
                transferPatient(patient, triage, treatment);

                // start treatment from the Treatment
                treatment.startTreatment();

                // transfer the patient from Treatment to Foyer
                transferPatient(patient, treatment, foyer);
            } else {

                // transfer the patient from Triage to Foyer
                transferPatient(patient, triage, foyer);
            } 

            System.out.println(this.toString() + " releases " + patient.toString());
        }
    }

    // get Patient from Foyer
    private Patient getPatient() {
        Patient p = foyer.getNextPatient();
        return p;
    }

    // examine the patient for severity and return if the patient is severe
    private boolean examinePatient(Patient p) {

        // sleep for the duration of TRIAGE_TIME which represent time it takes to examine the patient.
        try {
            sleep(Params.TRIAGE_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return p.Severe();
    }

    // acquire orderlies from Orderlies
    private void acquireOrderlies() {
        int orderliesLeft = orderlies.getOrderlies();
        logOrderlies("recruits", orderliesLeft);
    }

    // release orderlies back to Orderlies
    private void releaseOrderlies() {
        int orderliesLeft = orderlies.returnOrderlies();
        logOrderlies("releases", orderliesLeft);
    }

    // print the relevent message regarding recruiting or relasing orderlies
    private void logOrderlies(String action, int orderliesLeft) {
        System.out.println(
            String.format(
                "%s %s %d orderlies (%d).",
                this.toString(),
                action,
                Params.TRANSFER_ORDERLIES,
                orderliesLeft
            )
        );
    }

    // transfer patient from on department to another department
    private void transferPatient(Patient patient, Department from, Department to) {
        
        acquireOrderlies();

        // leave from specified department.
        from.leaveDepartment(patient);

        // sleep for duration of tranfer time
        try {
            sleep(Params.TRANSFER_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // enter specified department
        to.enterDepartment(patient);

        releaseOrderlies();
    }

    // produce identifying sting for the nurse.
    public String toString() {
    	String s = "Nurse " + id;
        return s;
    }
    
}
