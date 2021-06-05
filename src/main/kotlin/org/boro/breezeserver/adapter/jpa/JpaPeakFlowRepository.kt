package org.boro.breezeserver.adapter.jpa

import org.boro.breezeserver.domain.PeakFlow
import org.boro.breezeserver.domain.PeakFlowId
import org.boro.breezeserver.domain.PeakFlowRepository
import org.boro.breezeserver.domain.exception.PeakFlowNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

class JpaPeakFlowRepository(private val repository: SpringDataPeakFlowRepository) : PeakFlowRepository {

    override fun save(peakFlow: PeakFlow) = repository.save(peakFlow.toEntity()).toDomain()

    override fun findAll() = repository.findAll().map { it.toDomain() }

    override fun findAllLatestFirst() = repository.findAllLatestFirst().map { it.toDomain() }

    override fun findByIdOrNull(id: PeakFlowId) = repository.findByIdOrNull(id.value)?.toDomain()

    override fun find(id: PeakFlowId) = findByIdOrNull(id) ?: run { throw PeakFlowNotFoundException(id) }

    override fun delete(peakFlow: PeakFlow) = repository.delete(peakFlow.toEntity())
}

@Repository
interface SpringDataPeakFlowRepository : JpaRepository<PeakFlowEntity, Long> {

    @Query("SELECT pf FROM PeakFlowEntity pf ORDER BY pf.checkedAt DESC, pf.id DESC")
    fun findAllLatestFirst(): List<PeakFlowEntity>
}

@Entity
@Table(name = "peak_flow")
class PeakFlowEntity(
    var value: Int,
    var checkedAt: Instant,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {
    constructor(value: Int, checkedAt: Instant) : this(value, checkedAt, null)
}

private fun PeakFlow.toEntity() = PeakFlowEntity(value, checkedAt, id?.value)
private fun PeakFlowEntity.toDomain() = PeakFlow(value, checkedAt, id?.let { PeakFlowId(it) })
