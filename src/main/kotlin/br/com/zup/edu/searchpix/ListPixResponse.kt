package br.com.zup.edu.searchpix

import br.com.zup.edu.ListUserPixResponse

class ListPixResponse(response: ListUserPixResponse) {

    val userId: String = response.userId
    val pixDetails = response.pixDetailsList.map { pixDetail ->
        PixDetailsResponse(pixDetail)
    }
}

class PixDetailsResponse(pixDetail: ListUserPixResponse.PixDetails) {
    val pixId = pixDetail.pixId
    val pixInfo = PixInfoResponse(pixDetail.pixInfo)
    val accountType: String = pixDetail.accountType
}
