case class Book(title: String, authors: List[String])

def recommendedBooks(friend: String): List[Book] = {
  val scala =
    List(
      Book("FP in Scala", List("Chiusano", "Bjarnason")),
      Book("Get Programming with Scala", List("Sfregola"))
    )

  val fiction = List(
    Book("Harry Potter", List("Rowling")),
    Book("The Lord of the Rings", List("Tolkien"))
  )

  if (friend == "Alice") scala
  else if (friend == "Bob") fiction
  else List.empty
}

val friends = List("Alice", "Bob", "Charlie")

// 1
val recommendations = friends.flatMap(recommendedBooks(_))

assert(
  recommendations == List(
    Book("FP in Scala", List("Chiusano", "Bjarnason")),
    Book("Get Programming with Scala", List("Sfregola")),
    Book("Harry Potter", List("Rowling")),
    Book("The Lord of the Rings", List("Tolkien"))
  )
);

// 2
val authors = recommendations.flatMap(_.authors)

assert(
  authors == List("Chiusano", "Bjarnason", "Sfregola", "Rowling", "Tolkien")
)

// 3
val authors2 = friends.flatMap(recommendedBooks).flatMap(_.authors)

assert(authors2 == authors)
