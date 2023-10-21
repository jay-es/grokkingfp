import cats.effect.{IO, Ref}
import cats.implicits.{
  catsSyntaxParallelSequence1, // parSequence
  toTraverseOps // sequence
}
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit

object ch10_16 {
  def main = {
    def sleep1s[A](f: Unit => IO[A]) =
      IO.sleep((FiniteDuration(1, TimeUnit.SECONDS))).flatMap(f)

    def list(counter: Ref[IO, Int]): List[IO[Unit]] = List(
      sleep1s(_ => counter.update(_ + 2)),
      sleep1s(_ => counter.update(_ + 3)),
      sleep1s(_ => counter.update(_ + 4))
    )

    val seq = for {
      counter <- Ref.of[IO, Int](0)
      _ <- list(counter).sequence // 直列
      result <- counter.get
    } yield result

    println(seq.unsafeRunSync())

    val con = for {
      counter <- Ref.of[IO, Int](0)
      _ <- list(counter).parSequence // 並列
      result <- counter.get
    } yield result

    println(con.unsafeRunSync())
  }
}
ch10_16.main
