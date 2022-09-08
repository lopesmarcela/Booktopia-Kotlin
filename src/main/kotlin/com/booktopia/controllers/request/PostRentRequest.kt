package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostRentRequest (
    @JsonAlias("rental_date")
    var rentalDate: LocalDate,
    @JsonAlias("client_id")
    @field:NotNull
    @field:Positive
    var clientId: Int,
    @JsonAlias("book_id")
    @field:NotNull
    var bookId: Int
)