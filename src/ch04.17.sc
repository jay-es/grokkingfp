// 1
def sortByLength(list: List[String]): List[String] = {
  return list.sortBy(w => w.length)
}

val ans1 = sortByLength(List("scala", "rust", "ada"));
assert(ans1 == List("ada", "rust", "scala"));

// 2
def countOfS(s: String): Int = s.length - s.replaceAll("s", "").length;

val ans2 = List("rust", "ada").sortBy(countOfS);
assert(ans2 == List("ada", "rust"))

// 3
def negate(n: Int): Int = -n;

val ans3 = List(5, 1, 2, 4, 3).sortBy(negate);
assert(ans3 == List(5, 4, 3, 2, 1));

// 4
def negateCountOfS(s: String): Int = -countOfS(s);

val ans4 = List("ada", "rust").sortBy(negateCountOfS);
assert(ans4 == List("rust", "ada"));
