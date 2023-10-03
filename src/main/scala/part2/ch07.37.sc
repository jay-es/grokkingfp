object oldModel {
  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension (a: Location) def name: String = a
  }

  enum MusicGenre {
    case HeavyMetal
    case Pop
    case HardRock
  }

  enum YearsActive {
    case StillActive(since: Int)
    case ActiveBetween(start: Int, end: Int)
  }

  enum SearchCondition {
    case SearchByGenre(genres: List[MusicGenre])
    case SearchByOrigin(locations: List[Location])
    case SearchByActiveYears(start: Int, end: Int)
  }

  case class Artist(
      name: String,
      genre: MusicGenre,
      origin: Location,
      yearsActive: YearsActive
  )
}

// 7.37
object model {
  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension (a: Location) def name: String = a
  }

  enum MusicGenre {
    case HeavyMetal
    case Pop
    case HardRock
  }

  case class Period(start: Int, end: Int)

  enum YearsActive {
    case StillActive(since: Int, pastPeriods: List[Period])
    case ActiveBetween(periods: List[Period])
  }

  enum SearchCondition {
    case SearchByGenre(genres: List[MusicGenre])
    case SearchByOrigin(locations: List[Location])
    case SearchByActiveYears(start: Int, end: Int)
    case SearchByActiveLength(minYears: Int, currentYear: Int)
  }

  case class Artist(
      name: String,
      genre: MusicGenre,
      origin: Location,
      yearsActive: YearsActive
  )
}

import model._, MusicGenre._, YearsActive._, SearchCondition._

def wasActive(
    periods: List[Period],
    searchStart: Int,
    searchEnd: Int
): Boolean =
  periods.exists(p => (p.start <= searchEnd) && (p.end >= searchStart))

def sumPeriods(periods: List[Period]): Int =
  periods.foldLeft(0)((sum, p) => sum + p.end - p.start)

def searchArtists(
    artists: List[Artist],
    requiredConditions: List[SearchCondition]
): List[Artist] =
  artists.filter(artist =>
    requiredConditions.forall(cond =>
      cond match
        case SearchByGenre(genres)     => genres.contains(artist.genre)
        case SearchByOrigin(locations) => locations.contains((artist.origin))
        case SearchByActiveYears(start, end) => {
          artist.yearsActive match
            case StillActive(since, pastPeriods) =>
              (since <= end) || wasActive(pastPeriods, start, end)
            case ActiveBetween(periods) => wasActive(periods, start, end)
        }
        case SearchByActiveLength(minYears, currentYear) => {
          val activeLength = artist.yearsActive match
            case StillActive(since, pastPeriods) =>
              (currentYear - since) + sumPeriods(pastPeriods)
            case ActiveBetween(periods) => sumPeriods(periods)
          activeLength > minYears
        }
    )
  )

val metallica = Artist(
  "Metallica",
  HeavyMetal,
  Location("U.S."),
  StillActive(1981, List.empty)
)
val ledZeppelin = Artist(
  "Led Zeppelin",
  HardRock,
  Location("England"),
  ActiveBetween(List(Period(1968, 1980)))
)
val beeGees = Artist(
  "Bee Gees",
  Pop,
  Location("England"),
  ActiveBetween(List(Period(1958, 2003), Period(2009, 2012)))
)

val artists = List(metallica, ledZeppelin, beeGees)

val res1 =
  searchArtists(artists, List(SearchByGenre(List(HeavyMetal, HardRock))))
assert(res1 == List(metallica, ledZeppelin))

val res2 =
  searchArtists(artists, List(SearchByOrigin(List(Location("England")))))
assert(res2 == List(ledZeppelin, beeGees))

val res3 = searchArtists(artists, List(SearchByActiveYears(1950, 2022)))
assert(res3 == List(metallica, ledZeppelin, beeGees))

val res4 = searchArtists(artists, List(SearchByActiveYears(2010, 2018)))
assert(res4 == List(metallica, beeGees))

val res5 = searchArtists(artists, List(SearchByActiveLength(45, 2022)))
assert(res5 == List(beeGees))

val res6 =
  searchArtists(
    artists,
    List(
      SearchByGenre(List(HeavyMetal, HardRock)),
      SearchByOrigin(List(Location("England")))
    )
  )
assert(res6 == List(ledZeppelin))
