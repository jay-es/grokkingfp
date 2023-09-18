def rankedWords(wordScore: String => Int, words: List[String]): List[String] = {
  words.sortBy(wordScore).reverse
}

def score(word: String): Int = word.replaceAll("a", "").length

def bonus(word: String): Int = if (word.contains("c")) 5 else 0

// ここから

def penalty(word: String): Int = if (word.contains("s")) 7 else 0

val list = List("ada", "haskell", "scala", "java", "rust")

val ans1 = rankedWords(score, list)
assert(ans1 == List("haskell", "rust", "scala", "java", "ada"))

val ans2 = rankedWords(w => score(w) + bonus(w), list)
assert(ans2 == List("scala", "haskell", "rust", "java", "ada"))

val ans3 = rankedWords(w => score(w) + bonus(w) - penalty(w), list)
assert(ans3 == List("java", "scala", "ada", "haskell", "rust"))
