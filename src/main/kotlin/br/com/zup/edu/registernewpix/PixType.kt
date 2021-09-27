package br.com.zup.edu.registernewpix

import br.com.zup.edu.NewKeyRequest

enum class PixType(val grpcAttribute: NewKeyRequest.PixType) {
    CPF(NewKeyRequest.PixType.CPF),
    EMAIL(NewKeyRequest.PixType.EMAIL),
    TELEFONE(NewKeyRequest.PixType.TELEFONE),
    CHAVE_ALEATORIA(NewKeyRequest.PixType.CHAVE_ALEATORIA);
}