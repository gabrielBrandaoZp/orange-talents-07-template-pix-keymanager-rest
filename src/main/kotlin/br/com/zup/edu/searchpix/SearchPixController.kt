package br.com.zup.edu.searchpix

import br.com.zup.edu.KeyManagerListUserPixServiceGrpc
import br.com.zup.edu.KeyManagerSearchPixServiceGrpc
import br.com.zup.edu.ListUserPixRequest
import br.com.zup.edu.SearchKeyExternalRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import java.util.*

@Controller(value = "/api/v1/clientes")
class SearchPixController(
    @Inject
    val searchPix: KeyManagerSearchPixServiceGrpc.KeyManagerSearchPixServiceBlockingStub,

    @Inject
    val listUserPix: KeyManagerListUserPixServiceGrpc.KeyManagerListUserPixServiceBlockingStub,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get(value = "/pix/{pixId}")
    fun find(@PathVariable("pixId") pixId: String): HttpResponse<SearchPixResponse> {
        logger.info("method=find, msg=findinig pix: {}", pixId)

        val request = SearchKeyExternalRequest.newBuilder()
            .setPixValue(pixId)
            .build()

        val response = searchPix.searchKeyExternal(request)
        return HttpResponse.ok(SearchPixResponse(response))
    }

    @Get("/{clienteId}/pix")
    fun findAll(@PathVariable("clienteId") clienteId: UUID): HttpResponse<ListPixResponse> {
        val request = ListUserPixRequest.newBuilder()
            .setUserId(clienteId.toString())
            .build()

        val response = listUserPix.listUserPix(request)

        return HttpResponse.ok(ListPixResponse(response))
    }
}