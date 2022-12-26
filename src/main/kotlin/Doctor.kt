private var count: Int = 1
class Doctor (val serviceTime: Int = 3) {
    val id = count++
    override fun toString(): String {
        return "doctor$id"
    }
}