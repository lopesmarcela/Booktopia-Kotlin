package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostRentRequest (
    @JsonAlias("rental_date")
    @field:NotEmpty(message = "Rental date must be informed")
    var rentalDate: String,
    @JsonAlias("client_id")
    @field:NotNull
    @field:Positive
    var clientId: Int,
    @JsonAlias("book_id")
    @field:NotNull
    var bookId: Set<Int>
)