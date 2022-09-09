package com.booktopia.controllers.request

import javax.validation.constraints.Email

data class PutAdminRequest(
    var name: String?,
    @field:Email(message = "Invalid email format")
    var email: String?
) {
}