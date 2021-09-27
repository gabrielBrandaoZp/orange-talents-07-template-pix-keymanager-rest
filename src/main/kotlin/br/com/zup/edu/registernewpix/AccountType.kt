package br.com.zup.edu.registernewpix

import br.com.zup.edu.NewKeyRequest

enum class AccountType(val grpcAttribute: NewKeyRequest.AccountType) {
    CONTA_CORRENTE(NewKeyRequest.AccountType.CONTA_CORRENTE),
    CONTA_POUPANCA(NewKeyRequest.AccountType.CONTA_POUPANCA);
    
}