package br.com.zup.edu.searchpix

import br.com.zup.edu.AccountInfo
import br.com.zup.edu.PixInfo
import br.com.zup.edu.SearchKeyExternalResponse
import br.com.zup.edu.UserInfo

class SearchPixResponse(response: SearchKeyExternalResponse) {

    val pixInfo = PixInfoResponse(response.pixInfo)
    val userInfo = UserInfoResponse(response.userInfo)
    val accountInfo = AccountInfoResponse(response.accountInfo)
}

class PixInfoResponse(pixInfo: PixInfo) {
    val pixType: String = pixInfo.pixType
    val pixValue: String = pixInfo.pixValue
    val createdAt: String = pixInfo.createdAt
}

class UserInfoResponse(userInfo: UserInfo) {
    val name: String = userInfo.name
    val cpf: String = userInfo.cpf
}

class AccountInfoResponse(accountInfo: AccountInfo) {
    val institution: String = accountInfo.institution
    val agency: String = accountInfo.agency
    val number: String = accountInfo.number
    val accountType: String = accountInfo.accountType
}
