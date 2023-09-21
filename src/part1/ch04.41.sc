// 1
def fn1(): Int => List[Int] =
  higherThan => List(5, 1, 2, 4, 0).filter(n => n > higherThan);

val ans1 = fn1();
assert(ans1(4) == List(5));
assert(ans1(1) == List(5, 2, 4));

// 1 想定解
def largerThan(n: Int): Int => Boolean = i => i > n;

val ans1a = List(5, 1, 2, 4, 0).filter(largerThan(4));
val ans1b = List(5, 1, 2, 4, 0).filter(largerThan(1));
assert(ans1a == List(5));
assert(ans1b == List(5, 2, 4));

// 2
def canDivideBy(n: Int): Int => Boolean = i => i % n == 0;

val ans2a = List(5, 1, 2, 4, 15).filter(canDivideBy(5));
val ans2b = List(5, 1, 2, 4, 15).filter(canDivideBy(2));
assert(ans2a == List(5, 15));
assert(ans2b == List(2, 4));

// 3
def shorterThan(n: Int): String => Boolean = s => s.length < n;

val ans3a = List("scala", "ada").filter(shorterThan(4));
val ans3b = List("scala", "ada").filter(shorterThan(7));
assert(ans3a == List("ada"));
assert(ans3b == List("scala", "ada"));

// 4
def countOfS(s: String): Int = s.length - s.replaceAll("s", "").length;
def countOfSIsMoreThan(n: Int): String => Boolean = s => countOfS(s) >= n;

val ans4a = List("rust", "ada").filter(countOfSIsMoreThan(3));
val ans4b = List("rust", "ada").filter(countOfSIsMoreThan(1));
assert(ans4a == List());
assert(ans4b == List("rust"));
