package br.com.zup.edu.removepix

import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import br.com.zup.edu.RemoveKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import java.util.*

@Validated
@Controller(value = "/api/v1/clientes/{clienteId}/pix")
class RemovePixController(
    @Inject
    val removePixClient: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Delete(value = "/{pixId}")
    fun remove(@PathVariable("clienteId") clienteId: UUID, @PathVariable("pixId") pixId: String): HttpResponse<Unit> {
        logger.info("method=remove, msg=removing pix: {} for user: {}", pixId, clienteId)

        val request = RemoveKeyRequest.newBuilder()
            .setPixValue(pixId)
            .setUserId(clienteId.toString())
            .build()

        removePixClient.removeKey(request)
        return HttpResponse.ok()
    }
}