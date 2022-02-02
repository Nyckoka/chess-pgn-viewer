


/**
 * Represents a chess game in PGN format (Portable Game Notation).
 * 
 * [https://en.wikipedia.org/wiki/Portable_Game_Notation]
 */
data class PGN(val pgnInList: List<String>) {
    private val hashmap = HashMap<String, String>()

    operator fun get(string: String): String? {
        return hashmap[string]
    }

    val moves: String
        get() = hashmap["Moves"]!!
    
    constructor(pgnInString: String) : this(pgnInString.split("\\n").filter { it.isNotBlank() })

    init {
        fun String.substringBetweenQuotationMarks() = substringAfter("\"").substringBeforeLast("\"")

        var currentTagCount = 0
        
        pgnInList.filter { it.isNotBlank() }.forEach {
            val value = it.substringBetweenQuotationMarks()

            if (it.first() == '[') {
                val tag = it.substringAfter("[").substringBefore(" ")
                
                require(when(currentTagCount++){
                    0 -> tag == "Event"
                    1 -> tag == "Site"
                    2 -> tag == "Date"
                    3 -> tag == "Round"
                    4 -> tag == "White"
                    5 -> tag == "Black"
                    6 -> tag == "Result"
                    else -> true
                }) {
                    "Seven Tag Roster not respected."
                }
                
                hashmap[it.substringAfter("[").substringBefore(" ")] = value
            } else {
                hashmap["Moves"] = it
            }
        }
    }
    
    
    override fun toString(): String {
        return hashmap.toString()
    }
}
