package org.boro.breezeserver.adapter.rest

import org.boro.breezeserver.domain.ExhaleTest
import org.boro.breezeserver.domain.ExhaleTestRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.PastOrPresent

@RestController
@RequestMapping("/api/exhale-tests")
class ExhaleTestController(
    private val repository: ExhaleTestRepository
) {

    @PostMapping
    fun create(@RequestBody @Valid request: ExhaleTestRequest) : ExhaleTest {
        return repository.save(request.toDomain())
    }

    @GetMapping
    fun getAllPaged(pageable: Pageable): Page<ExhaleTest> {
        return repository.findAll(pageable)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid request: ExhaleTestRequest): ExhaleTest {
        return repository.findByIdOrNull(id)
            ?.let {
                it.lungCapacity = request.lungCapacity
                it.checkedAt = request.checkedAt
                repository.save(it)
            }
            ?: run { throw ExhaleTestNotFoundException(id) }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        repository.findByIdOrNull(id)
            ?.let { repository.delete(it) }
            ?: run { throw ExhaleTestNotFoundException(id) }
    }
}

private fun ExhaleTestRequest.toDomain() = ExhaleTest(
    lungCapacity = this.lungCapacity,
    checkedAt = this.checkedAt
)

data class ExhaleTestRequest(
    @field:Min(value = 60, message = "Value should not be less than 60")
    @field:Max(value = 800, message = "Value should not be greater than 800")
    val lungCapacity: Int,

    @field:PastOrPresent(message = "Test date can only be from past or present")
    val checkedAt: Instant
)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ExhaleTestNotFoundException(id: Long) : RuntimeException(
    "Exhale test with id {$id} not found!"
)
