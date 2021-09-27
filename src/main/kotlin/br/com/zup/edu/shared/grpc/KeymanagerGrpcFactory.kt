package br.com.zup.edu.shared.grpc

import br.com.zup.edu.KeyManagerRegisterServiceGrpc
import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class KeymanagerGrpcFactory(@GrpcChannel("keymanager") val channel: ManagedChannel) {

    @Singleton
    fun registerKey() = KeyManagerRegisterServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeKey() = KeyManagerRemoveServiceGrpc.newBlockingStub(channel)
}