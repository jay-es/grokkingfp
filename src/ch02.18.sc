object ShoppingCart {
  def getDiscountPercentage(items: List[String]): Int = {
    // if は式
    if (items.contains("Book")) {
      5
    } else {
      0
    }
  }
}

val justApple = List("Apple")
ShoppingCart.getDiscountPercentage(justApple)

val appleAndBook = List("Apple", "Book")
ShoppingCart.getDiscountPercentage(appleAndBook)
