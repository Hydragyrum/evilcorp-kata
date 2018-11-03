package hydragyrum.kata.evilcorp

import io.kotlintest.specs.BehaviorSpec
import org.amshove.kluent.`should be equal to`

class EvilCorpTest : BehaviorSpec({
    Given("An EvilCorp object with a single word blacklist") {
        val blacklist = "nice"
        val evil = generateEvil(blacklist)
        When("We censor a sentence") {
            val result = evil.censor("You are a nice person")
            Then("The word 'nice' is censored") {
                result `should be equal to` "You are a XXXX person"
            }
        }
    }
    Given("A new EvilCorp object with a different blacklist") {
        val blacklist = "fun"
        val evil = generateEvil(blacklist)
        When("We censor a sentence") {
            val result = evil.censor("You are having too much fun, stop having fun.")
            Then("The word 'fun' is censored") {
                result `should be equal to` "You are having too much XXX, stop having XXX."
            }
        }
    }
    Given("An EvilCorp object with multiple blacklisted words") {
        val blacklist = listOf("nice", "pony", "ponies", "sun", "light", "fun", "happy", "funny", "joy", "others", "friend")
        val evil = generateEvil(blacklist)
        When("We censor a sentence") {
            val result = evil.censor("Such a nice day with a bright sun, makes me happy")
            Then("All words on the blacklist are censored") {
                result `should be equal to` "Such a XXXX day with a bright XXX, makes me XXXXX"
            }
        }
        When("We censor a different sentence") {
            val result = evil.censor("Ponies are nice, ponies are fun. I like ponies, how about you?")
            Then("All words in the blacklist are censored as well") {
                result `should be equal to` "XXXXXX are XXXX, XXXXXX are XXX. I like XXXXXX, how about you?"
            }
        }
        When("We censor a new sentence") {
            val result = evil.censor("You are so friendly!")
            Then("The entire word which contains the banned word is censored.") {
                result `should be equal to` "You are so XXXXXXXXX"
            }
        }
        When("We censor a new sentence with words prefixed by banned words") {
            val result = evil.censor("The joyful nature of the pony is fundamentally flawed.")
            Then("Most of the sentence is blanked out.") {
                result `should be equal to` "The XXXXXX nature of the XXXX is XXXXXXXXXXXXX flawed."
            }
        }
        When("We ask to censor a sentence with no banned words") {
            val result = evil.censor("The bad man was very unfriendly.")
            Then("Nothing is blanked out") {
                result `should be equal to` "The bad man was very unfriendly."
            }
        }
    }
    Given("An EvilCorp object with replacements defined") {
        val blacklist = listOf("nice", "pony", "sun", "light", "fun", "happy", "funny", "joy", "others", "friend")
        val replacements = mapOf(
            "bad" to "ungood",
            "better" to "plusgood",
            "objection" to "thoughtcrime",
            "agree" to "crimestop"
        )
        val evil = generateEvil(blacklist, replacements)
        When("We modify a sentence") {
            val result = evil.censor("Objection is bad, a better thing to do, is to agree.")
            Then("The bad words are properly replaced") {
                result `should be equal to` "Thoughtcrime is ungood, a plusgood thing to do, is to crimestop."
            }
        }
    }
    Given("An EvilCorp object with new replacements") {
        val blacklist = listOf("nice", "pony", "sun", "light", "fun", "happy", "funny", "joy", "others", "friend")
        val replacements = mapOf(
            "bad" to "ungood",
            "worst" to "doubleplusungood",
            "objectors" to "thoughtcrimeful persons"
        )
        val evil = generateEvil(blacklist, replacements)
        When("We apply the rules to the sentence") {
            val result = evil.censor("The worst objectors are often friendly and nice.")
            Then("The sentence is properly modified") {
                result `should be equal to` "The doubleplusungood thoughtcrimeful persons are often XXXXXXXX and XXXX."
            }
        }
    }
})

private fun generateEvil(
    blacklist: List<String>,
    replacements: Map<String, String>
) = EvilCorp(blacklist, replacements)

private fun generateEvil(blacklist: List<String>) =
    EvilCorp(blacklist)

private fun generateEvil(blacklist: String) = EvilCorp(blacklist)