package org.broadinstitute.workbench.ccm

import org.scalacheck.{Arbitrary, Gen}

object Generators {
  val genBootDiskSizedGb = Gen.posNum[Int].map(BootDiskSizeGb)
}
