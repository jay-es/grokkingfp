import cats.effect.IO
import cats.implicits._
import cats.effect.unsafe.implicits.global

object ch08_CastingDie {
  def castTheDieImpure(): Int = {
    ch08_CastingDieImpure.NoFailures.castTheDieImpure()
  }

  object WithFailures {
    def castTheDieImpure(): Int = {
      ch08_CastingDieImpure.WithFailures.castTheDieImpure()
    }
  }
}
