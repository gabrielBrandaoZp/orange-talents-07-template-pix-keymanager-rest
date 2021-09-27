package br.com.zup.edu.registernewpix

import br.com.zup.edu.KeyManagerRegisterServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.Valid

@Validated
@Controller(value = "/api/v1/clientes/{clienteId}/pix")
class NewPixController(
    @Inject
    val registryPixClient: KeyManagerRegisterServiceGrpc.KeyManagerRegisterServiceBlockingStub,

    ) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Post
    fun register(
        @PathVariable("clienteId") clienteId: UUID,
        @Valid @Body request: NewPixRequest,
    ): HttpResponse<NewPixResponse> {
        logger.info("method=register, msg=registering new pix key: {}, for user: {}", request.pixValue, clienteId)

        val response = registryPixClient.registerKey(request.toGrpcModel(clienteId))

        val uri = UriBuilder
            .of("/api/v1/pix/{pixId}")
            .expand(mutableMapOf(Pair("pixId", response.pixId)))
        return HttpResponse.created(uri)

    }
}