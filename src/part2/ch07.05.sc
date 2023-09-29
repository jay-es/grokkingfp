case class Artist(
    name: String,
    genre: String,
    origin: String,
    yearsActiveStart: Int,
    isActive: Boolean,
    yearsActiveEnd: Int
)

val artists = List(
  Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0),
  Artist("Led Zeppelin", "Hard Rock", "England", 1968, false, 1980),
  Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
)

// 7.5
def searchArtists(
    artists: List[Artist],
    genres: List[String],
    locations: List[String],
    searchByActiveYears: Boolean,
    activeAfter: Int,
    activeBefore: Int
): List[Artist] =
  artists
    .filter(artist =>
      if (genres.isEmpty) true else genres.contains(artist.genre)
    )
    .filter(artist =>
      if (locations.isEmpty) true else locations.contains(artist.origin)
    )
    .filter(artist =>
      if (searchByActiveYears)
        (artist.yearsActiveStart <= activeBefore)
        && (artist.yearsActiveEnd >= activeAfter || artist.isActive)
      else true
    )
  // 想定解
  // .filter(artist =>
  //   (genres.isEmpty || genres.contains(artist.genre)) &&
  //     (locations.isEmpty || locations.contains(artist.origin)) && (
  //       !searchByActiveYears || ((artist.isActive ||
  //         artist.yearsActiveEnd >= activeAfter) &&
  //         (artist.yearsActiveStart <= activeBefore))
  //     )
  // )

val res1 =
  searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022)
val ans1 = List(Artist("Bee Gees", "Pop", "England", 1958, false, 2003))

val res2 = searchArtists(artists, List.empty, List("England"), true, 1950, 2022)
val ans2 = List(
  Artist("Led Zeppelin", "Hard Rock", "England", 1968, false, 1980),
  Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
)

val res3 = searchArtists(artists, List.empty, List.empty, true, 1981, 2003)
val ans3 = List(
  Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0),
  Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
)

val res4 = searchArtists(artists, List.empty, List("U.S."), false, 0, 0)
val ans4 = List(Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0))

val res5 = searchArtists(artists, List.empty, List.empty, false, 2019, 2022)
val ans5 = List(
  Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0),
  Artist("Led Zeppelin", "Hard Rock", "England", 1968, false, 1980),
  Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
)

assert(res1 == ans1)
assert(res2 == ans2)
assert(res3 == ans3)
assert(res4 == ans4)
assert(res5 == ans5)
