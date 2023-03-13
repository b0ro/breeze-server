package org.boro.breezeserver.domain

interface PeakFlowRepository {

    fun save(peakFlow: PeakFlow): PeakFlow

    fun findAll(): List<PeakFlow>

    fun findAllLatestFirst(): List<PeakFlow>

    fun findByIdOrNull(id: PeakFlowId): PeakFlow?

    fun find(id: PeakFlowId): PeakFlow

    fun delete(peakFlow: PeakFlow)
}
