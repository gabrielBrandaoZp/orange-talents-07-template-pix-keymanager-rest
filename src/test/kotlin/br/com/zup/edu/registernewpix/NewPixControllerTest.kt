package br.com.zup.edu.registernewpix

import br.com.zup.edu.KeyManagerRegisterServiceGrpc
import br.com.zup.edu.NewKeyResponse
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*

@MicronautTest
internal class NewPixControllerTest(
    @Inject
    @Client(value = "/")
    val client: HttpClient,

    @Inject
    val registerKeymanager: KeyManagerRegisterServiceGrpc.KeyManagerRegisterServiceBlockingStub,
) {

    lateinit var validUserId: UUID
    lateinit var pixId: String

    @BeforeEach
    internal fun setUp() {
        validUserId = UUID.fromString("c56dfef4-7901-44fb-84e2-a2cefb157890")
        pixId = UUID.randomUUID().toString()
    }

    @Test
    internal fun `should return 201 when user exists and data is valid`() {
        val grpcResponse = NewKeyResponse.newBuilder()
            .setPixId(pixId)
            .build()

        given(registerKeymanager.registerKey(Mockito.any())).willReturn(grpcResponse)

        val newPixRequest = NewPixRequest(
            pixType = PixType.CHAVE_ALEATORIA,
            pixValue = "",
            accountType = AccountType.CONTA_CORRENTE
        )

        val request = HttpRequest.POST("/api/v1/clientes/${validUserId}/pix", newPixRequest)
        val response = client
            .toBlocking()
            .exchange(request, NewPixRequest::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))
    }

    @Factory
    @Replaces(factory = KeymanagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(KeyManagerRegisterServiceGrpc.KeyManagerRegisterServiceBlockingStub::class.java)
    }
}
