import cats.effect.{IO, Ref}
import cats.implicits.catsSyntaxParallelSequence1 // parSequence
import fs2.Stream
import scala.concurrent.duration._

object model {
  opaque type City = String

  object City {
    def apply(name: String): City = name
    extension (city: City) def name: String = city
  }

  case class CityStats(city: City, checkIns: Int)
}
import model._

object ch10_21 {
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

  def topCities(cityCheckIns: Map[City, Int]): List[CityStats] = {
    cityCheckIns.toList
      .map(_ match {
        case (city, checkIns) => CityStats(city, checkIns)
      })
      .sortBy(_.checkIns)
      .reverse
      .take(3)
  }

  def updateRanking(
      storedCheckIns: Ref[IO, Map[City, Int]],
      storedRanking: Ref[IO, List[CityStats]]
  ): IO[Nothing] = {
    storedCheckIns.get
      .map(topCities)
      .flatMap(storedRanking.set)
      .foreverM
  }

  def storeCheckIn(
      storedCheckIns: Ref[IO, Map[City, Int]]
  )(city: City): IO[Unit] = {
    storedCheckIns.update(_.updatedWith(city)(_ match {
      case None           => Some(1)
      case Some(checkIns) => Some(checkIns + 1)
    }))
  }

  // 元のバージョン
  // def processCheckIns(checkIns: Stream[IO, City]): IO[Unit] =
  //   for {
  //     storedCheckIns <- Ref.of[IO, Map[City, Int]](Map.empty)
  //     storedRanking <- Ref.of[IO, List[CityStats]](List.empty)
  //     rankingProgram = updateRanking(storedCheckIns, storedRanking)
  //     checkInsProgram = checkIns
  //       .evalMap(storeCheckIn(storedCheckIns))
  //       .compile
  //       .drain
  //     _ <- List(rankingProgram, checkInsProgram).parSequence
  //   } yield ()

  val delay: FiniteDuration = FiniteDuration(1, TimeUnit.SECONDS)
  val ticks: Stream[IO, Unit] = Stream.fixedRate(delay)

  def processCheckIns(checkIns: Stream[IO, City]): IO[Unit] =
    for {
      storedCheckIns <- Ref.of[IO, Map[City, Int]](Map.empty)
      storedRanking <- Ref.of[IO, List[CityStats]](List.empty)
      rankingProgram = updateRanking(storedCheckIns, storedRanking)
      checkInsProgram = checkIns
        .evalMap(storeCheckIn(storedCheckIns))
        .compile
        .drain
      // displayProgram =
      //   ticks.foreach(_ => storedRanking.get.flatMap(IO.println)).compile.drain
      displayProgram =
        IO.sleep(1.second)
          .flatMap(_ => storedRanking.get)
          .flatMap(IO.println)
          .foreverM

      _ <- List(rankingProgram, checkInsProgram, displayProgram).parSequence
    } yield ()

  def main = {
    processCheckIns(checkIns).unsafeRunSync()
  }
}
ch10_21.main
