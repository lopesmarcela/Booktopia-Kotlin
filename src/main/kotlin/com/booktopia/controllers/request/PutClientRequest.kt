package com.booktopia.controllers.request

import javax.validation.constraints.Email

data class PutClientRequest(
    var name: String?,
    @field:Email(message = "Invalid email format")
    var email: String?
) {
}