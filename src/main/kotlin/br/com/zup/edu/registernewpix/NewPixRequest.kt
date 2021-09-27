package br.com.zup.edu.registernewpix

import br.com.zup.edu.NewKeyRequest
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class NewPixRequest(
    @field:NotNull
    val pixType: PixType?,
    @field:Size(max = 77)
    val pixValue: String?,
    @field:NotNull
    val accountType: AccountType?,
) {

    fun toGrpcModel(clienteId: UUID): NewKeyRequest {
        return NewKeyRequest.newBuilder()
            .setUserId(clienteId.toString())
            .setPixType(this.pixType?.grpcAttribute ?: NewKeyRequest.PixType.UNKNOWN_KEY_TYPE)
            .setPixValue(this.pixValue ?: "")
            .setAccountType(this.accountType?.grpcAttribute ?: NewKeyRequest.AccountType.UNKNOWN_ACCOUNT_TYPE)
            .build()
    }
}
