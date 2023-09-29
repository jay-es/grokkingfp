case class Artist(name: String)
case class User(name: String)
case class Song(name: String, artist: Artist)

enum MusicGenre {
  case Funk
  case House
  case HeavyMetal
}

enum PlaylistKind {
  case UserCreated(user: User)
  case ArtistBased(artist: Artist)
  case Genres(genres: List[MusicGenre])
}

case class Playlist(
    name: String,
    kind: PlaylistKind,
    songs: List[Song]
)

import MusicGenre._, PlaylistKind._

val ff = Artist("Foo Fighters")
val ffList = Playlist(
  "This is Foo Fighters",
  ArtistBased(ff),
  List(
    Song("Breakout", ff),
    Song("Learn To Fly", ff)
  )
)
val dfList = Playlist(
  "Deep Focus",
  Genres(List(Funk, House)),
  List(
    Song("One More Time", Artist("Daft Punk")),
    Song("Hey Boy Hey Girl", Artist("Chemical Brothers"))
  )
)
val myList = Playlist(
  "My Playlist",
  UserCreated(User("jay-es")),
  List(
    Song("The Saddest Song", Artist("The Ataris")),
    Song("It's Over Now", Artist("Neve")),
    Song("Monkey Wrench", ff)
  )
)

// 2
def gatherSongs(
    playlists: List[Playlist],
    artist: Artist,
    genre: MusicGenre
): List[Song] = playlists.flatMap(playlist =>
  playlist.kind match
    case UserCreated(_) => playlist.songs.filter(_.artist == artist)
    case ArtistBased(playlistArtist) =>
      if (playlistArtist == artist) playlist.songs else List.empty
    case Genres(genres) =>
      if (genres.contains(genre)) playlist.songs else List.empty
)

def songNames(songs: List[Song]): List[String] = songs.map(_.name)

val res1 = gatherSongs(List(ffList, myList), ff, House)
assert(
  songNames(res1) == List("Breakout", "Learn To Fly", "Monkey Wrench")
)

val res2 = gatherSongs(List(ffList, dfList, myList), Artist("Neve"), House)
assert(
  songNames(res2) == List("One More Time", "Hey Boy Hey Girl", "It's Over Now")
)

val res3 =
  gatherSongs(List(ffList, dfList, myList), Artist("Metallica"), HeavyMetal)
assert(res3 == List.empty)
