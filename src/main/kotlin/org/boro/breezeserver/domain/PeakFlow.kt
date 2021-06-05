package org.boro.breezeserver.domain

import java.time.Instant

data class PeakFlow(
    var value: Int,
    var checkedAt: Instant,
    var id: PeakFlowId? = null
) {
    constructor(value: Int, checkedAt: Instant): this(value, checkedAt, null)

    fun update(from: PeakFlow) = copy(
        value = from.value,
        checkedAt = from.checkedAt
    )
}
