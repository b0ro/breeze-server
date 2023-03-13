package org.boro.breezeserver.domain.exception

import org.boro.breezeserver.domain.PeakFlowId

class PeakFlowNotFoundException(id: PeakFlowId) : RuntimeException(
    "Peak flow with id ${id.value} not found!"
)
