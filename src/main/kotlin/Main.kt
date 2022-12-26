fun main() {
    val doctor1 = Doctor()
    val doctor2 = Doctor(5)

    val patient1 = Patient(doctor1)
    val patient2 = Patient(doctor2)
    val patient3 = Patient()
    val patient4 = Patient(doctor1)
    val patient5 = Patient(doctor1)
    val patient6 = Patient()
    val patient7 = Patient(doctor2)
    val patient8 = Patient()

    val patientQueue = mutableListOf(
        patient1, patient2, patient3, patient4,
        patient5, patient6, patient7, patient8
    )

    val doctorsAvailable = mutableListOf(doctor1, doctor2)
    val timeRecord = MutableList(doctorsAvailable.size) { 0 }

    val doctorTimeMap = mutableMapOf<Doctor, Int>()
        .apply { for (i in doctorsAvailable.indices) this[doctorsAvailable[i]] = timeRecord[i] }

    val (waitingTime, requestingPatient, awaitingDoctor) = calculatePatientWaitingTime(patient8, patientQueue, doctorTimeMap)

    println("Patient name: $requestingPatient\nMinimum waiting time: $waitingTime minutes\nWill be visiting: $awaitingDoctor")

}

//fun calculatePatientWaitingTime(
//    requestingPatient: Patient,
//    patientQueue: List<Patient>,
//    doctorTimeMap: MutableMap<Doctor, Int>
//): Int? {
//    for (patient in patientQueue) {
//        if (patient == requestingPatient)
//            break
//        if (patient.preferredDoctor != null)
//            doctorTimeMap.merge(patient.preferredDoctor, patient.preferredDoctor.serviceTime, Int::plus)
//        else {
//            val minTime = doctorTimeMap.values.min()
//            val minDoctor = doctorTimeMap.filter { minTime == it.value }.keys.first()
//            doctorTimeMap.merge(minDoctor, minDoctor.serviceTime, Int::plus)
//        }
//    }
//
//    println("Doctor Time Map: $doctorTimeMap")
//
//    return if (requestingPatient.preferredDoctor != null)
//        doctorTimeMap[requestingPatient.preferredDoctor]
//    else
//        doctorTimeMap.values.min()
//}
fun calculatePatientWaitingTime(
    requestingPatient: Patient,
    patientQueue: List<Patient>,
    doctorTimeMap: MutableMap<Doctor, Int>
): Triple<Int?, Patient, Doctor> {

    for (patient in patientQueue) {
        if (patient == requestingPatient)
            break
        if (patient.preferredDoctor != null)
            doctorTimeMap.merge(patient.preferredDoctor, patient.preferredDoctor.serviceTime, Int::plus)
        else {
            val minTime = doctorTimeMap.values.min()
            val minDoctor = doctorTimeMap.filter { minTime == it.value }.keys.first()
            doctorTimeMap.merge(minDoctor, minDoctor.serviceTime, Int::plus)
        }
    }

    println("Time records: $doctorTimeMap")

    val waitingTime: Int? = if (requestingPatient.preferredDoctor != null)
        doctorTimeMap[requestingPatient.preferredDoctor]
    else
        doctorTimeMap.values.min()

    val selectedDoctor = doctorTimeMap.filter { waitingTime == it.value }.keys.first()

    return Triple(waitingTime, requestingPatient, selectedDoctor)
}
