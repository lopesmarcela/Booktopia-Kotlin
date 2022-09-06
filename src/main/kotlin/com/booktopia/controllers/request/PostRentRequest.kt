package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotEmpty

data class PostRentRequest (
    @JsonAlias("rental_date")
    @field:NotEmpty(message = "Rental date must be informed")
    var rentalDate: String,
    @JsonAlias("client_id")
    var clientId: Int,
    @JsonAlias("book_id")
    var bookId: Int
)