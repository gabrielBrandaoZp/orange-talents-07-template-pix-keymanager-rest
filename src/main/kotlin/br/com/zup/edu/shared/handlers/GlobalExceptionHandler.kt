package br.com.zup.edu.shared.handlers

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class GlobalExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {
        val statusCode = exception.status.code
        val description = exception.status.description
        val (httpStatus, message) = when (statusCode) {
            Status.FAILED_PRECONDITION.code -> Pair(HttpStatus.SERVICE_UNAVAILABLE, description)
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, description)
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, description)
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, description)
            else -> {
                logger.error("method=handler, msg=unexpected error occurred while trying to save pix key")
                Pair(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
            }
        }

        return HttpResponse.status<JsonError>(httpStatus).body(JsonError(message))
    }
}