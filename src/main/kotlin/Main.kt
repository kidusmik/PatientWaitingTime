fun main() {
    // Declare all the available Doctors
    val doctor1 = Doctor()  // A Doctor with a default service time of 3 minutes
    val doctor2 = Doctor(5) // A Doctor with service time of 5 minutes
    // Declare all the Patients
    // 8 patients: with their preferred doctor if they have any
    val patient1 = Patient(doctor1)  // patient1 prefers doctor1
    val patient2 = Patient(doctor2)  // patient2 prefers doctor2
    val patient3 = Patient()  // patient3 has no preference
    val patient4 = Patient(doctor1)
    val patient5 = Patient(doctor1)
    val patient6 = Patient()
    val patient7 = Patient(doctor2)
    val patient8 = Patient()
    // Construct a List of all available Doctors
    val doctorsAvailable = mutableListOf(doctor1, doctor2)
    // Construct the Patient Queue which is also a List
    val patientQueue = mutableListOf(
        patient1, patient2, patient3, patient4,
        patient5, patient6, patient7, patient8
    )
    // Here, patient8 is the first parameter of this function since I am trying to figure
    // out this specific patient's waiting time
    val (waitingTime, requestingPatient, awaitingDoctor) = calculatePatientWaitingTime(patient8, patientQueue, doctorsAvailable)

    println("Patient name: $requestingPatient\nMinimum waiting time: $waitingTime minutes\nWill be visiting: $awaitingDoctor")
}

/*
 * calculatePatientWaitingTime - Calculates a specified patient's waiting
 * time to see a Doctor
 *
 * @requestingPatient: The patient that is requesting their waiting time
 * @patientQueue: The List that symbolizes the queue of the Patients
 * @doctorsAvailable: The List that contains available Doctors
 *
 * Returns a Triple which contains:
 * - requestingPatient: The patient that is requesting their waiting time
 * - waitingTime: The waiting time of the requesting patient
 * - selectedDoctor: The Doctor that the requesting patient has been allocated to
 */
fun calculatePatientWaitingTime(
    requestingPatient: Patient,
    patientQueue: List<Patient>,
    doctorsAvailable: MutableList<Doctor>
): Triple<Int?, Patient, Doctor> {
    // Construct a List that records each Doctor's elapsed time
    // This will be an Int List initialized with zeroes
    val timeRecord = MutableList(doctorsAvailable.size) { 0 }
    // This will create a Map that maps each available Doctor
    // to their respective time elapse record
    val doctorTimeMap = mutableMapOf<Doctor, Int>()
        .apply {
            for (i in doctorsAvailable.indices) this[doctorsAvailable[i]] = timeRecord[i]
        }

    for (patient in patientQueue) {
        if (patient == requestingPatient)
            // If the patient requesting their waiting time is already in the queue then break
            // the loop since there is no need to continue calculating the rest of the patient's time
            break
        if (patient.preferredDoctor != null)
            // If the patient in queue prefers a specific Doctor, then allocate this patient
            // to its specified preferred Doctor. In technical terms, increment that specific
            // Doctor's time record.
            doctorTimeMap.merge(patient.preferredDoctor, patient.preferredDoctor.serviceTime, Int::plus)
        else {
            // Otherwise, if the patient in queue has no specific doctor preference, then
            // allocate this patient to any of the available Doctors who are not currently
            // occupied. In technical terms, find the Doctor with the smallest time record
            // and increment his time record.
            val minTime = doctorTimeMap.values.min()
            val minTimeDoctor = doctorTimeMap.filter { minTime == it.value }.keys.first()
            doctorTimeMap.merge(minTimeDoctor, minTimeDoctor.serviceTime, Int::plus)
        }
    }

    val waitingTime: Int? = if (requestingPatient.preferredDoctor != null)
        // If the patient who is requesting his waiting time prefers a specific Doctor
        // then their waiting time will be their specified Doctor's time record.
        doctorTimeMap[requestingPatient.preferredDoctor]
    else
        // Otherwise, if the patient who is requesting their waiting time has no preference
        // then their waiting time is the time of the Doctor with the smallest time record.
        doctorTimeMap.values.min()
    // Get the Doctor with that specific time (waitingTime)
    val selectedDoctor = doctorTimeMap.filter { waitingTime == it.value }.keys.first()

    return Triple(waitingTime, requestingPatient, selectedDoctor)
}
