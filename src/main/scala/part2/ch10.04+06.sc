import cats.effect.IO
import fs2.Stream

object model {
  opaque type City = String

  object City {
    def apply(name: String): City = name
    extension (city: City) def name: String = city
  }

  case class CityStats(city: City, checkIns: Int)
}
import model._

// 10.4
object ch10_04 {
  val checkIns: Stream[IO, City] = Stream(
    City("Sydney"),
    City("Sydney"),
    City("Cape Town"),
    City("Singapore"),
    City("Cape Town"),
    City("Sydney")
  ).covary[IO]

  def processCheckIns(checkIns: Stream[IO, City]): IO[Unit] =
    checkIns
      .scan(Map(): Map[City, Int])((map, city) =>
        map.updated(city, map.getOrElse(city, 0) + 1)
      )
      .map(
        _.toList
          .sortWith((a, b) => a._2 >= b._2)
          .take(3)
          .map(CityStats.apply)
      )
      // .map(list => IO.pure(println(list)))
      .foreach(IO.println)
      .compile
      .drain

  def main = {
    val res = processCheckIns(checkIns).unsafeRunSync()
    println(res)
  }
}
ch10_04.main

object ch10_06 {
  val checkIns: Stream[IO, City] =
    Stream(
      City("Sydney"),
      City("Dublin"),
      City("Cape Town"),
      City("Lima"),
      City("Singapore")
    )
      .repeatN(100_000)
      .append(Stream.range(0, 100_000).map(i => City(s"City $i")))
      .append(Stream(City("Sydney"), City("Sydney"), City("Lima")))
      .covary[IO]

  def processCheckIns(checkIns: Stream[IO, City]): IO[Unit] =
    checkIns
      .scan(Map(): Map[City, Int])((map, city) =>
        map.updated(city, map.getOrElse(city, 0) + 1)
      )
      .chunkN(100_000) // ここを追加
      .map(_.last) // ここを追加
      .unNone // ここを追加
      .map(
        _.toList
          .map(CityStats.apply)
          .sortBy(_.checkIns)
          .reverse
          .take(3)
      )
      .foreach(IO.println)
      .compile
      .drain

  def main = {
    val res = processCheckIns(checkIns).unsafeRunSync()
    println(res)
  }
}
ch10_06.main
