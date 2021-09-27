package br.com.zup.edu.shared.handlers

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GlobalExceptionHandlerTest {

    private val genericRequest = HttpRequest.GET<Any>("/")

    @Test
    internal fun `shoult return 404 when statusException is FAILED_PRECONDITION`() {
        val message = "not found"
        val notFoundException = StatusRuntimeException(Status.FAILED_PRECONDITION
            .withDescription(message))

        val response = GlobalExceptionHandler().handle(genericRequest, notFoundException)

        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `shoult return 400 when statusException is INVALID_ARGUMENT`() {
        val message = "invalid argument"
        val badRequestException = StatusRuntimeException(Status.INVALID_ARGUMENT
            .withDescription(message))

        val response = GlobalExceptionHandler().handle(genericRequest, badRequestException)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `shoult return 422 when statusException is ALREADY_EXISTS`() {
        val message = "already exists"
        val unprocessableEntityException = StatusRuntimeException(Status.ALREADY_EXISTS
            .withDescription(message))

        val response = GlobalExceptionHandler().handle(genericRequest, unprocessableEntityException)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    internal fun `shoult return 500 when statusException is any other error`() {
        val internalErrorException = StatusRuntimeException(Status.INTERNAL)

        val response = GlobalExceptionHandler().handle(genericRequest, internalErrorException)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        assertNotNull(response.body())
        assertTrue((response.body() as JsonError).message.contains("INTERNAL"))
    }

}