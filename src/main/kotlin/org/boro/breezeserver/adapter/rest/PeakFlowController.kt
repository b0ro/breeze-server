package org.boro.breezeserver.adapter.rest

import mu.KLogging
import org.boro.breezeserver.domain.PeakFlow
import org.boro.breezeserver.domain.PeakFlowFacade
import org.boro.breezeserver.domain.PeakFlowId
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.time.Instant
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.PastOrPresent


@RestController
@RequestMapping("/api/peak-flows")
class PeakFlowController(private val facade: PeakFlowFacade) {

    @PostMapping
    fun create(@RequestBody @Valid request: PeakFlowRequest) =
        facade.create(request.toDomain()).toResponse()

    @GetMapping
    fun getAllPaged() = facade.findAllLatestFirst().map { it.toResponse() }

    @PutMapping("/{id}")
    fun update(@PathVariable id: PeakFlowId, @RequestBody @Valid request: PeakFlowRequest) =
        facade.update(id, request.toDomain()).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: PeakFlowId) = facade.delete(id)
}

@Component
class StringToPeakFlowIdConverter: Converter<String, PeakFlowId> {

    companion object: KLogging()

    override fun convert(id: String) = PeakFlowId(id.toLong())
}

data class PeakFlowRequest(
    @field:Min(value = 60, message = "Value should not be less than 60")
    @field:Max(value = 800, message = "Value should not be greater than 800")
    val value: Int,

    @field:PastOrPresent(message = "Peak flow check date can only be from past or present")
    val checkedAt: Instant
)

private fun PeakFlowRequest.toDomain() = PeakFlow(
    value = this.value,
    checkedAt = this.checkedAt
)

data class PeakFlowResponse(
    val value: Int,
    val checkedAt: Instant,
    val id: Long
)

private fun PeakFlow.toResponse() = PeakFlowResponse(
    value = value,
    checkedAt = checkedAt,
    id = id?.value ?: 0
)
