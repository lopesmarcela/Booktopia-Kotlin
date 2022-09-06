package com.booktopia.controllers.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostAddressRequest (
    @field:NotEmpty(message = "Street must be informed")
    var street: String,
    @field:NotNull(message = "Number must be informed")
    var number: Int,
    @field:NotEmpty(message = "District must be informed")
    var district: String,
    @field:NotEmpty(message = "City must be informed")
    var city: String,
    @field:NotEmpty(message = "Postal code must be informed")
    var cep: String
)