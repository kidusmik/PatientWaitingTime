// A variable to count the number of initialization of this class and works
// as an ID to instantiated classes
private var count: Int = 1
class Doctor (val serviceTime: Int = 3) {
    private val id = count++

    // Override the toString() method to make the representation user/debug friendly
    override fun toString(): String {
        return "doctor$id"
    }
}