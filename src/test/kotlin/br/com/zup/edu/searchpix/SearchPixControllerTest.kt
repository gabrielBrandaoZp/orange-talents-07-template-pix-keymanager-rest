package br.com.zup.edu.searchpix

import br.com.zup.edu.*
import br.com.zup.edu.registernewpix.AccountType
import br.com.zup.edu.registernewpix.PixType
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime

@MicronautTest
internal class SearchPixControllerTest(
    @Inject
    @Client(value = "/")
    val client: HttpClient,

    @Inject
    val searchManager: KeyManagerSearchPixServiceGrpc.KeyManagerSearchPixServiceBlockingStub,
) {

    private lateinit var validPix: String

    @BeforeEach
    internal fun setUp() {
        validPix = "ponte@email.com"
    }

    @Test
    internal fun `should return 200 when valid pix`() {
        val grpcPixInfo = createGrpcPixInfo()
        val grpcUserInfo = createGrpcUserInfo()
        val grpcAccountInfo = createGrpcAccountInfo()

        val grpcReponse = SearchKeyExternalResponse.newBuilder()
            .setPixInfo(grpcPixInfo)
            .setUserInfo(grpcUserInfo)
            .setAccountInfo(grpcAccountInfo)
            .build()

        given(searchManager.searchKeyExternal(Mockito.any())).willReturn(grpcReponse)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/pix/${validPix}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.status)
    }

    private fun createGrpcPixInfo(): PixInfo {
        return PixInfo.newBuilder()
            .setPixType(PixType.EMAIL.toString())
            .setPixValue(validPix)
            .setCreatedAt(LocalDateTime.now().toString())
            .build()
    }

    private fun createGrpcUserInfo(): UserInfo {
        return UserInfo.newBuilder()
            .setName("Rafael Ponte")
            .setCpf("12345678909")
            .build()
    }

    private fun createGrpcAccountInfo(): AccountInfo {
        return AccountInfo.newBuilder()
            .setInstitution("ITAU UNIBANCO S.A.")
            .setAgency("0001")
            .setNumber("12345")
            .setAccountType(AccountType.CONTA_CORRENTE.toString())
            .build()
    }

    @Factory
    @Replaces(factory = KeymanagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(KeyManagerSearchPixServiceGrpc.KeyManagerSearchPixServiceBlockingStub::class.java)
    }
}