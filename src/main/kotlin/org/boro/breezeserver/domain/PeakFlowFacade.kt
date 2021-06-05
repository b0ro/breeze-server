package org.boro.breezeserver.domain

class PeakFlowFacade(private val repository: PeakFlowRepository) {

    fun create(peakFlow: PeakFlow) = repository.save(peakFlow)

    fun findAll(): List<PeakFlow> = repository.findAll()

    fun findAllLatestFirst(): List<PeakFlow> = repository.findAllLatestFirst()

    fun update(id: PeakFlowId, from: PeakFlow): PeakFlow {
        val peakFlow = repository.find(id).update(from)
        return repository.save(peakFlow)
    }

    fun delete(id: PeakFlowId) {
        val peakFlow = repository.find(id)
        repository.delete(peakFlow)
    }
}
