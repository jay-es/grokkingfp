// 1
def sum(a: Int, b: Int): Int = a + b;

val ans1 = List(5, 1, 2, 4, 100).foldLeft(0)(sum)
assert(ans1 == 112)

// 2
val ans2 = List("scala", "rust", "ada").foldLeft(0)((sum, s) => sum + s.length);
assert(ans2 == 12)

// 3
def countOfS(s: String): Int = s.length - s.replaceAll("s", "").length;
val ans3 = List("scala", "haskell", "rust", "ada").foldLeft(0)((sum, s) =>
  sum + countOfS(s)
)
assert(ans3 == 3);

// 4
val ans4 = List(5, 1, 2, 4, 15).foldLeft(0)((max, n) =>
  if (max > n) { max }
  else { n }
)
assert(ans4 == 15)

// 4 想定解
val ans4b =
  List(5, 1, 2, 4, 15).foldLeft(Int.MinValue)((max, i) => Math.max(max, i))
assert(ans4b == 15)
