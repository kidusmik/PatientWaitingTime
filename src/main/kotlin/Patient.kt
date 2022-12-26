private var count: Int = 1
class Patient (val preferredDoctor: Doctor? = null) {
    val id = count++

    override fun toString(): String {
        return "patient$id"
    }
}