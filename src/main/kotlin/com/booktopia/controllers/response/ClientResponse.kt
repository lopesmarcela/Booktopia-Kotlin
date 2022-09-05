package com.booktopia.controllers.response

import com.booktopia.enums.StatusEnum

data class ClientResponse(
    var id: Int?,
    var cpf: String,
    var name: String,
    var email: String,
    var addressId:Int?,
    var status: StatusEnum?
) {
}