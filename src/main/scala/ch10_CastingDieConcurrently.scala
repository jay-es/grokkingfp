import cats.effect.unsafe.{IORuntime, IORuntimeConfig, Scheduler}
import cats.effect.{IO, Ref}
import cats.implicits._

import java.util.concurrent.{Executors, ScheduledThreadPoolExecutor}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object ch10_CastingDieConcurrently extends App {
  import ch08_CastingDieImpure.NoFailures.castTheDieImpure

  def castTheDie(): IO[Int] = IO.delay(castTheDieImpure())
}
