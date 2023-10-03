object model {
  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension (a: Location) def name: String = a
  }
}

import model._

enum MusicGenre {
  case HeavyMetal
  case Pop
  case HardRock
}

import MusicGenre._

enum YearsActive {
  case StillActive(since: Int)
  case ActiveBetween(start: Int, end: Int)
}

import YearsActive._

case class Artist(
    name: String,
    genre: MusicGenre,
    origin: Location,
    yearsActive: YearsActive
)

// 7.29
def activeLength(artist: Artist, currentYear: Int): Int =
  artist.yearsActive match
    case StillActive(since)        => currentYear - since
    case ActiveBetween(start, end) => end - start

val res1 = activeLength(
  Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1981)),
  2022
)
val res2 = activeLength(
  Artist(
    "Led Zeppelin",
    HardRock,
    Location("England"),
    ActiveBetween(1968, 1980)
  ),
  2022
)
val res3 = activeLength(
  Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003)),
  2022
)

assert(res1 == 41)
assert(res2 == 12)
assert(res3 == 45)
