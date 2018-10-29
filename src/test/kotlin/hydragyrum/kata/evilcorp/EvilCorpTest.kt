package hydragyrum.kata.evilcorp

import io.kotlintest.specs.BehaviorSpec
import org.amshove.kluent.`should be equal to`

class EvilCorpTest : BehaviorSpec({
    Given("An EvilCorp object with a single word blacklist") {
        val blacklist = "nice"
        val evil = EvilCorp(blacklist)
        When("We censor a sentence") {
            val result = evil.censor("You are a nice person")
            Then("The word 'nice' is censored") {
                result `should be equal to` "You are a XXXX person"
            }
        }
    }
    Given("An EvilCorp object with multiple blacklisted words") {
        val blacklist = listOf("nice", "pony", "sun", "light", "fun", "happy", "funny", "joy", "others", "friend")
        val evil = EvilCorp(blacklist)
        When("We censor a sentence") {
            val result = evil.censor("Such a nice day with a bright sun, makes me happy")
            Then("All words on the blacklist are censored") {
                result `should be equal to` "Such a XXXX day with a bright XXX, makes me XXXXX"
            }
        }
        When("We censor a new sentence") {
            val result = evil.censor("You are so friendly!")
            Then("The entire word which contains the banned word is censored.") {
                result `should be equal to` "You are so XXXXXXXXX"
            }
        }
    }
    Given("An EvilCorp object with replacements defined") {
        val blacklist = listOf("nice", "pony", "sun", "light", "fun", "happy", "funny", "joy", "others", "friend")
        val replacements = mapOf(
            "bad" to "ungood",
            "better" to "gooder",
            "objection" to "thoughtcrime",
            "agree" to "crimestop"
        )
        val evil = EvilCorp(blacklist, replacements)
        When("We modify a sentence") {
            val result = evil.censor("Objection is bad, a better thing to do, is to agree.")
            Then("The bad words are properly replaced") {
                result `should be equal to` "Thoughtcrime is ungood, a gooder thing to do, is to crimestop."
            }
        }
    }
})