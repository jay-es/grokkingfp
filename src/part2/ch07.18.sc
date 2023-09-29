case class User(
    name: String,
    city: Option[String],
    favoriteArtists: List[String]
)

val alice = User("Alice", Some("Melbourne"), List("Bee Gees"))
val bob = User("Bob", Some("Lagos"), List("Bee Gees"))
val eve = User("Eve", Some("Tokyo"), List.empty)
val mallory = User("Mallory", None, List("Metallica", "Bee Gees"))
val trent = User("Trent", Some("Buenos Aires"), List("Led Zeppelin"))
val list = List(alice, bob, eve, mallory, trent)

val ans1 = list.filter(_.city.forall(_ == "Melbourne"))
assert(ans1 == List(alice, mallory))

// val ans2 = list.filter(_.city.exists(_ == "Lagos"))
val ans2 = list.filter(_.city.contains("Lagos"))
assert(ans2 == List(bob))

val ans3 = list.filter(_.favoriteArtists.contains("Bee Gees"))
assert(ans3 == List(alice, bob, mallory))

val ans4 = list.filter(_.city.exists(_.startsWith("T")))
assert(ans4 == List(eve))

val ans5 = list.filter(_.favoriteArtists.forall(_.length > 8))
assert(ans5 == List(eve, trent))

val ans6 = list.filter(_.favoriteArtists.exists(_.startsWith("M")))
assert(ans6 == List(mallory))
