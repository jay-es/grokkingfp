import cats.effect.IO
import ch08_CardGame.castTheDie, ch08_CardGame.drawAPointCard

val res1 = IO.delay(castTheDie()).orElse(IO.pure(0))

val res2 = IO.delay(drawAPointCard()).orElse(IO.delay(castTheDie()))

val res3 = IO
  .delay(castTheDie())
  .orElse(IO.delay(castTheDie()))
  .orElse(IO.pure(0))

val res4 = for {
  die <- IO.delay(castTheDie()).orElse(IO.pure(0))
  card <- IO.delay(drawAPointCard()).orElse(IO.pure(0))
} yield die + card

val res5 = (for {
  card <- IO.delay(drawAPointCard())
  die1 <- IO.delay(castTheDie())
  die2 <- IO.delay(castTheDie())
} yield card + die1 + die2).orElse(IO.pure(0))
