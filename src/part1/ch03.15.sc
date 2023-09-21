def abbreviate(name: String): String = {
  val initial = name.substring(0, 1)
  val sepIndex = name.indexOf(" ")
  val lastName = name.substring(sepIndex + 1)
  initial + ". " + lastName
}

// test

abbreviate("Alonzo Church")
assert(abbreviate("Alonzo Church") == "A. Church")

abbreviate("A. Church")
assert(abbreviate("A. Church") == "A. Church")

abbreviate("A Church")
assert(abbreviate("A Church") == "A. Church")
