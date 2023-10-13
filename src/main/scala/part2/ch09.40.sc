import cats.effect.IO
import fs2.Stream
import ch08_CastingDie.castTheDieImpure

// 9.40
object ch09_40 {
  def castTheDie(): IO[Int] = IO.delay(castTheDieImpure())
  val infiniteDieCasts: Stream[IO, Int] = Stream.eval(castTheDie()).repeat

  def main = {
    val ans1 = infiniteDieCasts.filter(_ % 2 != 0).take(3).compile.toList
    println(ans1.unsafeRunSync())

    val ans2 = infiniteDieCasts
      .take(5)
      .map(n => if (n == 6) 12 else n)
      .compile
      .toList
    println(ans2.unsafeRunSync())

    val ans3 =
      // infiniteDieCasts.take(3).fold(0)(_ + _).compile.toList
      infiniteDieCasts.take(3).compile.toList.map(_.sum)
    println(ans3.unsafeRunSync())

    val ans4 = infiniteDieCasts.dropWhile(_ != 5).take(3).compile.toList
    println(ans4.unsafeRunSync())

    val ans5 = infiniteDieCasts.take(100).compile.drain
    println(ans5.unsafeRunSync())

    val ans6 =
      infiniteDieCasts
        .take(3)
        .append(infiniteDieCasts.take(3).map(_ * 3))
        .compile
        .toList
    println(ans6.unsafeRunSync())

    // val ans3b = infiniteDieCasts.take(3).scan(0)(_ + _).drop(3).compile.toList
    // println(ans3b.unsafeRunSync())

    // val ans7 =
    //   infiniteDieCasts
    //     .scan((false, false))((acc, b) => (acc._2, b == 6))
    //     .exists(t => t._1 && t._2)
    //     .compile
    //     .toList
    val ans7 =
      infiniteDieCasts
        .scan(0)((sixesInRow, current) =>
          if (current == 6) sixesInRow + 1 else 0
        )
        .filter(_ == 2)
        .take(1)
        .compile
        .toList
    println(ans7.unsafeRunSync())

  }
}
ch09_40.main
