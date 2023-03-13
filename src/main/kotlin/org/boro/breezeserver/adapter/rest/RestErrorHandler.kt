package org.boro.breezeserver.adapter.rest

import mu.KLogging
import org.boro.breezeserver.domain.exception.PeakFlowNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class RestErrorHandler {

    companion object: KLogging()

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PeakFlowNotFoundException::class)
    fun handleNotFound(exception: PeakFlowNotFoundException): ErrorResponse {
        logger.warn { exception.message }
        return ErrorResponse(
            Instant.now(),
            HttpStatus.NOT_FOUND.value(),
            exception.message ?: "Unknown error"
        )
    }
}

data class ErrorResponse(
    val timestamp: Instant,
    val status: Int,
    val error: String
)
