import cats.effect.IO
// 9.6
object model {
  opaque type Currency = String
  object Currency {
    def apply(name: String): Currency = name
    extension (currency: Currency) def name: String = currency
  }
}
import model._

def ch09_06 = {
  // 1-6
  val m1: Map[String, String] = Map("key" -> "value")
  assert(m1 == Map("key" -> "value"))
  val m2: Map[String, String] = m1.updated("key2", "value2")
  assert(m2 == Map("key" -> "value", "key2" -> "value2"))
  val m3: Map[String, String] = m2.updated("key2", "another2")
  assert(m3 == Map("key" -> "value", "key2" -> "another2"))
  val m4: Map[String, String] = m3.removed("key")
  assert(m4 == Map("key2" -> "another2"))
  val valueFromM3 = m3.get("key")
  assert(valueFromM3 == Some("value"))
  val valueFromM4 = m4.get("key")
  assert(valueFromM4 == None)

  // 7
  val usdRates = Map(Currency("EUR") -> BigDecimal(0.82))
  assert(
    usdRates.updated(Currency("EUR"), BigDecimal(0.83))
      == Map(Currency("EUR") -> BigDecimal(0.83))
  )
  assert(usdRates.removed(Currency("EUR")) == Map.empty)
  assert(
    usdRates.removed(Currency("JPY"))
      == Map(Currency("EUR") -> BigDecimal(0.82))
  )
  assert(usdRates.get(Currency("EUR")) == Some(BigDecimal(0.82)))
  assert(usdRates.get(Currency("JPY")) == None)
}
ch09_06

// 9.13
object ch09_13 {
  val usdExchangeTables = List(
    Map(Currency("EUR") -> BigDecimal(0.88)),
    Map(
      Currency("EUR") -> BigDecimal(0.89),
      Currency("JPY") -> BigDecimal(114.62)
    ),
    Map(Currency("JPY") -> BigDecimal(114))
  )

  def extractSingleCurrencyRate(currencyToExtract: Currency)(
      table: Map[Currency, BigDecimal]
  ): Option[BigDecimal] = table.get(currencyToExtract)

  def main = {
    val res = usdExchangeTables.map(extractSingleCurrencyRate(Currency("EUR")))
    assert(
      res == List(Some(BigDecimal(0.88)), Some(BigDecimal(0.89)), None)
    )
    val res2 = usdExchangeTables.map(_.get(Currency("EUR")))
    assert(res2 == res)
  }
}
ch09_13.main

object ch09_23 {
  import ch09_13.extractSingleCurrencyRate
  import ch09_CurrencyExchange.exchangeRatesTableApiCall

  def exchangeTable(from: Currency): IO[Map[Currency, BigDecimal]] = {
    IO.delay(exchangeRatesTableApiCall(from.name))
      .map(table =>
        table.map(kv =>
          kv.match { case (currencyName, rate) =>
            (Currency(currencyName), rate)
          }
        )
      )
  }
  def retry[A](action: IO[A], maxRetries: Int): IO[A] = {
    List
      .range(0, maxRetries)
      .map(_ => action)
      .foldLeft(action)((program, retryAction) => program.orElse(retryAction))
  }

  def currencyRate(from: Currency, to: Currency): IO[BigDecimal] = {
    for {
      table <- retry(exchangeTable(from), 10)
      rate <- extractSingleCurrencyRate(to)(table) match
        case None        => currencyRate(from, to)
        case Some(value) => IO.pure(value)

    } yield rate
  }

  def main = {}
}
ch09_23.main
