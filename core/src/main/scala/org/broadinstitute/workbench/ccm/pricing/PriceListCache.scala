package org.broadinstitute.workbench.ccm
package pricing

import cats.implicits._
import cats.effect.Async
import io.circe.{Decoder, Encoder, Json}
import scalacache.Cache
import scalacache.CatsEffect.modes._

// Price list cache so that we don't have to hit Google Billing API all the time
class PriceListWithCache[F[_]: Async](cache: Cache[PricingList], gcpPricing: GcpPricing[F]) {
  def getPriceList(cacheKey: CacheKey): F[PricingList] = cache.cachingF[F](cacheKey)(None){
    if(cacheKey.cloudProvider == CloudProvider.Google)
      gcpPricing.getPriceList().widen[PricingList]
    else
      Async[F].raiseError(new Exception("Cloud provider not yet supported"))
  }
}

// Define actual fields for pricing list
sealed abstract class PricingList {
  def cpuCost: Double
}
object PricingList{
  final case class GcpPriceList(asJson: Json) extends PricingList {
    override def cpuCost: Double = 10
  }

  final case class AwsPriceList(asJson: Json) extends PricingList {
    override def cpuCost: Double = 10
  }

  implicit val cacheTestObjectDecoder: Decoder[PricingList] = Decoder.decodeDouble.map(x => new PricingList {
    override def cpuCost: Double = x
  })
  implicit val cacheTestObjectEncoder: Encoder[PricingList] = Encoder.encodeDouble.contramap(_.cpuCost)
}

sealed abstract class CloudProvider {
  def asString: String
}

object CloudProvider {
  final case object Google extends CloudProvider {
    override def asString: String = "GCP"
  }
  final case object AWS extends CloudProvider {
    override def asString: String = "AWS"
  }
}

final case class ExpiresDay(asString: String) extends AnyVal //eg: 2019:01:10
final case class CacheKey(cloudProvider: CloudProvider, expiresDay: ExpiresDay){
  override def toString: String = s"${cloudProvider.toString}:${expiresDay.asString}"
}