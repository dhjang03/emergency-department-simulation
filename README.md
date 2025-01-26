# Emergency Department Simulation

## Introduction

This project is part of SWEN90004 Assessment which simulates the operations of a hospital emergency department (ED) using concurrent programming concepts in Java. The simulation models the flow of patients through the ED, including their interactions with nurses, orderlies, and a specialist, while adhering to constraints that ensure safety and liveness.

The primary goal of this project is to implement a realistic simulation model of an ED, identify potential issues with its operation, and explore system behavior under different configurations.

## Background

Emergency departments handle urgent medical cases and manage the flow of patients through multiple stages, such as admission, triage, treatment, and discharge. This project involves simulating such a system while adhering to specific rules and constraints.

Key components of the system include:

- Patients: Arrive at the ED and follow a specific pathway based on the severity of their condition.
- Nurses: Assist patients through the ED process, from admission to discharge.
- Orderlies: Support nurses during patient transfers between locations.
- Specialist: Provides treatment to severe cases but also has duties outside the ED.

The simulation is designed to explore the impact of parameters like the number of nurses and orderlies and the timing of various activities.

## System Description

The ED simulation operates based on the following workflow:

- Admission: Patients arrive at the ED and are allocated to a nurse.
- Triage: Patients are assessed to determine the severity of their condition.
- Treatment: Severe cases are treated by the specialist, while non-severe cases are discharged after triage.
- Discharge: All patients are eventually discharged from the ED.

### Design

The simulation utilizes:

- Java Threads: To model active processes like nurses, the specialist, and patients.
- Monitors: To represent ED locations like the foyer, triage, and treatment room.
- Shared Resources: Orderlies are used as a shared pool to assist with patient transfers.

The program is parameterized to allow:

- Variations in the number of nurses and orderlies.
- Customizable time intervals for patient flow and specialist availability.
- Adjustable requirements for orderly assistance.


## How To Run

1. Clone the repository
```bash
git clone https://github.com/dhjang03/emergency-department-simulation.git
```

2. Compile the code
```bash
javac -d bin src/*.java
```

3. Run the Simulator:
```bash
java -cp bin Main
```

## Configuring Parameters

Modify the Params.java file to adjust system settings such as:

- Number of nurses and orderlies.
- Number of orderlies required for transfers.
- Timing intervals for patient processing.

## Output

The program produces a trace of events that detail the flow of patients through the ED. For example:

- Patient admissions, transfers, and discharges.
- Specialist activity.
- Orderly resource usage.

### Sample Output Trace
```
Specialist enters treatment room.
Patient 1 (S) admitted to ED.
Patient 1 (S) allocated to Nurse 1.
Nurse 1 recruits 3 orderlies (5).
Patient 1 (S) leaves Foyer.
Patient 1 (S) enters triage.
Nurse 1 releases 3 orderlies (8).
Nurse 1 recruits 3 orderlies (5).
Patient 1 (S) leaves triage.
Patient 1 (S) enters treatment.
Nurse 1 releases 3 orderlies (8).
Patient 1 (S) treatment started.
Patient 1 (S) treatment complete.
Specialist leaves treatment room.
Nurse 1 recruits 3 orderlies (5).
Patient 1 (S) leaves treatment.
Patient 1 (S) enters Foyer.
Nurse 1 releases 3 orderlies (8).
Nurse 1 releases Patient 1 (S)
Patient 2 admitted to ED.
Patient 2 allocated to Nurse 2.
Nurse 2 recruits 3 orderlies (5).
Patient 2 leaves Foyer.
Patient 2 enters triage.
Nurse 2 releases 3 orderlies (8).
Patient 1 (S) discharged from ED.
Specialist enters treatment room.
Nurse 2 recruits 3 orderlies (5).
```