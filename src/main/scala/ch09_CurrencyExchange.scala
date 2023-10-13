import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits._
import fs2.Stream

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration
import scala.jdk.CollectionConverters.MapHasAsScala

object ch09_CurrencyExchange {

  /** PREREQUISITE: model
    */
  object model {
    opaque type Currency = String
    object Currency {
      def apply(name: String): Currency = name
      extension (currency: Currency) def name: String = currency
    }
  }
  import model._

  /** PREREQUISITE: Impure, unsafe and side-effectful API call
    *
    * See [[ch09_CurrencyExchangeImpure.exchangeRatesTableApiCall]]
    *
    * We wrap them here to be able to use Scala immutable collections and
    * BigDecimal.
    */
  def exchangeRatesTableApiCall(currency: String): Map[String, BigDecimal] = {
    ch09_CurrencyExchangeImpure
      .exchangeRatesTableApiCall(currency)
      .asScala
      .view
      .mapValues(BigDecimal(_))
      .toMap
  }
}
