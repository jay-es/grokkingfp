import cats.effect.{IO, Ref}
import cats.implicits.catsSyntaxParallelSequence1 // parSequence
import scala.concurrent.duration._

object ch10_17 {
  import ch10_CastingDieConcurrently.castTheDie

  val ans1 = for {
    _ <- IO.sleep((1.second))
    list <- List(castTheDie(), castTheDie()).parSequence
  } yield list.sum

  val ans2 = for {
    list <- Ref.of[IO, List[Int]](List.empty)
    _ <- List(
      castTheDie().flatMap(n => list.update(_.appended(n))),
      castTheDie().flatMap(n => list.update(_.appended(n)))
    ).parSequence
    result <- list.get
  } yield result

  val ans3 = for {
    list <- Ref.of[IO, List[Int]](List.empty)
    _ <- List(
      castTheDie().flatMap(n => list.update(_.appended(n))),
      castTheDie().flatMap(n => list.update(_.appended(n))),
      castTheDie().flatMap(n => list.update(_.appended(n)))
    ).parSequence
    result <- list.get
  } yield result

  val ans4 = for {
    counter <- Ref.of[IO, Int](0)
    _ <- List
      .fill(100)(
        castTheDie().flatMap(n =>
          if (n == 6) counter.update(_ + 1) else IO.unit
        )
      )
      .parSequence
    result <- counter.get
  } yield result

  val ans5 = List
    .fill(100)(IO.sleep(1.second).flatMap(_ => castTheDie()))
    .parSequence
    .map(_.sum)

  def main = {
    println(ans1.unsafeRunSync())
    println(ans2.unsafeRunSync())
    println(ans3.unsafeRunSync())
    println(ans4.unsafeRunSync())
    println(ans5.unsafeRunSync())
  }
}
ch10_17.main
