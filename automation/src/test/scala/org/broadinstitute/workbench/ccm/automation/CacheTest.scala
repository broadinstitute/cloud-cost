package org.broadinstitute.workbench.ccm
package automation

import cats.effect.IO
import minitest.laws.Checkers
import org.broadinstitute.workbench.ccm.pricing.{CacheKey, PricingList}
import scalacache.CatsEffect.modes._
import server.Generators._

object CacheTest extends CcmTestSuite with Checkers {
  test("Validate cache is up and running") {
    check2 {
      (key: CacheKey, cacheTestObject: PricingList) =>
        println(s"first $key")
        val res = TestResources.cache.use {
          cache =>
            for {
              _ <- cache.remove[IO](key)
              getNonExistentCache <- cache.get[IO](key)
              _ = println(getNonExistentCache)
              cachedObject <- cache.cachingF[IO](key)(None)(IO.pure(cacheTestObject))
              _ = println(cachedObject)
              getCacheAfterCaching <- cache.get[IO](key)
            } yield {
              assertEquals(getNonExistentCache, None)
              assertEquals(getCacheAfterCaching, cacheTestObject)
              println("nonExist: "+getNonExistentCache)
              println("cachedObject: "+cachedObject)
            }
        }

        res.unsafeRunSync()
        true
    }
  }
}