// A variable to count the number of initialization of this class and works
// as an ID to instantiated classes
private var count: Int = 1
class Patient (val preferredDoctor: Doctor? = null) {
    private val id = count++

    // Override the toString() method to make the representation user/debug friendly
    override fun toString(): String {
        return "patient$id"
    }
}