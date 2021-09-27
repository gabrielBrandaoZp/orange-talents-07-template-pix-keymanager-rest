package br.com.zup.edu.removepix

import br.com.zup.edu.KeyManagerRegisterServiceGrpc
import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import br.com.zup.edu.RemoveKeyResponse
import br.com.zup.edu.shared.grpc.KeymanagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*

@MicronautTest
internal class RemovePixControllerTest(
    @Inject
    @Client(value = "/")
    val client: HttpClient,

    @Inject
    val removeKeyManager: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub,

    ) {

    @Test
    internal fun `should return 200 when user and pix exists`() {
        val grpcResponse = RemoveKeyResponse.newBuilder()
            .setResult(true)
            .build()

        given(removeKeyManager.removeKey(Mockito.any())).willReturn(grpcResponse)


        val validUserId = "c56dfef4-7901-44fb-84e2-a2cefb157890"
        val pixId = UUID.randomUUID().toString()

        val request = HttpRequest.DELETE("/api/v1/clientes/${validUserId}/pix/${pixId}", Any::class.java)
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = KeymanagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub::class.java)
    }

}