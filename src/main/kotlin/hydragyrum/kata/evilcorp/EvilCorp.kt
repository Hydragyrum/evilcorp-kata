package hydragyrum.kata.evilcorp

class EvilCorp(
    private val replacementMap: Map<String, String>
) {
    constructor(
        blacklist: List<String>,
        replacementMap: Map<String, String>
    ) : this(blacklist.map { it to "X".repeat(it.length) }.toMap() + replacementMap)

    constructor(blacklist: List<String>) : this(blacklist.map { it to "X".repeat(it.length) }.toMap())
    constructor(bannedWord: String) : this(listOf(bannedWord))

    fun censor(text: String): String {
        val censored = text.split(" ")
            .joinToString(" ") {
                val word = removePunctuation(it)
                if (replacementMap.any { (key, _) -> word.toLowerCase().contains(key) }) {
                    (replacementMap[word.toLowerCase()] ?: "X".repeat(word.length)) + getPunctuation(it)
                } else {
                    it
                }
            }
        return if (text[0].isUpperCase()) censored.capitalize() else censored
    }

    private fun getPunctuation(word: String): String =
        if (word.matches("""[A-Za-z]+[,.]""".toRegex())) {
            word.last().toString()
        } else {
            ""
        }

    private fun removePunctuation(word: String) =
        if (word.matches("""[A-Za-z]+[,.]""".toRegex())) {
            word.substring(0 until word.length - 1)
        } else {
            word
        }
}
