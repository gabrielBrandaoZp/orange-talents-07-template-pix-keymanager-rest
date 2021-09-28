package br.com.zup.edu.searchpix

import br.com.zup.edu.KeyManagerSearchPixServiceGrpc
import br.com.zup.edu.SearchKeyExternalRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import jakarta.inject.Inject
import org.slf4j.LoggerFactory

@Controller(value = "/api/v1/clientes/pix")
class SearchPixController(
    @Inject
    val searchPix: KeyManagerSearchPixServiceGrpc.KeyManagerSearchPixServiceBlockingStub,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get(value = "/{pixId}")
    fun find(@PathVariable("pixId") pixId: String): HttpResponse<SearchPixResponse> {
        logger.info("method=find, msg=findinig pix: {}", pixId)

        val request = SearchKeyExternalRequest.newBuilder()
            .setPixValue(pixId)
            .build()

        val response = searchPix.searchKeyExternal(request)
        return HttpResponse.ok(SearchPixResponse(response))
    }
}