import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.*

object ch08_CardGame {
  def castTheDie(): Int = {
    ch08_CastingDieImpure.WithFailures.castTheDieImpure()
  }

  def drawAPointCard(): Int = {
    ch08_CastingDieImpure.drawAPointCard()
  }
}
