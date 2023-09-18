// 1
val ans1 = List("scala", "rust", "ada").filter(w => w.length < 5)
assert(ans1 == List("rust", "ada"))

// 2
def fn2(word: String): Boolean = {
  (word.length - word.replaceAll("s", "").length) >= 3
}
val ans2 = List("rust", "ada").filter(fn2)
assert(ans2 == List())

// 3
val ans3 = List(5, 1, 2, 4, 0).filter(n => n % 2 != 0)
assert(ans3 == List(5, 1))

// 4
val ans4 = List(5, 1, 2, 4, 0).filter(n => n > 4)
assert(ans4 == List(5))
