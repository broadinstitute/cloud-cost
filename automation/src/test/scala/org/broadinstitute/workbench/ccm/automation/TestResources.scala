package org.broadinstitute.workbench.ccm
package automation

import cats.implicits._
import cats.effect.{IO, Resource}
import io.grpc.{ManagedChannel, ManagedChannelBuilder}
import org.lyranthe.fs2_grpc.java_runtime.implicits._
import cats.implicits._
import io.circe.{Decoder, Encoder, Json}
import org.broadinstitute.workbench.ccm.pricing.PricingList
import org.broadinstitute.workbench.ccm.pricing.PricingList.GcpPriceList
import scalacache.CatsEffect.modes._
import scalacache.memcached._
import scalacache.serialization.circe._

object TestResources {
  val managedChannelResource: Resource[IO, ManagedChannel] = for {
    config <- Resource.liftF(AutomationTestConfig.config)
    mc <- ManagedChannelBuilder
            .forAddress(config.host, config.grpcPort)
            .usePlaintext()
            .resource[IO]
  } yield mc

  //TODO: get host and port right
  implicit val cacheTestObjectDecoder: Decoder[PricingList] = Decoder.decodeDouble.map(x => GcpPriceList(Json.fromDouble(x).get))
  implicit val cacheTestObjectEncoder: Encoder[PricingList] = Encoder.encodeDouble.contramap(_.cpuCost)

  val cache = Resource.make[IO, MemcachedCache[PricingList]](IO(MemcachedCache[PricingList]("localhost:11211")))(cache => IO(cache.removeAll[IO]()) >> IO(cache.close[IO]))
}