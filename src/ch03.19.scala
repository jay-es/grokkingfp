// 1
def firstTwo(list: List[String]): List[String] = {
  list.slice(0, 2)
}

val abc = List("a", "b", "c")
firstTwo(abc)
assert(firstTwo(abc) == List("a", "b"))

// 2
def lastTwo(list: List[String]): List[String] = {
  list.slice(list.size - 2, list.size)
}

lastTwo(abc)
assert(lastTwo(abc) == List("b", "c"))

// 3
def movedFirstTwoToTheEnd(list: List[String]): List[String] = {
  val firstTwo = list.slice(0, 2)
  val rest = list.slice(2, list.size)
  rest.appendedAll(firstTwo)
}

movedFirstTwoToTheEnd(abc)
assert(movedFirstTwoToTheEnd(abc) == List("c", "a", "b"))

// 4
def insertedBeforeLast(list: List[String], element: String): List[String] = {
  val size = list.size
  val leading = list.slice(0, size - 1)
  val last = list.slice(size - 1, size)
  leading.appended(element).appendedAll(last)
}

insertedBeforeLast(List("a", "b"), "c")
assert(insertedBeforeLast(List("a", "b"), "c") == List("a", "c", "b"))
