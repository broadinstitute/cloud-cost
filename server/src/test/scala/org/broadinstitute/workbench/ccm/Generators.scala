package org.broadinstitute.workbench.ccm
package server

import io.circe.Json
import org.broadinstitute.workbench.ccm.pricing.{CacheKey, CloudProvider, ExpiresDay, PricingList}
import org.broadinstitute.workbench.ccm.pricing.PricingList.GcpPriceList
import org.broadinstitute.workbench.ccm.protos.ccm.WorkflowCostRequest
import org.scalacheck.{Arbitrary, Gen}

object Generators {
  val genWorkflowCostRequest = Gen.alphaStr.map(x => WorkflowCostRequest(x))
  val genCloudProvider = Gen.oneOf(CloudProvider.Google, CloudProvider.AWS)
  val genCacheKey = for{
    cloudProvider <- genCloudProvider
    expiresDay <- Gen.calendar.map(x => ExpiresDay(s"2019:01:${x.getFirstDayOfWeek}"))
  } yield CacheKey(cloudProvider, expiresDay)

  implicit val arbWorkflowCostRequest = Arbitrary(genWorkflowCostRequest)


  implicit val arbPricingList: Arbitrary[PricingList] = Arbitrary(Gen.posNum[Double].map(x => GcpPriceList(Json.fromDouble(x).get)))
  implicit val arbCachedKey: Arbitrary[CacheKey] = Arbitrary(genCacheKey)
}
