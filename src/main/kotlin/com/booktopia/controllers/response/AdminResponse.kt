package com.booktopia.controllers.response

import com.booktopia.enums.StatusEnum

data class AdminResponse(
    var id: Int?,
    var cpf: String,
    var name: String,
    var email: String,
    var status: StatusEnum?
) {
}