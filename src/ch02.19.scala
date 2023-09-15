object TipCalculator {
  def getTipPercentage(names: List[String]): Int = {
    if (names.length > 5) {
      20
    } else if (names.length > 0) {
      10
    } else {
      0
    }
  }
}

val names = List()
TipCalculator.getTipPercentage(names)

val names3 = List("Alice", "Bob", "Charlie")
TipCalculator.getTipPercentage(names3)

val names6 = List("Alice", "Bob", "Charlie", "Daniel", "Emily", "Frank")
TipCalculator.getTipPercentage(names6)
