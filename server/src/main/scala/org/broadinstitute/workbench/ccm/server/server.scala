package org.broadinstitute.workbench.ccm

import cats.Eq
import org.broadinstitute.workbench.protos.ccm.WorkflowCostResponse

package object server {
  implicit val eqWorkflowCostResponse: Eq[WorkflowCostResponse] = Eq.instance((x, y) => x.cost == y.cost)
}
